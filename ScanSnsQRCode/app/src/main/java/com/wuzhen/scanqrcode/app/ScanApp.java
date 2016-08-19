package com.wuzhen.scanqrcode.app;

import android.app.Application;

import com.kokozu.net.cache.RequestCacheManager;

/**
 * @author wuzhen
 * @version Version 1.0, 2016-04-19
 */
public class ScanApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RequestCacheManager.init(this, 1);
    }
}
