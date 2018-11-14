package com.bohan.android.capstone.Helper.Utils;

import android.graphics.Point;
import android.support.annotation.IdRes;
import android.support.v7.widget.Toolbar;


import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

/**
 * Created by Bo Han.
 * The ShowcaseView (SCV) library is designed to highlight and showcase specific parts of apps
 * to the user with a distinctive and attractive overlay.
 * Please refer to https://github.com/amlcurran/ShowcaseView
 */
public class ToolbarTargetUtils implements Target {

    private final int itemId;
    private final Toolbar toolbar;


    public ToolbarTargetUtils(@IdRes int itemId, Toolbar toolbar) {
        this.itemId = itemId;
        this.toolbar = toolbar;
    }

    @Override
    public Point getPoint() {
        return new ViewTarget(toolbar.findViewById(itemId)).getPoint();
    }

}

