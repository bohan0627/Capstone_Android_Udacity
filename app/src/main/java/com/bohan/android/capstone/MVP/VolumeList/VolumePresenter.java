package com.bohan.android.capstone.MVP.VolumeList;

import android.os.Bundle;

import com.bohan.android.capstone.model.data.ComicRemoteSource;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("deprecation")
public class VolumePresenter extends MvpBasePresenter<VolumeView> {

    private final FirebaseAnalytics firebaseAnalytics;
    private final ComicRemoteSource remoteSource;

    @Inject
    VolumePresenter(FirebaseAnalytics firebaseAnalytics, ComicRemoteSource remoteSource) {
        this.firebaseAnalytics = firebaseAnalytics;
        this.remoteSource = remoteSource;
    }


    void fetchVolumeByName(String volumeName) {
        Timber.d("Fetching volume by name: " + volumeName);
        remoteSource.volumeListByVolumeName(volumeName)
                .subscribe(getObserver());
    }

    void logVolumeSearchEvent(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(Param.ITEM_NAME, name);
        bundle.putString(Param.CONTENT_TYPE, "volume");
        firebaseAnalytics.logEvent(Event.SEARCH, bundle);
    }

    private SingleObserver<List<ComicVolumeList>> getObserver() {
        return new SingleObserver<List<ComicVolumeList>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Fetching volume data...");
                if (isViewAttached()) {
                    Timber.d("Showing fetching view...");
                    getView().displayEmptyView(false);
                    getView().displayBaseView(false);
                    getView().showLoading(true);
                }
            }

            @Override
            public void onSuccess(@NonNull List<ComicVolumeList> volumeList) {
                if (isViewAttached()) {
                    if (volumeList.size() > 0) {
                        // Display content
                        Timber.d("Showing info...");
                        getView().setData(volumeList);
                        getView().showContent();
                    } else {
                        // Display empty view
                        Timber.d("Showing empty view...");
                        getView().displayEmptyView(true);
                        getView().showLoading(false);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable error) {
                Timber.d("Volumes data loading error: " + error.getMessage());
                if (isViewAttached()) {
                    Timber.d("Displaying error view...");
                    getView().showError(error, false);
                    getView().showLoading(false);
                }
            }
        };
    }
}

