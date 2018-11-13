package com.bohan.android.capstone.model.data.Local;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */

@ComicLocalScope
@Subcomponent(modules = {ComicsLocalDBHelperModule.class})
public interface ComicsLocalDBHelperComponent {
    void inject(ComicProvider provider);
}