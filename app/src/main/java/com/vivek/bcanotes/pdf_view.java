package com.vivek.bcanotes;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class pdf_view extends AppCompatActivity {

    PDFView pdfView;

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pdf_view);


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


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        pdfView = findViewById(R.id.pdfviews);


        int story = getIntent().getIntExtra("key_position", 0);

        if (story == 1) {
            pdfView.fromAsset("managenet.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        } else if (story == 2) {
            pdfView.fromAsset("cfullnotes.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 3) {
            pdfView.fromAsset("Maths.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 4) {
            pdfView.fromAsset("Business_Communication.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 5) {
            pdfView.fromAsset("computer.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 6) {
            pdfView.fromAsset("sataiscs.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 7) {
            pdfView.fromAsset("LINUXFINALNOTES.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 8) {
            pdfView.fromAsset("dsnotes.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 9) {
            pdfView.fromAsset("DigitalLogic.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();


        } else if (story == 10) {
            pdfView.fromAsset("ppl.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 11) {
            pdfView.fromAsset("basicelectorinc.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        } else if (story == 12) {
            pdfView.fromAsset("discrite.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 13) {
            pdfView.fromAsset("dsignalgorithms.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 14) {
            pdfView.fromAsset("SystemSoftware.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 15) {
            pdfView.fromAsset("cpp.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 16) {
            pdfView.fromAsset("dbms.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 17) {
            pdfView.fromAsset("Microprocess.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        } else if (story == 18) {
            pdfView.fromAsset("operationresour.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 19) {
            pdfView.fromAsset("operationgsystem.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 20) {
            pdfView.fromAsset("DataCommunicationNetworking.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 21) {
            pdfView.fromAsset("softwareEngineeri.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 22) {
            pdfView.fromAsset("java.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 23) {
            pdfView.fromAsset("numericalmethod.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        } else if (story == 24) {
            pdfView.fromAsset("net.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 25) {
            pdfView.fromAsset("computer graphic.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        } else if (story == 26) {
            pdfView.fromAsset("Adbms.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        }else if (story == 27) {
            pdfView.fromAsset("Image Processing.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        }else if (story == 28) {
            pdfView.fromAsset("MULTIMEDIA_SYSTEMS.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();
        }
    }

}