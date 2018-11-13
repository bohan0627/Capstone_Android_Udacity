package com.bohan.android.capstone.MVP.IssuelocalAsset;

import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.data.ComicLocalSource;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Bo Han.
 * This is for the Local owned issue
 */
@SuppressWarnings("deprecation")
public class IssueLocalPresenter extends MvpBasePresenter<IssueLocalView> {

    private final ComicLocalSource localSource;

    @Inject
    IssueLocalPresenter(ComicLocalSource localSource) {
        this.localSource = localSource;
    }

    void fetchLocalIssue() {
        localSource
                .localIssueFromDB()
                .subscribe(getObserver());
    }

    void fetchLocalIssueByName(String issueName) {
        localSource.localIssueFromDB()
                .flatMapObservable(Observable::fromIterable)
                .filter(issue -> issue.volume() != null)
                .filter(issue -> issue.volume().volumeName().contains(issueName))
                .toList()
                .subscribe(getObserver());
    }

    private SingleObserver<List<ComicIssueList>> getObserver() {

        return new SingleObserver<List<ComicIssueList>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                getView().displayEmptyView(false);
                getView().showLoading(true);
            }

            @Override
            public void onSuccess(@NonNull List<ComicIssueList> list) {
                if (isViewAttached()) {
                    if (list.size() > 0) {
                        Timber.d("Showing data...");
                        getView().setData(list);
                        getView().showContent();
                    } else {
                        Timber.d("Showing empty view...");
                        getView().showLoading(false);
                        getView().displayEmptyView(true);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.d("Error occured when fetching data: " + e.getMessage());
                if (isViewAttached()) {
                    Timber.d("Showing error view...");
                    getView().showError(e, false);
                }
            }};
    }
}

