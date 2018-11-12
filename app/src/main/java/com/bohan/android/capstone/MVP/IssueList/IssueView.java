package com.bohan.android.capstone.MVP.IssueList;

import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Bo Han.
 */
public interface IssueView extends MvpLceView<List<ComicIssueList>> {

    void displayEmptyView(boolean instruction);
    void fetchIssueByDate(String date);
    void fetchIssueByFilters(String filters);
    void chooseFetchIssue();
    void dispalyErrorMessage(String message);
    void setTitle(String title);
}
