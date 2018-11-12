package com.bohan.android.capstone;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bo Han.
 */


@Component(modules = {ComicsLoverAppModule.class, ComicPrefsHelperModule.class})
@Singleton
public class ComicsLoverAppComponent {
}
