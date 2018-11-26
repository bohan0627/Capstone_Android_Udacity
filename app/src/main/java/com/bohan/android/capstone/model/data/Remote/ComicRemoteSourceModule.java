package com.bohan.android.capstone.model.data.Remote;

import com.bohan.android.capstone.Helper.ModelHelper.GsonHelperModule;
import com.bohan.android.capstone.Helper.ModelHelper.NetworkHelperModule;
import com.bohan.android.capstone.Helper.ModelHelper.RetrofitHelperModule;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Bo Han.
 */

@Module(includes = {GsonHelperModule.class, NetworkHelperModule.class, RetrofitHelperModule.class})
public class ComicRemoteSourceModule {

    @Provides
    @ComicRemoteScope
    ComicsDataService provideComicVineService(Retrofit retrofit) {
        return retrofit.create(ComicsDataService.class);
    }

    @Provides
    @ComicRemoteScope
    ComicRemoteSourceHelper provideComicRemoteSourceHelper(ComicsDataService service) {
        return new ComicRemoteSourceHelper(service);
    }
}
