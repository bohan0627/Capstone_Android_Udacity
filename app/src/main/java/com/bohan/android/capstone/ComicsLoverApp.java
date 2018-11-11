package com.bohan.android.capstone;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bo Han.
 */
public class ComicsLoverApp extends Application {
    private static ComicsLoverAppComponent comicserAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (comicserAppComponent == null) {
            comicserAppComponent = DaggerComicsLoverAppComponent.builder()
                    .comicserAppModule(new ComicsLoverAppModule(this))
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

    public static ComicserAppComponent getAppComponent() {
        return comicserAppComponent;
    }
}
