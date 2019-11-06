package com.vartyr.inmobiconflictbindinglistenertest;

import android.content.Context;

public interface MonetizationManager {

    public void initialize(Context context);
    public void loadAndShowInterstitialAd(Context context);
    public void preloadInterstitialAd(Context context);
    public void showInterstitialAd(Context context);

    public Boolean isInterstitialLoaded();

    public void setPlacementIDForManager(String plc);
    public String getPlacementIDForManager();

    public void cleanup();



}
