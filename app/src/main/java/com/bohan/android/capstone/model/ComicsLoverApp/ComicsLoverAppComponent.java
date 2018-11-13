package com.bohan.android.capstone.model.ComicsLoverApp;

import com.bohan.android.capstone.Helper.NavigationHelper.NavigationActivity;
import com.bohan.android.capstone.model.Prefs.ComicPrefsHelperModule;
import com.bohan.android.capstone.model.data.Local.ComicLocalWidgetComponent;
import com.bohan.android.capstone.model.data.Local.ComicsLocalDBHelperComponent;
import com.bohan.android.capstone.model.data.Local.ComicsLocalDBHelperModule;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceComponent;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bo Han.
 */


@Component(modules = {ComicsLoverAppModule.class, ComicPrefsHelperModule.class})
@Singleton
public interface ComicsLoverAppComponent {

    ComicsLocalDBHelperComponent plusDbHelperComponent(ComicsLocalDBHelperModule localDBModule);

    ComicRemoteSourceComponent plusRemoteComponent(ComicRemoteSourceModule remoteSourceModule);

    ComicLocalWidgetComponent plusWidgetComponent();

    void inject(NavigationActivity activity);
}
