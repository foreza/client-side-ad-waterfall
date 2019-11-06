package com.vartyr.inmobiconflictbindinglistenertest;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class FBMonetizationManager implements MonetizationManager {

    private static final FBMonetizationManager ourInstance = new FBMonetizationManager();
    private static String LOG_TAG = getInstance().getClass().getSimpleName();

    private InterstitialAd interstitialAd;
    private Boolean isLoaded;
    private String placementID;



    public static FBMonetizationManager getInstance() {
        return ourInstance;
    }

    private FBMonetizationManager() {
    }

    @Override
    public void initialize(Context context) {

        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(context);

        interstitialAd = null;
        isLoaded = false;
        placementID = "";

        Log.d(LOG_TAG, "this Manager has been initialized");

    }

    @Override
    public void loadAndShowInterstitialAd(Context context) {

        interstitialAd = new InterstitialAd(context, getPlacementIDForManager());


        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.d(LOG_TAG, "loadAndShowInterstitialAd - Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.d(LOG_TAG, "loadAndShowInterstitialAd - Interstitial ad dismissed.");

                isLoaded = false;
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(LOG_TAG, "loadAndShowInterstitialAd - Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(LOG_TAG, "loadAndShowInterstitialAd - Interstitial ad is loaded, calling show");

                isLoaded = true;

                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(LOG_TAG, "loadAndShowInterstitialAd - Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(LOG_TAG, "loadAndShowInterstitialAd - Interstitial ad impression logged!");
            }
        });


        interstitialAd.loadAd();

    }

    @Override
    public void preloadInterstitialAd(Context context) {

        interstitialAd = new InterstitialAd(context, getPlacementIDForManager());

        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.d(LOG_TAG, " preloadInterstitialAd - Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.d(LOG_TAG, "preloadInterstitialAd - Interstitial ad dismissed.");

                isLoaded = false;
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(LOG_TAG, "preloadInterstitialAd - Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(LOG_TAG, "preloadInterstitialAd - Interstitial ad is loaded and ready to be displayed!");

                isLoaded = true;

                // Don't show the ad.
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(LOG_TAG, "preloadInterstitialAd - Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(LOG_TAG, "preloadInterstitialAd - Interstitial ad impression logged!");
            }
        });


        interstitialAd.loadAd();
    }

    @Override
    public void showInterstitialAd(Context context) {

        if (interstitialAd != null && isLoaded){
            interstitialAd.show();
            Log.d(LOG_TAG, "showInterstitialAd success");
        } else {
            Log.e(LOG_TAG, "showInterstitialAd failed - interstitial Ad was null or wasn't loaded");
        }

    }

    @Override
    public Boolean isInterstitialLoaded() {
        return isLoaded;
    }

    @Override
    public void setPlacementIDForManager(String plc) {
        placementID = plc;

        Log.d(LOG_TAG, "setPlacementIDForManager: " + plc);
    }

    @Override
    public String getPlacementIDForManager() {
        return placementID;
    }


    public void cleanup() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
    }

}