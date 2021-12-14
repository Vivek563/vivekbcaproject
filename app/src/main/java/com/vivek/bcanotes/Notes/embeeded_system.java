package com.vivek.bcanotes.Notes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.vivek.bcanotes.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class embeeded_system extends AppCompatActivity {

    String link = "", productList = "", product = "";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    /*private com.facebook.ads.AdView adView;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pdf_view);

        onPageChanged();
        pdfView = findViewById(R.id.pdfv);

       /* AudienceNetworkAds.initialize(this);
        adView = new com.facebook.ads.AdView(this, getString(R.string.bannerfb), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner1);
        adContainer.addView(adView);
        adView.loadAd();*/


        product = getIntent().getStringExtra("title");
        productList = getIntent().getStringExtra("productList");
        link = getIntent().getStringExtra("link");
        pdfView = findViewById(R.id.pdfv);
        // pdfView.fromAsset(link).load();


        if (isConnected()) {
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(embeeded_system.this);
            builder.setTitle("NoInternet Connection Alert")
                    .setMessage("GO to Setting ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(embeeded_system.this, "Go Back TO HomePage!", Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            link = getIntent().getStringExtra("link");
        }
        new embeeded_system.RetrievePDFStream().execute("https://firebasestorage.googleapis.com/v0/b/bca-note.appspot.com/o/Notes%2Fembedded%20system.pdf?alt=media&token=1d1a2502-c205-4e97-bf65-ef18c318e577");
    }


    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }


    class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(embeeded_system.this);
            progressDialog.setTitle("Getting the Notes contents...");
            progressDialog.setMessage("Please wait until it load...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {

                URL urlx = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) urlx.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
            progressDialog.dismiss();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)//means home default hai kya yesok
        {
            onBackPressed();
            return true;
        }
        return false;
    }

    public void onPageChanged() {
        int page = 0, pageCount = 0;
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }
}