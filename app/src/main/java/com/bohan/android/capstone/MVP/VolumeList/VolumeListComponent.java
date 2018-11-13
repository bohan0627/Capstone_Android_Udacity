package com.bohan.android.capstone.MVP.VolumeList;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@VolumeScope
@Subcomponent
public interface VolumeListComponent {

    VolumeListPresenter volumeListPresenter();

    void inject(VolumeListFragment volumeListFragment);
}
