package com.bohan.android.capstone.MVP.CharacterDetails;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@CharacterDetailsScope
@Subcomponent
public interface CharacterDetailsComponent {

    CharacterDetailsPresenter characterDetailsPresenter();

    void inject(CharacterDetailsFragment characterDetailsfragment);
}
