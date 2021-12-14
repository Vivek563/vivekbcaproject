package com.vivek.bcanotes;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.vivek.bcanotes.DashboardActivity.Adapter;
import com.vivek.bcanotes.DashboardActivity.Model;
import com.vivek.bcanotes.Semester.syllubus;
import com.vivek.bcanotes.menu.DrawerAdapter;
import com.vivek.bcanotes.menu.DrawerItem;
import com.vivek.bcanotes.menu.SimpleItem;
import com.vivek.bcanotes.menu.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainDashboard extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    //viewpager
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    CircleImageView profileImage;
    //end
    LinearLayout syllabus, about, dev,compiler,feed,apps;

    private AdView mAdView;
    TextView userinfo;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;
    //Navigation Activity
    private static final int POS_DASHBOARD = 0;
    private static final int POS_SYLLABUS = 1;
    private static final int POS_TELEGRAM = 2;
    private static final int POS_COMPILER = 3;
    private static final int POS_DEVELOPER = 4;
    private static final int POS_RATEUS = 5;
    private static final int POS_FEEDBACK = 6;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;


    private SlidingRootNav slidingRootNav;
//end


    //end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_main_dashboard);


        bannerads();



        profileImage = findViewById(R.id.profileImage);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        //item click
        syllabus = findViewById(R.id.syllubussss);
        userinfo = findViewById(R.id.userinfo);
        about = findViewById(R.id.about);
        dev = findViewById(R.id.develperr);
        compiler = findViewById(R.id.compiler);
        feed = findViewById(R.id.feedback);
        apps = findViewById(R.id.app);



        StorageReference profileRef = storageReference.child("users/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });



        username();

        syllabus.setOnClickListener(view -> {
            Intent intent = new Intent(MainDashboard.this, syllubus.class);
            startActivity(intent);
        });

        feed.setOnClickListener(view -> {
            Intent intent = new Intent(MainDashboard.this, Feedback.class);
            startActivity(intent);
        });

        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(MainDashboard.this, userprofile.class);
            startActivity(intent);
        });


       /* about.setOnClickListener(view -> {
            Intent intent = new Intent(MainDashboard.this, About_us.class);
            startActivity(intent);
        });*/
        dev.setOnClickListener(view -> {
            Intent intent = new Intent(MainDashboard.this, Developer_profile.class);
            startActivity(intent);
        });

        compiler.setOnClickListener(view -> {
            Intent intent = new Intent(MainDashboard.this, Onliner_Compiler.class);
            startActivity(intent);
        });

        apps.setOnClickListener(view -> {
            Intent play = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Vivek+Maurya"));
            startActivity(play);
        });
        //end

//viewpager
        models = new ArrayList<>();
        models.add(new Model(R.drawable.a, "SEMESTER 1st","" ));
        models.add(new Model(R.drawable.b, "SEMESTER 2nd", ""));
        models.add(new Model(R.drawable.c, "SEMESTER 3rd", ""));
        models.add(new Model(R.drawable.d, "SEMESTER 4th", ""));
        models.add(new Model(R.drawable.e, "SEMESTER 5th", ""));
        models.add(new Model(R.drawable.sem6, "SEMESTER 6th", ""));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(195, 25, 195, 25);

        //end

        //Navigation Activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                new SpaceItem(24),
               // createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_SYLLABUS),
                createItemFor(POS_TELEGRAM),
                createItemFor(POS_COMPILER),
                createItemFor(POS_DEVELOPER),
                createItemFor(POS_RATEUS),
                createItemFor(POS_FEEDBACK),
                new SpaceItem(150),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(POS_DASHBOARD);
    }


    @Override
    public void onItemSelected(int position) {
        //  Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);

        if (position == 1) {
            Intent intent = new Intent(MainDashboard.this, syllubus.class);
            startActivity(intent);
        } else if (position == 2) {
            PackageManager pm = getPackageManager();
            try {

                PackageInfo info = pm.getPackageInfo("org.telegram.messenger", PackageManager.GET_META_DATA);

                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/bca563"));
                startActivity(telegram);

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "Telegram is not installed", Toast.LENGTH_SHORT)
                        .show();
            }

        }else if (position == 3) {
            Intent intent = new Intent(MainDashboard.this, Onliner_Compiler.class);
            startActivity(intent);

        }else if (position == 4) {
            Intent intent = new Intent(MainDashboard.this, Developer_profile.class);
            startActivity(intent);

        } else if (position == 5) {
            PackageManager vm = getPackageManager();
            try {

                PackageInfo info = vm.getPackageInfo("com.android.vending", PackageManager.GET_META_DATA);

                Intent playstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.vivek.bcanotes&hl=en_us"));
                startActivity(playstore);

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(this, "PlayStore is Not Available", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (position == 6) {
            Intent intent = new Intent(MainDashboard.this, Feedback.class);
            startActivity(intent);

        }else if (position == 8) {
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        slidingRootNav.closeMenu();

        // showFragment(selectedScreen);
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    //end
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainDashboard.this);
        builder.setMessage("Are you sure want to exit?");
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Exit", (dialog, which) -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void username() {
        final TextView username = (TextView) findViewById(R.id.userinfo);
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(MainDashboard.this, (documentSnapshot, e) -> {
            if (documentSnapshot != null) {
                if (documentSnapshot.exists()) {
                    //                    phone.setText(documentSnapshot.getString("phone"));
                    username.setText(documentSnapshot.getString("fname"));


                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }

    public void bannerads() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.main);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

        });
    }
}


