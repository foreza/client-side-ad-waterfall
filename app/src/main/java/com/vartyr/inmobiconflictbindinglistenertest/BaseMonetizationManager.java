package com.vartyr.inmobiconflictbindinglistenertest;

import android.content.Context;

public class BaseMonetizationManager implements MonetizationManager {

    @Override
    public void setCustomListener(OnWaterfallCallbackHandler handler) {

    }

    @Override
    public void initialize(Context context) {

    }

    @Override
    public void setupInterstitialAd(Context context) {

    }

    @Override
    public void loadInterstitialAd() {

    }

    @Override
    public Boolean isInterstitialLoaded() {
        return null;
    }


    @Override
    public String getPlacementIDForManager() {
        return null;
    }

    @Override
    public Boolean isInitialized() {
        return null;
    }

    @Override
    public void cleanup() {

    }
}
