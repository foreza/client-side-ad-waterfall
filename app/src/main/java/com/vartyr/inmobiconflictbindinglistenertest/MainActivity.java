package com.vartyr.inmobiconflictbindinglistenertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdProviders();
    }

    // Method to init ad providers
    public void initAdProviders(){
        FBMonetizationManager.getInstance().initialize(this);
        FBMonetizationManager.getInstance().setPlacementIDForManager("CAROUSEL_IMG_SQUARE_LINK#1785700261650984_2495620377325632");
    }


    public void triggerAction(View view){

        beginAdLoadShowWaterfall();

    }

    public void beginAdLoadShowWaterfall(){

        FBMonetizationManager.getInstance().loadAndShowInterstitialAd(this);

    }


}
