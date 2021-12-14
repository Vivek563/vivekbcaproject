package com.vivek.bcanotes;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Sign_up extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mFullName, mPassword, mPhoneNO, mEmail;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    CircularProgressButton mRegisterBtn;
    ProgressBar progressBAr;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sign_up);


        //hooks to all xml element in activity_sign_up.xml

        mFullName = findViewById(R.id.editTextName);
        mEmail = findViewById(R.id.editTextEmail);
        mPhoneNO = findViewById(R.id.editTextMobile);
        mPassword = findViewById(R.id.editTextPassword);
        mRegisterBtn = findViewById(R.id.cirRegisterButton);
        mLoginBtn = findViewById(R.id.reg_login_btn);
        fAuth = FirebaseAuth.getInstance();
        progressBAr = findViewById(R.id.progressBar2);
        fStore = FirebaseFirestore.getInstance();


        int colorCodeDark = Color.parseColor("#EFCB95");
        progressBAr.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainDashboard.class));
            finish();
        }

        mLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Sign_up.this, Login.class);
            startActivity(intent);
        });

        mRegisterBtn.setOnClickListener(v -> {
            final String email = mEmail.getText().toString().trim();
            final String password = mPassword.getText().toString().trim();
            final String fullName = mFullName.getText().toString();
            final String phone = mPhoneNO.getText().toString();

            if (TextUtils.isEmpty(fullName)) {
                mFullName.setError("Full name is required");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("email is required");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                mPhoneNO.setError("phone number is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("password is required");
                return;
            }
            if (password.length() < 6) {
                mPassword.setError("password must be greater than 5 characters");
                return;
            }
            progressBAr.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Sign_up.this, "user created", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("fname", fullName);
                    user.put("email", email);
                    user.put("phone", phone);
                    user.put("password", password);
                    documentReference.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess:user Profile is created for " + userID)).addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.toString()));
                    startActivity(new Intent(getApplicationContext(), MainDashboard.class));
                } else {
                    Toast.makeText(Sign_up.this, "error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
                progressBAr.setVisibility(View.GONE);
            });
        });

    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,Login.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }
}


