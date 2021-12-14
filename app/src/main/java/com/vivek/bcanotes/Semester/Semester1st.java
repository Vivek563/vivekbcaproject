package com.vivek.bcanotes.Semester;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.vivek.bcanotes.MainDashboard;
import com.vivek.bcanotes.R;

public class Semester1st extends AppCompatActivity {
    private final String TAG = Semester1st.class.getSimpleName();
    LinearLayout c, maths, busness, computer, statics, Management;
    ImageView back;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_semester1st);

        bannerads();

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,getResources().getString(R.string.interstitialads), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });



        c = findViewById(R.id.clanguage);
        back = findViewById(R.id.backk1);
        maths = findViewById(R.id.math);
        busness = findViewById(R.id.busniness);
        computer = findViewById(R.id.computer);
        statics = findViewById(R.id.stai);
        Management = findViewById(R.id.Management);


        Management.setOnClickListener(view -> {
            Intent intent = new Intent(Semester1st.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position", 1);
            startActivity(intent);
        });


        back.setOnClickListener(view -> {
            Intent intent = new Intent(Semester1st.this, MainDashboard.class);
            startActivity(intent);
        });

        c.setOnClickListener(view -> {
            Intent intent = new Intent(Semester1st.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position", 2);
            startActivity(intent);
        });

        maths.setOnClickListener(view -> {
            Intent intent = new Intent(Semester1st.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position", 3);
            startActivity(intent);
        });

        busness.setOnClickListener(view -> {
            Intent intent = new Intent(Semester1st.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position", 4);
            startActivity(intent);
        });

        computer.setOnClickListener(view -> {
            Intent intent = new Intent(Semester1st.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position", 5);
            startActivity(intent);
        });

        statics.setOnClickListener(view -> {
            Intent intent = new Intent(Semester1st.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position", 6);
            startActivity(intent);
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


    @Override
    public void onBackPressed () {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Semester1st.this);

        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        super.onBackPressed();
    }

    }
