package com.bohan.android.capstone.Helper.NavigationHelper;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Bo Han.
 */
public interface NavigationActivityView extends MvpView {

    void chooseItem(int chosenMenuItem);

    void navigation();
}

