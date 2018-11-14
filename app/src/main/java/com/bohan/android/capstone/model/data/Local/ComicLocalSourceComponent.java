package com.bohan.android.capstone.model.data.Local;

import com.bohan.android.capstone.Helper.SyncHelper.SyncAdapter;
import com.bohan.android.capstone.MVP.IssueDetails.IssueDetailsComponent;
import com.bohan.android.capstone.MVP.IssueList.IssueListComponent;
import com.bohan.android.capstone.MVP.IssuelocalAsset.IssueLocalComponent;
import com.bohan.android.capstone.MVP.VolumeDetails.VolumeDetailsComponent;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@ComicLocalScope
@Subcomponent(modules = {ComicLocalSourceModule.class})
public interface ComicLocalSourceComponent {

    IssueListComponent plusIssueListComponent();

    IssueDetailsComponent plusIssueDetailsComponent();

    VolumeDetailsComponent plusVolumeDetailsComponent();

    IssueLocalComponent plusLocalIssueComponent();

    void inject(SyncAdapter adapter);
}
