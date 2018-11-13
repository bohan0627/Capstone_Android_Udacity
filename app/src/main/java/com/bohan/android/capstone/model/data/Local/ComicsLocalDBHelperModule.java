package com.bohan.android.capstone.model.data.Local;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bo Han.
 */
@Module
public class ComicsLocalDBHelperModule {
    @Provides
    @ComicLocalScope
    ComicsDBHelper provideComicDBHelper(Context context) {
        return new ComicsDBHelper(context);
    }
}

