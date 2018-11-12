package com.bohan.android.capstone.MVP.IssuelocalAsset;

import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Bo Han.
 */
public interface IssueLocalView extends MvpLceView<List<ComicIssueList>> {


    void setIssueTitle(String issueTitle);

    void displayEmptyView(boolean instruction);

    void fetchDataByFilters(String filters);
}
