package com.vivek.bcanotes.Semester;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Semester_6th extends AppCompatActivity {
    private final String TAG = Semester_5th.class.getSimpleName();
    LinearLayout netfamework,embedded,project;
    ImageView back;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_semester6th);


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


        back = findViewById(R.id.back4);
        netfamework = findViewById(R.id.og);
        embedded = findViewById(R.id.dd);
        project = findViewById(R.id.fff);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_6th.this, MainDashboard.class);
            startActivity(intent);
        });


        netfamework.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_6th.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",27);
            startActivity(intent);
        });

        embedded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Semester_6th.this, com.vivek.bcanotes.pdf_view.class);
                intent.putExtra("key_position",28);
                startActivity(intent);
            }
        });

        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Vivek563"));
                startActivity(playstore);
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

    @Override
    public void onBackPressed () {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Semester_6th.this);

        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        super.onBackPressed();
    }
}