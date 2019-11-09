package AdManager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.aerserv.sdk.AerServConfig;
import com.aerserv.sdk.AerServEvent;
import com.aerserv.sdk.AerServEventListener;
import com.aerserv.sdk.AerServInterstitial;
import com.aerserv.sdk.AerServSdk;

import java.util.List;

public class InMobiMonetizationManager extends BaseMonetizationManager{
    private static final InMobiMonetizationManager instance = new InMobiMonetizationManager();


    private OnWaterfallCallbackHandler listener;

    private String LOG_TAG = getClass().getSimpleName();

    private Boolean isSDKInitialized = false;

    private AerServInterstitial interstitial;
    private Boolean isLoaded;
    private String placementID = "1067685";

    private AerServConfig interstitialConfig;

    private Context ContextRef;


    @Override
    public void setCustomListener(OnWaterfallCallbackHandler handler) {
        Log.d(LOG_TAG, "setCustomListener to handler");
        this.listener = handler;

    }

    public static InMobiMonetizationManager getInstance() {
        return instance;
    }


    private InMobiMonetizationManager() {
    }

    @Override
    public void initialize(Context context) {


        if (!isSDKInitialized){

            // Initialize the InMobi SDK
            AerServSdk.init(context,"1021434");     // TODO: Extend the initialize interface to take in an additional param?

            interstitial = null;
            isLoaded = false;

            Log.d(LOG_TAG, "this Manager has been initialized, setting isSDKInitialized to true");
            isSDKInitialized = true;

            ContextRef = context;

        }

        Log.d(LOG_TAG, "this Manager has already been initialized");

    }


    public void reportFailure(){
        listener.onFail(LOG_TAG, "AD_FAILED from INMOBI");    // Let the listener know that we've failed
    }

    public void reportSuccess(){
        listener.onSuccess(LOG_TAG);                      // Let the listener know we've succeeded
    }

    public Activity getActivityFromContext(){

        // Downcast our context to an activity so we can utilize that.
        return (Activity)ContextRef;
    }


    @Override
    public void setupInterstitialAd(Context context) {

        interstitialConfig = new AerServConfig(context, placementID);

        AerServEventListener interstitialListener = new AerServEventListener() {
            @Override
            public void onAerServEvent( final AerServEvent event, final List<Object> args )
            {

                getActivityFromContext().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        switch ( event )
                        {
                                case PRELOAD_READY:
                                    Log.e(LOG_TAG, "PROFILE: PRELOAD_READY");
                                    interstitial.show();
                                    break;
                                case AD_FAILED:

                                    if (args.size() > 1) {
                                        Integer adFailedCode =
                                                (Integer) args.get(AerServEventListener.AD_FAILED_CODE);
                                        String adFailedReason =
                                                (String) args.get(AerServEventListener.AD_FAILED_REASON);
                                        Log.e(LOG_TAG, "PROFILE: AD_FAILED with code=" + adFailedCode + ", reason=" + adFailedReason);
                                    } else {
                                        Log.e(LOG_TAG, "PROFILE: AD_FAILED with message:" + args.get(0).toString());
                                    }
                                    reportFailure();
//                                    listener.onFail(LOG_TAG, "AD_FAILED from INMOBI");    // Let the listener know that we've failed
                                    break;
                                case VC_READY:
                                    break;
                                case VC_REWARDED:
                                    break;
                                case LOAD_TRANSACTION:
                                    Log.d(LOG_TAG, "PROFILE: LOAD_TRANSACTION");
                                    reportSuccess();
//                                    listener.onSuccess(LOG_TAG);                      // Let the listener know we've succeeded
                                    break;
                                case SHOW_TRANSACTION:
                                    Log.d(LOG_TAG, "PROFILE: SHOW_TRANSACTION");
                                    break;
                                default:
                                    Log.d(LOG_TAG, "PROFILE: default");
                            }
                    }
                });
            }
        };

        interstitialConfig.setEventListener(interstitialListener);
        interstitialConfig.setPreload(true);

        Log.d(LOG_TAG, "Interstitial Config is ready!");
    }

    @Override
    public void loadInterstitialAd() {

        Log.d(LOG_TAG, "loadInterstitialAd called!");

        if (interstitialConfig != null){
            interstitial = new AerServInterstitial(interstitialConfig);
            interstitial.show();

        } else {
            Log.e(LOG_TAG, "interstitialConfig is null!");
        }


    }

    @Override
    public Boolean isInterstitialLoaded() {
        return isLoaded;        // Not hooked in
    }


    @Override
    public String getPlacementIDForManager() {
        return placementID;
    }

    @Override
    public Boolean isInitialized() {
        return isSDKInitialized;
    }

    @Override
    public void cleanup() {

        // DO something here if needed

    }
}
