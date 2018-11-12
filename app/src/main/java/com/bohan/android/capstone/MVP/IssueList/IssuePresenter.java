package com.bohan.android.capstone.MVP.IssueList;

import android.content.Context;

import com.bohan.android.capstone.model.Prefs.ComicPrefsHelper;
import com.bohan.android.capstone.model.data.ComicRemoteSource;
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
public class IssuePresenter extends MvpBasePresenter<IssuesView> {

    final ComicPrefsHelper comicPreferencesHelper;
    final ComicLocalSource localsource;
    final ComicRemoteSource remoteSource;
    final Context context;

    @Inject
    public IssuesPresenter(
            ComicPrefsHelper comicPreferencesHelper,
            ComicLocalSource localsource,
            ComicRemoteSource  remoteSource,
            Context context) {
        this.comicPreferencesHelper = comicPreferencesHelper;
        this.localsource = localsource;
        this.remoteSource = remoteSource;
        this.context = context;
    }

    public boolean shouldNotDisplayShowcases() {
        return comicPreferencesHelper.wasIssuesViewShowcased();
    }

    public void showcaseWasDisplayed() {
        comicPreferencesHelper.markIssuesViewAsShowcased();
    }

    public void loadTodayIssues(boolean pullToRefresh) {
        if (pullToRefresh) {
            // Sync data with apps sync manager
            Timber.d("Loading and sync started...");
            loadTodayIssuesFromServer();
        } else {
            // Load and display issues from db
            Timber.d("Loading issues from db started...");
            loadTodayIssuesFromDB();
        }
    }

    public void loadTodayIssuesFromServer() {

        if (NetworkUtils.isNetworkConnected(context)) {
            ComicSyncManager.syncImmediately();
        } else {
            Timber.d("Network is not available!");
            if (isViewAttached()) {
                getView().showLoading(false);
                getView().showContent();
                getView().showErrorToast(context.getString(R.string.error_data_not_available_short));
            }
        }
    }

    public void loadTodayIssuesFromDB() {
        localDataHelper
                .getTodayIssuesFromDb()
                .subscribe(getObserver());
    }

    public void loadIssuesByDate(String date) {
        Timber.d("Load issues by date: " + date);
        remoteDataHelper
                .getIssuesListByDate(date)
                .subscribe(getObserverFiltered(date, true));
    }

    public void loadIssuesByDateAndName(String date, String name) {
        Timber.d("Load issues by date: " + date + " and name: " + name);
        remoteDataHelper
                .getIssuesListByDate(date)
                .flatMapObservable(Observable::fromIterable)
                .filter(issue -> issue.volume() != null)
                .filter(issue -> issue.volume().name().contains(name))
                .toList()
                .subscribe(getObserverFiltered(name, false));
    }

    private SingleObserver<List<ComicIssueList>> getObserver() {
        return new SingleObserver<List<ComicIssueList>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Data loading started...");
                if (isViewAttached()) {
                    getView().showEmptyView(false);
                    getView().showLoading(true);
                }
            }

            @Override
            public void onSuccess(@NonNull List<ComicIssueList> list) {

                if (isViewAttached()) {
                    getView().setTitle(DateTextUtils.getFormattedDateToday());

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

    private SingleObserver<List<ComicIssueList>> getObserverFiltered(String str, boolean isDate) {
        return new SingleObserver<List<ComicIssueList>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Issues data loading started...");
                if (isViewAttached()) {
                    getView().showEmptyView(false);
                    getView().showLoading(true);
                }
            }

            @Override
            public void onSuccess(@NonNull List<ComicIssueList> list) {

                if (isViewAttached()) {
                    String title = isDate ? DateTextUtils.getFormattedDate(str, "MMM d, yyyy") : str;
                    getView().setTitle(title);

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
