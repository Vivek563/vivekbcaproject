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

public class Semester_3rd extends AppCompatActivity {
    LinearLayout discrite,design,software,object,dbms,microproce;
    ImageView back;
    private final String TAG = Semester_3rd.class.getSimpleName();
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_semester_3rd);

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

        discrite = findViewById(R.id.dis);
        back = findViewById(R.id.back3);
        design = findViewById(R.id.desi);
        software = findViewById(R.id.sys);
        object = findViewById(R.id.c);
        dbms = findViewById(R.id.dbms);
        microproce = findViewById(R.id.mic);



        back.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_3rd.this, MainDashboard.class);
            startActivity(intent);
        });

        discrite.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_3rd.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",12);
            startActivity(intent);
        });

        design.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_3rd.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",13);
            startActivity(intent);
        });

        software.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_3rd.this,com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",14);
            startActivity(intent);
        });

        object.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_3rd.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",15);
            startActivity(intent);
        });

        dbms.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_3rd.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",16);
            startActivity(intent);
        });

       microproce.setOnClickListener(view -> {
           Intent intent = new Intent(Semester_3rd.this, com.vivek.bcanotes.pdf_view.class);
           intent.putExtra("key_position",17);
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
            mInterstitialAd.show(Semester_3rd.this);

        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        super.onBackPressed();
    }
}