package AdManager;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class FBMonetizationManager extends BaseMonetizationManager {

    private static final FBMonetizationManager ourInstance = new FBMonetizationManager();

    private OnWaterfallCallbackHandler listener;


    private String LOG_TAG = getClass().getSimpleName();

    private Boolean isSDKInitialized = false;

    private static InterstitialAd interstitialAd;               // We should only ever refer to this instance of interstitial Ad? will this break stuff?
    private Boolean isLoaded;
    private String placementID = "1785700261650984_2495620377325632"; // CAROUSEL_IMG_SQUARE_LINK#1785700261650984_2495620377325632

    @Override
    public void setCustomListener(OnWaterfallCallbackHandler handler) {
        Log.d(LOG_TAG, "setCustomListener to handler");
        this.listener = handler;
    }

    public static FBMonetizationManager getInstance() {
        return ourInstance;
    }

    private FBMonetizationManager() {
    }

    @Override
    public void initialize(Context context) {

        if (!isSDKInitialized){

            // Initialize the Audience Network SDK
            AudienceNetworkAds.initialize(context);

            interstitialAd = null;
            isLoaded = false;

            Log.d(LOG_TAG, "this Manager has been initialized, setting isSDKInitialized to true");

            isSDKInitialized = true;
        }

        Log.d(LOG_TAG, "this Manager has already been initialized");


    }

    @Override
    public Boolean isInitialized() {
        return isSDKInitialized;
    }

    @Override

    // Set up our listeners.

    public void setupInterstitialAd(Context context) {

        interstitialAd = new InterstitialAd(context, getPlacementIDForManager());

        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.d(LOG_TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.d(LOG_TAG, "Interstitial ad dismissed.");

                isLoaded = false;
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(LOG_TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                listener.onFail(LOG_TAG, adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(LOG_TAG, "Interstitial ad is loaded, calling show");

                isLoaded = true;

                // Show the ad
                interstitialAd.show();

                listener.onSuccess(LOG_TAG);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(LOG_TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(LOG_TAG, "Interstitial ad impression logged!");
            }
        });

        Log.d(LOG_TAG, "Interstitial Config is ready!");


    }

    @Override
    public void loadInterstitialAd() {

        Log.d(LOG_TAG, "loadInterstitialAd called!");


        if (interstitialAd != null){
            interstitialAd.loadAd();
        } else {
            Log.e(LOG_TAG, "interstitialAd is null!");
        }

    }

    @Override
    public Boolean isInterstitialLoaded() {
        return isLoaded;
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
