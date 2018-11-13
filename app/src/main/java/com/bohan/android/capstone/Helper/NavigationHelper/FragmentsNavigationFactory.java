package com.bohan.android.capstone.Helper.NavigationHelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.bohan.android.capstone.Helper.ComicPrefsFragment;
import com.bohan.android.capstone.MVP.CharacterList.CharacterListFragment;
import com.bohan.android.capstone.MVP.IssueList.IssueListFragment;
import com.bohan.android.capstone.MVP.IssuelocalAsset.IssueLocalFragment;
import com.bohan.android.capstone.MVP.VolumeList.VolumeListFragment;
import com.bohan.android.capstone.MVP.VolumeUsed.VolumeUsedFragment;

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
                return new IssueListFragmentBuilder().build();
            case ComicAppNavigation.VOLUMES:
                return new VolumeListFragmentBuilder().build();
            case ComicAppNavigation.CHARACTERS:
                return new CharacterListFragmentBuilder().build();
            case ComicAppNavigation.COLLECTION:
                return new IssueLocalFragmentBuilder().build();
            case ComicAppNavigation.TRACKER:
                return new VolumeUsedFragmentBuilder().build();
            case ComicAppNavigation.SETTINGS:
                return new ComicPrefsFragmentBuilder().build();
            default:
                return null;
        }
    }

    public static String getFragmentTag(@ComicAppNavigation.Section int type) {
        switch (type) {
            case ComicAppNavigation.ISSUES:
                return IssueListFragment.class.getSimpleName();
            case ComicAppNavigation.VOLUMES:
                return VolumeListFragment.class.getSimpleName();
            case ComicAppNavigation.CHARACTERS:
                return CharacterListFragment.class.getSimpleName();
            case ComicAppNavigation.COLLECTION:
                return IssueLocalFragment.class.getSimpleName();
            case ComicAppNavigation.TRACKER:
                return VolumeUsedFragment.class.getSimpleName();
            case ComicAppNavigation.SETTINGS:
                return ComicPrefsFragment.class.getSimpleName();
            default:
                return "";
        }
    }
}

