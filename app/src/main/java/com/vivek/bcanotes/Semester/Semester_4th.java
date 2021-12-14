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

public class Semester_4th extends AppCompatActivity {
    private final String TAG = Semester_4th.class.getSimpleName();
    LinearLayout operat,opration,data,engree,java,numerical;
    ImageView back;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_semester_4th);

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



        operat = findViewById(R.id.o);
        back = findViewById(R.id.back4);
        opration = findViewById(R.id.operation);
        data = findViewById(R.id.datacommunaction);
        engree = findViewById(R.id.software);
        java = findViewById(R.id.java);
        numerical = findViewById(R.id.numaerical);


        back.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_4th.this, MainDashboard.class);
            startActivity(intent);
        });


        opration.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_4th.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",18);
            startActivity(intent);
        });

        operat.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_4th.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",19);
            startActivity(intent);
        });

        data.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_4th.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",20);
            startActivity(intent);
        });

        engree.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_4th.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",21);
            startActivity(intent);
        });

        java.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_4th.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",22);
            startActivity(intent);
        });

        numerical.setOnClickListener(view -> {
            Intent intent = new Intent(Semester_4th.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",23);
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
            mInterstitialAd.show(Semester_4th.this);

        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        super.onBackPressed();
    }
}