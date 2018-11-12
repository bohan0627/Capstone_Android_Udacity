package com.bohan.android.capstone.model.Prefs;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bo Han.
 */
@Module
public class ComicPrefsHelperModule {
    @Provides
    @Singleton
    ComicPrefsHelper prefsHelper(Context context) {
        return new ComicPrefsHelper(context);
    }
}
