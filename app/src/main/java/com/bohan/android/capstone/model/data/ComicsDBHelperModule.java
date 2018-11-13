package com.bohan.android.capstone.model.data;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bo Han.
 */
@Module
public class ComicsDBHelperModule {

    @Provides
    @ComicLocalScope
    ComicsDBHelper provideComicsDBHelper(Context context) {
        return new ComicsDBHelper(context);
    }
}
