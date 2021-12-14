package com.vivek.bcanotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    EditText mFullName,mEmail,text;
    Button mRegisterBtn;
    ImageView imageView;
    Button btnNoInternetConnection;
    RelativeLayout relativeLayout;
    public static final String TAG = "TAG";
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_feedback);


        mFullName = findViewById(R.id.editTextName);
        mEmail = findViewById(R.id.editTextEmail);
        mRegisterBtn = findViewById(R.id.buttonnnnn);
        text = findViewById(R.id.textfeed);
        imageView = findViewById(R.id.back);

        btnNoInternetConnection = (Button) findViewById(R.id.btnNoConnection);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.GONE);

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Feedback.this, MainDashboard.class);
            startActivity(intent);
        });

        mRegisterBtn.setOnClickListener(v -> {

            checkConnection();

            final String fullName = mFullName.getText().toString();
            final String email = mEmail.getText().toString().trim();
            final String textfeed = text.getText().toString().trim();


            if (TextUtils.isEmpty(fullName)) {
                mFullName.setError("Full name is required");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("email is required");
                return;
            }
            if (TextUtils.isEmpty(textfeed)) {
                text.setError("FeedBack is Required");
                return;
            }
//                userID = fAuth.getCurrentUser().getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> user = new HashMap<>();
                user.put("fname", fullName);
                user.put("email", email);
                user.put("feedback", textfeed);
                db.collection("feedback")
                      .add(user)
                      .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess:user Profile is created for " + userID))
                      .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.toString()));
            feedbackmessage();
        });


        btnNoInternetConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
            }
        });

    }

    public void feedbackmessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Feedback.this);
        builder.setMessage("ThankYou for your Valuable Feedback.");
        builder.setPositiveButton("OK", (dialog, which) -> {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if(wifi.isConnected()){
            relativeLayout.setVisibility(View.GONE);


        }
        else if (mobileNetwork.isConnected()){
            relativeLayout.setVisibility(View.GONE);
        }
        else{
            relativeLayout.setVisibility(View.VISIBLE);

        }


    }
}