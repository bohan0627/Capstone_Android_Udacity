package com.bohan.android.capstone.model.data;

import android.content.ContentResolver;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bo Han.
 */
@Module
public class ComicLocalSourceModule {

    @Provides
    @ComicLocalScope
    ComicLocalDataHelper provideComicLocalDataHelper(ContentResolver resolver) {
        return new ComicLocalDataHelper(resolver);
    }

    @Provides
    @LocalDataScope
    ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }
}
