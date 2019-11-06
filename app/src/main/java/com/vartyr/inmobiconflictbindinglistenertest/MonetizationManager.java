package com.vartyr.inmobiconflictbindinglistenertest;

import android.content.Context;

public interface MonetizationManager {

    public void initialize(Context context);
    public void setupInterstitialAd(Context context);
    public void loadInterstitialAd();
    public Boolean isInterstitialLoaded();

    public void setPlacementIDForManager(String plc);
    public String getPlacementIDForManager();

    public Boolean isInitialized();

    public void cleanup();



}
