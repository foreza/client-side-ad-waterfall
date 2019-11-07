package com.vartyr.inmobiconflictbindinglistenertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import ClientWaterfall.WaterfallManager;



public class MainActivity extends AppCompatActivity{

    private String LOG_TAG = getClass().getSimpleName();

    private WaterfallManager waterfallManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAdProvidersAndWaterfall();
    }

    // Method to init ad providers
    public void initAdProvidersAndWaterfall() {

        waterfallManager = new WaterfallManager();
        waterfallManager.initialize(this);
    }


    public void triggerAction(View view){
        waterfallManager.prepareAdWaterfall(this);
        waterfallManager.beginAdLoadShowWaterfall();
    }

}
