package com.bohan.android.capstone.MVP.VolumeDetails;

import com.bohan.android.capstone.Helper.Utils.ContentUtils;
import com.bohan.android.capstone.model.ComicModel.ComicVolume;
import com.bohan.android.capstone.model.data.Local.ComicLocalSourceHelper;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceHelper;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("deprecation")
public class VolumeDetailsPresenter extends MvpBasePresenter<VolumeDetailsView> {

    private final ComicLocalSourceHelper localDataHelper;
    private final ComicRemoteSourceHelper remoteDataHelper;

    @Inject
    VolumeDetailsPresenter(
            ComicLocalSourceHelper localDataHelper,
            ComicRemoteSourceHelper remoteDataHelper) {
        this.localDataHelper = localDataHelper;
        this.remoteDataHelper = remoteDataHelper;
    }

    boolean ifTargetIssueOwned(long issueId) {
        return localDataHelper.isIssueMarked(issueId);
    }

    void setUpTrackIconState(long volumeId) {
        if (isViewAttached()) {
            if (isCurrentVolumeTracked(volumeId)) {
                getView().mark();
            } else {
                getView().unMark();
            }
        }
    }

    boolean isCurrentVolumeTracked(long volumeId) {
        return localDataHelper.isVolumeLocal(volumeId);
    }

    void trackVolume(ComicVolume volume) {
        localDataHelper.localVolumeToDB(ContentUtils.volumeWithShortInfo(volume));
    }

    void removeTracking(long volumeId) {
        localDataHelper.deleteLocalVolumeFromDB(volumeId);
    }

    void loadVolumeDetails(long volumeId) {
        remoteDataHelper
                .getVolumeDetailsById(volumeId)
                .subscribe(getVolumeDetailsObserver());
    }

    private SingleObserver<ComicVolume> getVolumeDetailsObserver() {
        return new SingleObserver<ComicVolume>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Volume details loading started...");
                getView().showLoading(true);
            }

            @Override
            public void onSuccess(@NonNull ComicVolume comicVolumeInfo) {
                Timber.d("Volume details loading completed.");
                if (isViewAttached()) {
                    getView().showLoading(false);
                    getView().setData(comicVolumeInfo);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.d("Volume details loading error: " + e.getMessage());
                getView().showError(e, false);
            }
        };
    }
}

