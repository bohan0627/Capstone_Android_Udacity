package com.bohan.android.capstone;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bo Han.
 */


@Component(modules = {ComicsLoverAppModule.class, ComicPreferencesHelperModule.class})
@Singleton
public class ComicsLoverAppComponent {
}
