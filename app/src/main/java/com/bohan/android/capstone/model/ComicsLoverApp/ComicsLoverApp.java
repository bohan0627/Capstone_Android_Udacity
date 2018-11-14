package com.bohan.android.capstone.model.ComicsLoverApp;

import android.app.Application;
import android.content.Context;

import com.bohan.android.capstone.BuildConfig;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverAppComponent;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverAppModule;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Bo Han.
 */
public class ComicsLoverApp extends Application {
    private static ComicsLoverAppComponent comicsLoverAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (comicsLoverAppComponent == null) {
            comicsLoverAppComponent = DaggerComicsLoverAppComponent.builder()
                    .comicsLoverAppModule(new ComicsLoverAppModule(this))
                    .build();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // We should not init our app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code below.

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());

            Stetho.initializeWithDefaults(this);
        }
    }

    public static ComicsLoverAppComponent getAppComponent() {
        return comicsLoverAppComponent;
    }
}
