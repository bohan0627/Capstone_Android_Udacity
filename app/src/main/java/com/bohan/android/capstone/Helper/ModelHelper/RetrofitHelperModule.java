package com.bohan.android.capstone.Helper.ModelHelper;

import com.bohan.android.capstone.model.data.Remote.ComicsDataService;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteScope;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
@Module
public class RetrofitHelperModule {

    @Provides
    @NonNull
    @ComicRemoteScope
    Retrofit provideRetrofit(@NonNull OkHttpClient client, @NonNull Gson gson) {

        return new Retrofit.Builder()
                .baseUrl(ComicsDataService.ENDPOINT)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
