package com.bohan.android.capstone.MVP.CharacterList;

import com.bohan.android.capstone.model.ComicModel.ComicCharacterList;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Bo Han.
 * Using Mosby3: MVP + ViewState + LCE Views
 */
public interface CharacterView  extends MvpLceView<List<ComicCharacterList>> {
    void characterByName(String characterName);

    void displayBaseView(boolean instruction);

    void displayEmptyView(boolean instruction);

    void setTitle(String setTitle);
}
