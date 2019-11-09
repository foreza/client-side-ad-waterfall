package AdManager;

import android.content.Context;

public interface MonetizationManager {
    void setCustomListener( OnWaterfallCallbackHandler handler);
    void initialize(Context context);
    void setupInterstitialAd(Context context);
    void loadInterstitialAd();
    String getPlacementIDForManager();
}
