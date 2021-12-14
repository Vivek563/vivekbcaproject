package com.vivek.bcanotes;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.vivek.bcanotes.MyApplication.OnShowAdCompleteListener;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;


public class MainActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {

    InAppUpdateManager inAppUpdateManager;
    private long secondsRemaining;
    private static final String LOG_TAG = "SplashActivity";
    private static final long COUNTER_TIME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setStatusBarColor(Color.parseColor("#EFCB95"));
        setContentView(R.layout.activity_main);

        createTimer(COUNTER_TIME);

        TextView python = (TextView) findViewById(R.id.textView);
        TextView quotes = (TextView) findViewById(R.id.text);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        python.startAnimation(anim);
        quotes.startAnimation(anim);

        int colorCodeDark = Color.parseColor("#EFCB95");

        Thread splashThread = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        splashThread.start();



//Update code


        inAppUpdateManager = InAppUpdateManager.Builder(this,101)
                .resumeUpdates(true)
                .mode(Constants.UpdateMode.FLEXIBLE)
                .snackBarAction("An Update has just been downloaded.")
                .snackBarAction("Restart")
                .handler(this);

        inAppUpdateManager.checkForAppUpdate();
    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {

    }


    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        if (status.isDownloaded()){
            View view = getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(view,"An Update has just been downloaded.",Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("", view1 -> inAppUpdateManager.completeUpdate());

            snackbar.show();
        }

    }

    private void createTimer(long seconds) {
      //  final TextView counterTextView = findViewById(R.id.timer);

        CountDownTimer countDownTimer =
                new CountDownTimer(seconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondsRemaining = ((millisUntilFinished / 1000) + 1);
                     //   counterTextView.setText("App is done loading in: " + secondsRemaining);
                    }

                    @Override
                    public void onFinish() {
                        secondsRemaining = 0;
                       // counterTextView.setText("Done.");

                        Application application = getApplication();

                        // If the application is not an instance of MyApplication, log an error message and
                        // start the MainActivity without showing the app open ad.
                        if (!(application instanceof MyApplication)) {
                            Log.e(LOG_TAG, "Failed to cast application to MyApplication.");
                            startMainActivity();
                            return;
                        }

                        // Show the app open ad.
                        ((MyApplication) application)
                                .showAdIfAvailable(
                                        MainActivity.this,
                                        new OnShowAdCompleteListener() {
                                            @Override
                                            public void onShowAdComplete() {
                                                startMainActivity();
                                            }
                                        });
                    }
                };
        countDownTimer.start();
    }

    /** Start the MainActivity. */
    public void startMainActivity() {
        Intent intent = new Intent(this, Login.class);
        this.startActivity(intent);
    }
}

