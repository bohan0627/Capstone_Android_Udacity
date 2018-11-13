package com.bohan.android.capstone.MVP.VolumeDetails;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@VolumeDetailsScope
@Subcomponent
public interface VolumeDetailsComponent {
    VolumeDetailsPresenter volumeDetailsPresenter();

    void inject(VolumeDetailsFragment volumeDetailsFragment);
}
