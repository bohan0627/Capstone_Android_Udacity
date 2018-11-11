package com.bohan.android.capstone;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Bo Han.
 */
public class ComicsLoverAppModule {

    @NonNull
    private final ComicsLoverApp comicsLoverApp;

    ComicsLoverAppModule(@NonNull  ComicsLoverApp comicsLoverApp){
        this.comicsLoverApp = comicsLoverApp;
    }

    Context provideContext(){
        return comicsLoverApp;
    }

    FirebaseAnalytics provideFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }
}
