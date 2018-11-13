package com.bohan.android.capstone.model.data;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@ComicLocalScope
@Subcomponent(modules = {ComicsDBHelperModule.class})
public interface ComicDbHelperComponent {
    void inject(ComicProvider provider);
}
