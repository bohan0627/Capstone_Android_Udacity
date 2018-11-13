package com.bohan.android.capstone.MVP.IssueDetails;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */

@IssueDetailsScope
@Subcomponent
public interface IssueDetailsComponent {

    IssueDetailsPresenter issueDetailsPresenter();

    void inject(IssueDetailsFragment issueDetailsFragment);
}

