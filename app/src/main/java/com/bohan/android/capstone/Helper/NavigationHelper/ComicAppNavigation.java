package com.bohan.android.capstone.Helper.NavigationHelper;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Bo Han.
 */
public class ComicAppNavigation {
    public static final int ISSUES = 0;
    public static final int VOLUMES = 1;
    public static final int CHARACTERS = 2;
    public static final int COLLECTION = 3;
    public static final int TRACKER = 4;
    public static final int SETTINGS = 5;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({ISSUES, VOLUMES, CHARACTERS, COLLECTION, TRACKER, SETTINGS})
    public @interface Section {

    }
}