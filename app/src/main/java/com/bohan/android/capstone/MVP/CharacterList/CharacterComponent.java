package com.bohan.android.capstone.MVP.CharacterList;

import com.bohan.android.capstone.MVP.CharacterList.CharacterFragment;
import com.bohan.android.capstone.MVP.CharacterList.CharacterPresenter;
import com.bohan.android.capstone.MVP.CharacterList.CharacterScope;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@CharacterScope
@Subcomponent
public interface CharacterComponent {

    CharacterPresenter characterPresenter();

    void inject(CharacterFragment characterFragment);
}
