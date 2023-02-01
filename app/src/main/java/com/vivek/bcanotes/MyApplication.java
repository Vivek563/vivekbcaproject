
package com.vivek.bcanotes;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Date;

public class MyApplication extends Application
    implements ActivityLifecycleCallbacks, LifecycleObserver {

  private AppOpenAdManager appOpenAdManager;
  private Activity currentActivity;

  @Override
  public void onCreate() {
    super.onCreate();
    this.registerActivityLifecycleCallbacks(this);
    MobileAds.initialize(
        this,
        new OnInitializationCompleteListener() {
          @Override
          public void onInitializationComplete(
              @NonNull InitializationStatus initializationStatus) {}
        });

    ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    appOpenAdManager = new AppOpenAdManager();
  }

  @OnLifecycleEvent(Event.ON_START)
  protected void onMoveToForeground() {
    // Show the ad (if available) when the app moves to foreground.
    appOpenAdManager.showAdIfAvailable(currentActivity);
  }


  @Override
  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}

  @Override
  public void onActivityStarted(@NonNull Activity activity) {
    // An ad activity is started when an ad is showing, which could be AdActivity class from Google
    // SDK or another activity class implemented by a third party mediation partner. Updating the
    // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
    // one that shows the ad.
    if (!appOpenAdManager.isShowingAd) {
      currentActivity = activity;
    }
  }

  @Override
  public void onActivityResumed(@NonNull Activity activity) {}

  @Override
  public void onActivityPaused(@NonNull Activity activity) {}

  @Override
  public void onActivityStopped(@NonNull Activity activity) {}

  @Override
  public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

  @Override
  public void onActivityDestroyed(@NonNull Activity activity) {}


  public void showAdIfAvailable(
      @NonNull Activity activity,
      @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
    // We wrap the showAdIfAvailable to enforce that other classes only interact with MyApplication
    // class.
    appOpenAdManager.showAdIfAvailable(activity, onShowAdCompleteListener);
  }

  public interface OnShowAdCompleteListener {
    void onShowAdComplete();
  }

  private class AppOpenAdManager {

    private static final String LOG_TAG = "AppOpenAdManager";
    private static final String AD_UNIT_ID = "ca-app-pub-6367744599593234/6930365153";

    private AppOpenAd appOpenAd = null;
    private boolean isLoadingAd = false;
    private boolean isShowingAd = false;


    private long loadTime = 0;

    public AppOpenAdManager() {}


    private void loadAd(Context context) {
      // Do not load ad if there is an unused ad or one is already loading.
      if (isLoadingAd || isAdAvailable()) {
        return;
      }

      isLoadingAd = true;
      AdRequest request = new AdRequest.Builder().build();
      AppOpenAd.load(
          context,
          AD_UNIT_ID,
          request,
          AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
          new AppOpenAdLoadCallback() {

            @Override
            public void onAdLoaded(AppOpenAd ad) {
              appOpenAd = ad;
              isLoadingAd = false;
              loadTime = (new Date()).getTime();

              Log.d(LOG_TAG, "onAdLoaded.");
            //  Toast.makeText(context, "onAdLoaded", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
              isLoadingAd = false;
              Log.d(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
              //Toast.makeText(context, "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
            }
          });
    }


    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
      long dateDifference = (new Date()).getTime() - loadTime;
      long numMilliSecondsPerHour = 3600000;
      return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    private boolean isAdAvailable() {
      // Ad references in the app open beta will time out after four hours, but this time limit
      // may change in future beta versions. For details, see:
      // https://support.google.com/admob/answer/9341964?hl=en
      return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    private void showAdIfAvailable(@NonNull final Activity activity) {
      showAdIfAvailable(
          activity,
          new OnShowAdCompleteListener() {
            @Override
            public void onShowAdComplete() {
              // Empty because the user will go back to the activity that shows the ad.
            }
          });
    }


    private void showAdIfAvailable(
        @NonNull final Activity activity,
        @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
      // If the app open ad is already showing, do not show the ad again.
      if (isShowingAd) {
        Log.d(LOG_TAG, "The app open ad is already showing.");
        return;
      }

      // If the app open ad is not available yet, invoke the callback then load the ad.
      if (!isAdAvailable()) {
        Log.d(LOG_TAG, "The app open ad is not ready yet.");
        onShowAdCompleteListener.onShowAdComplete();
        loadAd(activity);
        return;
      }

      Log.d(LOG_TAG, "Will show ad.");

      appOpenAd.setFullScreenContentCallback(
          new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
              // Set the reference to null so isAdAvailable() returns false.
              appOpenAd = null;
              isShowingAd = false;

              Log.d(LOG_TAG, "onAdDismissedFullScreenContent.");
             // Toast.makeText(activity, "onAdDismissedFullScreenContent", Toast.LENGTH_SHORT).show();

              onShowAdCompleteListener.onShowAdComplete();
              loadAd(activity);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
              appOpenAd = null;
              isShowingAd = false;

              Log.d(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());
             // Toast.makeText(activity, "onAdFailedToShowFullScreenContent", Toast.LENGTH_SHORT)
                 // .show();

              onShowAdCompleteListener.onShowAdComplete();
              loadAd(activity);
            }

            @Override
            public void onAdShowedFullScreenContent() {
              Log.d(LOG_TAG, "onAdShowedFullScreenContent.");
           //   Toast.makeText(activity, "onAdShowedFullScreenContent", Toast.LENGTH_SHORT).show();
            }
          });

      isShowingAd = true;
      appOpenAd.show(activity);
    }
  }
}
