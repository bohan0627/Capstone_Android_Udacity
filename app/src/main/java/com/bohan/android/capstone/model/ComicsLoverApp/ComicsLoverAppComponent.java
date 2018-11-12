package com.bohan.android.capstone.model.ComicsLoverApp;

import com.bohan.android.capstone.model.Prefs.ComicPrefsHelperModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bo Han.
 */


@Component(modules = {ComicsLoverAppModule.class, ComicPrefsHelperModule.class})
@Singleton
public class ComicsLoverAppComponent {
}
