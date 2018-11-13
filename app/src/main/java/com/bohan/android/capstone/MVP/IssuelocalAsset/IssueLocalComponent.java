package com.bohan.android.capstone.MVP.IssuelocalAsset;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@IssueLocalScope
@Subcomponent
public interface IssueLocalComponent {
    IssueLocalPresenter issueLocalPresenter();

    void inject(IssueLocalFragment issueLocalFragment);
}
