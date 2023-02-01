package com.vivek.bcanotes;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;

public class Developer_profile extends AppCompatActivity {
    Button insta;
    ImageView back,instagr,outlook,fb,linkdin;
    TextView textView;


    private AdView mAdView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_developer_profile);


        bannerads();

        textView = findViewById(R.id.aboutme);
        instagr = findViewById(R.id.instagramgg);
        outlook = findViewById(R.id.outlook);
        fb = findViewById(R.id.fbbb);
        linkdin = findViewById(R.id.linkdin);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        DocumentReference documentReference = fStore.collection("vivek").document("fname");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    textView.setText(documentSnapshot.getString("fname"));
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


        back = findViewById(R.id.back);
        insta = findViewById(R.id.instagram);

        findViewById(R.id.instagram).setOnClickListener(v -> clicked_btn("https://www.instagram.com/vivekmaurya_563/"));
        findViewById(R.id.instagramgg).setOnClickListener(v -> clicked_btn("https://www.instagram.com/letcodeit/"));
        findViewById(R.id.fbbb).setOnClickListener(v -> clicked_btn("https://github.com/Vivek563"));
        findViewById(R.id.linkdin).setOnClickListener(v -> clicked_btn("https://www.linkedin.com/in/vivek563maurya/"));

        back.setOnClickListener(view -> {
            Intent intent = new Intent(Developer_profile.this, MainDashboard.class);
            startActivity(intent);
        });

        outlook.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "vivek563mauryaa@gmail.com" });
            intent.putExtra(Intent.EXTRA_SUBJECT, "BCA");
            intent.putExtra(Intent.EXTRA_TEXT, "Hey! ");
            startActivity(Intent.createChooser(intent, ""));
        });
    }

    public void clicked_btn(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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