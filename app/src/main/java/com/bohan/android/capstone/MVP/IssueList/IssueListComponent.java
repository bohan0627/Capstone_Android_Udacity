package com.bohan.android.capstone.MVP.IssueList;

/**
 * Created by Bo Han.
 */

import dagger.Subcomponent;

@IssueListScope
@Subcomponent
public interface IssueListComponent {
    IssueListPresenter issueListPresenter();

    void inject(IssueListFragment issueListFragment);
}
