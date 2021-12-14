package com.vivek.bcanotes;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Login extends AppCompatActivity {

    EditText mEmail,mPassword;
    CircularProgressButton mLoginBtn;
    TextView mCreateBtn,forgotText;
    ProgressBar progressBar1;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_login);

        mEmail      = findViewById(R.id.editTextEmail);
        mPassword   = findViewById(R.id.editTextPassword);
        progressBar1 = findViewById(R.id.progressBar1);
        fAuth       = FirebaseAuth.getInstance();
        mLoginBtn   = findViewById(R.id.cirLoginButton);
        mCreateBtn  = findViewById(R.id.newuser);
        forgotText  = findViewById(R.id.Forget);



        int colorCodeDark = Color.parseColor("#EFCB95");
        progressBar1.setIndeterminateTintList(ColorStateList.valueOf(colorCodeDark));


        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainDashboard.class));
            finish();
        }

        mLoginBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("password is required");
                return;
            }
            progressBar1.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this,"Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainDashboard.class));
                }else {
                    Toast.makeText(Login.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar1.setVisibility(View.GONE);
                }

            });

        });

        mCreateBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this,Sign_up.class);
            startActivity(intent);
        });

        forgotText.setOnClickListener(v -> {
            final EditText resetMail = new EditText(v.getContext());
            AlertDialog.Builder passwordRestDialog = new AlertDialog.Builder(v.getContext());
            passwordRestDialog.setMessage("Enter your Email to received Reset Link.");
            passwordRestDialog.setView(resetMail);

            passwordRestDialog.setPositiveButton("Yes", (dialog, which) -> {
                //extract the mail and send reset link
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(Login.this,"Reset Link send to your Email",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this,"Error! Reset Link is Not send" +e.getMessage(),Toast.LENGTH_SHORT).show());
            });
            passwordRestDialog.setNegativeButton("NO", (dialog, which) -> {
                //closing Dialog
            });
            passwordRestDialog.create().show();
        });
    }
    @Override
    public void onBackPressed() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Login.this);
        builder.setMessage("Are you sure want to exit?");
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Exit", (dialog, which) -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,Sign_up.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}








