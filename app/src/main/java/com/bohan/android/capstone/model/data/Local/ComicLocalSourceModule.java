package com.bohan.android.capstone.model.data.Local;

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
    ComicLocalSourceHelper provideComicLocalDataHelper(ContentResolver resolver) {
        return new ComicLocalSourceHelper(resolver);
    }

    @Provides
    @ComicLocalScope
    ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }
}
