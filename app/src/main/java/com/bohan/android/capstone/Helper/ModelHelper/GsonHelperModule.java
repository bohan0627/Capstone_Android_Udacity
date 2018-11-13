package com.bohan.android.capstone.Helper.ModelHelper;

import com.bohan.android.capstone.Helper.Utils.GsonFactoryUtils;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteScope;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;

/**
 * Created by Bo Han.
 */
@Module
public class GsonHelperModule {

    @Provides
    @NonNull
    @ComicRemoteScope
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(GsonFactoryUtils.create())
                .create();
    }
}
