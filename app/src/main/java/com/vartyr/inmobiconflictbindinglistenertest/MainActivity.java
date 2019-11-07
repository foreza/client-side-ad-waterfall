package com.vartyr.inmobiconflictbindinglistenertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnWaterfallCallbackHandler {

    private String LOG_TAG = getClass().getSimpleName();

    private static int waterfallPosition = 0;

    public ArrayList<BaseMonetizationManager> adWaterfall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAdProvidersAndWaterfall();
    }

    // Method to init ad providers
    public void initAdProvidersAndWaterfall() {

        FBMonetizationManager.getInstance().initialize(this);
        FBMonetizationManager.getInstance().setCustomListener(this);
        InMobiMonetizationManager.getInstance().initialize(this);
        InMobiMonetizationManager.getInstance().setCustomListener(this);

        adWaterfall= new ArrayList<>();

        adWaterfall.add(0,FBMonetizationManager.getInstance());
        adWaterfall.add(1,InMobiMonetizationManager.getInstance());


    }

    public void prepareAdWaterfall(){

        Log.d(LOG_TAG, "====== beginAdLoadShowWaterfall - setting up partners ======== ");

        for (int i = 0; i < adWaterfall.size(); ++i){
            adWaterfall.get(i).setupInterstitialAd(this);
        }

    }


    public void triggerAction(View view){
        prepareAdWaterfall();
        beginAdLoadShowWaterfall();
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
