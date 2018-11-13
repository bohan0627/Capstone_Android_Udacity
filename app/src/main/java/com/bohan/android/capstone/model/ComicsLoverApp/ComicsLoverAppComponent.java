package com.bohan.android.capstone.model.ComicsLoverApp;

import com.bohan.android.capstone.Helper.NavigationHelper.NavigationActivity;
import com.bohan.android.capstone.model.Prefs.ComicPrefsHelperModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bo Han.
 */


@Component(modules = {ComicsLoverAppModule.class, ComicPrefsHelperModule.class})
@Singleton
public class ComicsLoverAppComponent {

    ComicDbHelperComponent plusDbHelperComponent(ComicDbHelperModule module);

    ComicRemoteDataComponent plusRemoteComponent(ComicRemoteDataModule module);

    ComicWidgetComponent plusWidgetComponent();

    void inject(NavigationActivity activity);
}
