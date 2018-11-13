package com.bohan.android.capstone.MVP.CharacterList;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@CharacterListScope
@Subcomponent
public interface CharacterListComponent {

    CharacterListPresenter characterListPresenter();

    void inject(CharacterListFragment characterListFragment);
}
