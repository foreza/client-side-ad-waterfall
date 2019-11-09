package AdManager;

import android.content.Context;

/*

Extend BaseMonetizationManager for all ad managers

 */

public class BaseMonetizationManager implements MonetizationManager {

    @Override
    public void setCustomListener(OnWaterfallCallbackHandler handler) { }

    @Override
    public void initialize(Context context) { }

    @Override
    public void setupInterstitialAd(Context context) { }

    @Override
    public void loadInterstitialAd() { }

    @Override
    public String getPlacementIDForManager() {
        return null;
    }

}
