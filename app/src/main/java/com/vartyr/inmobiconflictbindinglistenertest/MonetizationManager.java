package com.vartyr.inmobiconflictbindinglistenertest;

import android.content.Context;

public interface MonetizationManager {

    void setCustomListener( OnWaterfallCallbackHandler handler);

    public void initialize(Context context);
    public void setupInterstitialAd(Context context);
    public void loadInterstitialAd();
    public Boolean isInterstitialLoaded();
    public String getPlacementIDForManager();

    public Boolean isInitialized();

    public void cleanup();



}
