package ClientWaterfall;

import android.content.Context;
import android.util.Log;


import java.util.ArrayList;

import AdManager.BaseMonetizationManager;
import AdManager.FBMonetizationManager;
import AdManager.InMobiMonetizationManager;
import AdManager.OnWaterfallCallbackHandler;

public class WaterfallManager implements OnWaterfallCallbackHandler {

    private String LOG_TAG = getClass().getSimpleName();

    public ArrayList<BaseMonetizationManager> adWaterfall;

    private int waterfallPosition = 0;
    private Boolean isSDKInitialized = false;

    // Method to init the ad waterfall manager
    public void initialize(Context context) {

        if (!isSDKInitialized) {

            FBMonetizationManager.getInstance().initialize(context);
            FBMonetizationManager.getInstance().setCustomListener(this);
            InMobiMonetizationManager.getInstance().initialize(context);
            InMobiMonetizationManager.getInstance().setCustomListener(this);

            adWaterfall = new ArrayList<>();

            adWaterfall.add(0, FBMonetizationManager.getInstance());
            adWaterfall.add(1, InMobiMonetizationManager.getInstance());

            isSDKInitialized = true;

        }

    }


    public void prepareAdWaterfall(Context context){

        Log.d(LOG_TAG, "====== beginAdLoadShowWaterfall - setting up partners ======== ");

        for (int i = 0; i < adWaterfall.size(); ++i){
            adWaterfall.get(i).setupInterstitialAd(context);
        }

    }

    @Override
    public void onSuccess(String log) {
        Log.d(LOG_TAG, "====== onSuccess from AdManager, stop waterfall - thank you for your imp: " + log);
        resetWaterfallState();
    }

    @Override
    public void onFail(String log, String error) {
        Log.e(LOG_TAG, "====== onFail from AdManager, continue waterfall - failure from: " + log + " with error: " + error);
        waterfallPosition++;
        doNextWaterfallCall(waterfallPosition);
    }

    public void beginAdLoadShowWaterfall(){
        resetWaterfallState();
        Log.d(LOG_TAG, "======== beginAdLoadShowWaterfall - loading partners starting from the top ========");
        doNextWaterfallCall(waterfallPosition);
    }


    public void doNextWaterfallCall(int index){
        if (index < adWaterfall.size()){
            Log.d(LOG_TAG, "======== doNextWaterfallCall - loading partner: " + (index+1) + "/" + adWaterfall.size() + " ========");
            adWaterfall.get(index).loadInterstitialAd();

        } else
            Log.e(LOG_TAG, "======== doNextWaterfallCall - no more partners ========");


    }


    public void resetWaterfallState(){
        waterfallPosition = 0;
    }


}
