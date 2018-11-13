package com.bohan.android.capstone.MVP.VolumeList;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@VolumeListScope
@Subcomponent
public interface VolumeListComponent {

    VolumeListPresenter volumeListPresenter();

    void inject(VolumeListFragment volumeListFragment);
}
