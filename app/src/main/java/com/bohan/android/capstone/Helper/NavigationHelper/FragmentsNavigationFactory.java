package com.bohan.android.capstone.Helper.NavigationHelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Bo Han.
 */
public class FragmentsNavigationFactory {
    public static Fragment getFragment(FragmentManager manager, @ComicAppNavigation.Section int type) {

        Fragment fragment = manager.findFragmentByTag(getFragmentTag(type));

        if (fragment != null) {
            return fragment;
        }

        switch (type) {
            case ComicAppNavigation.ISSUES:
                return new IssuesFragmentBuilder().build();
            case ComicAppNavigation.VOLUMES:
                return new VolumesFragmentBuilder().build();
            case ComicAppNavigation.CHARACTERS:
                return new CharactersFragmentBuilder().build();
            case ComicAppNavigation.COLLECTION:
                return new OwnedIssuesFragmentBuilder().build();
            case ComicAppNavigation.TRACKER:
                return new VolumesTrackerFragmentBuilder().build();
            case ComicAppNavigation.SETTINGS:
                return new ComicPrefsFragmentBuilder().build();
            default:
                return null;
        }
    }

    public static String getFragmentTag(@AppNavigation.Section int type) {
        switch (type) {
            case ComicAppNavigation.ISSUES:
                return IssuesFragment.class.getSimpleName();
            case ComicAppNavigation.VOLUMES:
                return VolumesFragment.class.getSimpleName();
            case ComicAppNavigation.CHARACTERS:
                return CharactersFragment.class.getSimpleName();
            case ComicAppNavigation.COLLECTION:
                return OwnedIssuesFragment.class.getSimpleName();
            case ComicAppNavigation.TRACKER:
                return VolumesTrackerFragment.class.getSimpleName();
            case ComicAppNavigation.SETTINGS:
                return ComicPreferencesFragment.class.getSimpleName();
            default:
                return "";
        }
    }
}

