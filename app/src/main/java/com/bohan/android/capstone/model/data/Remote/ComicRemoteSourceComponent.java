package com.bohan.android.capstone.model.data.Remote;

import com.bohan.android.capstone.MVP.CharacterDetails.CharacterDetailsComponent;
import com.bohan.android.capstone.MVP.CharacterList.CharacterListComponent;
import com.bohan.android.capstone.MVP.VolumeList.VolumeListComponent;
import com.bohan.android.capstone.model.data.Local.ComicLocalSourceComponent;
import com.bohan.android.capstone.model.data.Local.ComicLocalSourceModule;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteScope;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@ComicRemoteScope
@Subcomponent(modules = {ComicRemoteSourceModule.class})
public interface ComicRemoteSourceComponent {

    ComicLocalSourceComponent plusLocalComponent(ComicLocalSourceModule module);

    VolumeListComponent plusVolumesComponent();

    CharacterListComponent plusCharacterListComponent();

    CharacterDetailsComponent plusCharacterDetailsComponent();
}
