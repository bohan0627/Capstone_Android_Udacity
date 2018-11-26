package com.bohan.android.capstone.Helper.ModelHelper;

import com.bohan.android.capstone.model.data.Remote.ComicRemoteScope;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import dagger.Module;
import dagger.Provides;
import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;

/**
 * Created by Bo Han.
 */

@SuppressWarnings("WeakerAccess")
@Module
public class NetworkHelperModule  {

    @Provides
    @NonNull
    @ComicRemoteScope
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }
}