package com.bohan.android.capstone.MVP.IssueDetails;

import com.bohan.android.capstone.model.ComicModel.ComicIssue;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

/**
 * Created by Bo Han.
 * This extends the Mosby3
 */
public interface IssueDetailsView extends MvpLceView<ComicIssue> {
    void onClickBookmark();
    void mark();
    void unMark();
}
