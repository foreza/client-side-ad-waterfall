package com.vartyr.inmobiconflictbindinglistenertest;

public interface OnWaterfallCallbackHandler {

    public void onSuccess(String log);
    public void onFail(String log, String error);

}
