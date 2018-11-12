package com.bohan.android.capstone.MVP.VolumeList;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@VolumeScope
@Subcomponent
public interface VolumeComponent {

    VolumePresenter volumePresenter();

    void inject(VolumeFragment volumeFragment);
}
