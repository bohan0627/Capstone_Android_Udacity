package com.bohan.android.capstone.MVP.IssuelocalAsset;

import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.data.ComicLocalSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Bo Han.
 */
public class IssueLocalPresenter extends MvpBasePresenter<IssuelocalView> {

    private final ComicLocalSource localSource;

    @Inject
    IssueLocalPresenter(ComicLocalSource localSource) {
        this.localSource = localSource;
    }

    void loadOwnedIssues() {
        localSource
                .getOwnedIssuesFromDb()
                .subscribe(getObserver());
    }

    void loadOwnedIssuesFilteredByName(String name) {
        localSource
                .getOwnedIssuesFromDb()
                .flatMapObservable(Observable::fromIterable)
                .filter(issue -> issue.volume() != null)
                .filter(issue -> issue.volume().name().contains(name))
                .toList()
                .subscribe(getObserver());
    }

    private SingleObserver<List<ComicIssueList>> getObserver() {

        return new SingleObserver<List<ComicIssueList>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                getView().showEmptyView(false);
                getView().showLoading(true);
            }

            @Override
            public void onSuccess(@NonNull List<ComicIssueList> list) {

                if (isViewAttached()) {
                    if (list.size() > 0) {
                        // Display content
                        Timber.d("Displaying content...");
                        getView().setData(list);
                        getView().showContent();
                    } else {
                        // Display empty view
                        Timber.d("Displaying empty view...");
                        getView().showLoading(false);
                        getView().showEmptyView(true);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.d("Data loading error: " + e.getMessage());
                if (isViewAttached()) {
                    Timber.d("Displaying error view...");
                    getView().showError(e, false);
                }
            }
        };
    }
}

