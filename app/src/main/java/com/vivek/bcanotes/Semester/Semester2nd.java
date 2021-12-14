package com.vivek.bcanotes.Semester;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class Semester2nd extends AppCompatActivity {

    private final String TAG = Semester2nd.class.getSimpleName();
    LinearLayout linux, data, digital, progrma, basic;
    ImageView back;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_semester2nd);

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


        linux = findViewById(R.id.linux);
        back = findViewById(R.id.back2);
        data = findViewById(R.id.data);
        digital = findViewById(R.id.digital);
        progrma = findViewById(R.id.progamm);
        basic = findViewById(R.id.basic);



        findViewById(R.id.matsh).setOnClickListener(v -> {
            mathdownload("https://firebasestorage.googleapis.com/v0/b/bca-note.appspot.com/o/Math.pdf?alt=media&token=6912e6cb-4292-4da5-99e2-9651612bab18");
            Toast.makeText(Semester2nd.this,"Choose Browser to Start Downloading", Toast.LENGTH_LONG).show();
        });


        back.setOnClickListener(view -> {
            Intent intent = new Intent(Semester2nd.this, MainDashboard.class);

            startActivity(intent);
        });


        linux.setOnClickListener(view -> {
            Intent intent = new Intent(Semester2nd.this, com.vivek.bcanotes.pdf_view.class);
            intent.putExtra("key_position",7);
            startActivity(intent);
        });



                data.setOnClickListener(view -> {
                    Intent intent = new Intent(Semester2nd.this, com.vivek.bcanotes.pdf_view.class);
                    intent.putExtra("key_position",8);
                    startActivity(intent);
                });

                digital.setOnClickListener(view -> {
                    Intent intent = new Intent(Semester2nd.this, com.vivek.bcanotes.pdf_view.class);
                    intent.putExtra("key_position",9);
                    startActivity(intent);
                });

                progrma.setOnClickListener(view -> {
                    Intent intent = new Intent(Semester2nd.this, com.vivek.bcanotes.pdf_view.class);
                    intent.putExtra("key_position",10);
                    startActivity(intent);
                });

                basic.setOnClickListener(view -> {
                    Intent intent = new Intent(Semester2nd.this, com.vivek.bcanotes.pdf_view.class);
                    intent.putExtra("key_position",11);
                    startActivity(intent);
                });
            }

            public void mathdownload(String url) {
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

    @Override
    public void onBackPressed () {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Semester2nd.this);

        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        super.onBackPressed();
    }
}
