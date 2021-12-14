package com.vivek.bcanotes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class About_us extends AppCompatActivity {
    ImageView back;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_about_us);

        back = findViewById(R.id.bachk);

        bannerads();

        back.setOnClickListener(view -> {
            Intent intent = new Intent(About_us.this, MainDashboard.class);
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
}