package com.bohan.android.capstone.MVP.IssueDetails;

import com.bohan.android.capstone.Helper.Utils.ContentUtils;
import com.bohan.android.capstone.model.data.ComicRemoteSource;
import com.bohan.android.capstone.model.ComicModel.ComicIssue;
import com.bohan.android.capstone.model.data.ComicLocalSource;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Bo Han.
 */
@SuppressWarnings({"deprecation", "WeakerAccess"})
public class IssueDetailsPresenter  extends MvpBasePresenter<IssueDetailsView> {

    public final ComicLocalSource localSource;
    public final ComicRemoteSource remoteSource;

    @Inject
    IssueDetailsPresenter(ComicLocalSource localSource, ComicRemoteSource remoteSource) {
        this.localSource = localSource;
        this.remoteSource = remoteSource;
    }

    void setBookmarkState(long issueId) {
        if (isViewAttached()) {
            if (isCurrentIssueBookmarked(issueId))
                getView().mark();
            else
                getView().unMark();
        }
    }

    boolean isCurrentIssueBookmarked(long issueId) {
        return localSource.isIssueMarked(issueId);
    }

    void addBookmarkIssue(ComicIssue issue) {
        localSource.localIssueToDB(ContentUtils.issueWithShortInfo(issue));
    }

    void removeBookmarkIssue(long issueId) {
        localSource.deleteLocalIssueFromDB(issueId);
    }

    void fetchIssueDetails(long issueId) {
        remoteSource.issueDetailsByIssueId(issueId).subscribe(getIssueDetailsObserver());
    }

    private SingleObserver<ComicIssue> getIssueDetailsObserver() {
        return new SingleObserver<ComicIssue>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Fetching issue details...");
                getView().showLoading(true);
            }

            @Override
            public void onSuccess(@NonNull ComicIssue comicIssueInfo) {
                Timber.d("Finished fetching issue details.");
                if (isViewAttached()) {
                    getView().showLoading(false);
                    getView().setData(comicIssueInfo);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.d("Error occured when fetching issue details: " + e.getMessage());
                getView().showError(e, false);
            }
        };
    }
}

