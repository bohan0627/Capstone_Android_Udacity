package com.bohan.android.capstone.model.ComicsLoverApp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Provides;

/**
 * Created by Bo Han.
 */
public class ComicsLoverAppModule {

    @NonNull
    private final ComicsLoverApp comicsLoverApp;

    ComicsLoverAppModule(@NonNull  ComicsLoverApp comicsLoverApp){
        this.comicsLoverApp = comicsLoverApp;
    }

    @NonNull
    @Provides
    @Singleton
    Context provideContext(){
        return comicsLoverApp;
    }

    @NonNull
    @Provides
    @Singleton
    FirebaseAnalytics provideFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }
}
