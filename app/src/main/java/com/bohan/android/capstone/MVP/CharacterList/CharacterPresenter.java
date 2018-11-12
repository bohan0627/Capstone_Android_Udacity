package com.bohan.android.capstone.MVP.CharacterList;

import android.os.Bundle;

import com.bohan.android.capstone.model.data.ComicRemoteSource;
import com.bohan.android.capstone.model.ComicModel.ComicCharacterList;
import com.google.firebase.analytics.FirebaseAnalytics;
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
@SuppressWarnings({"deprecation","WeakerAccess"})
public class CharacterPresenter extends MvpBasePresenter<CharacterView> {

    final FirebaseAnalytics firebaseAnalytics;
    final ComicRemoteSource remoteSource;

    @Inject
    CharacterPresenter(FirebaseAnalytics firebaseAnalytics,
                        ComicRemoteSource remoteSource) {
        this.firebaseAnalytics = firebaseAnalytics;
        this.remoteSource = remoteSource;
    }

    void loadCharactersData(String characterName) {
        Timber.d("Fetching character by name: " + characterName);
        remoteSource.characterListByCharacterName(characterName).subscribe(getObserver());
    }

    void logCharacterSearchEvent(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "character");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }

    private SingleObserver<List<ComicCharacterList>> getObserver() {
        return new SingleObserver<List<ComicCharacterList>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Fetching character data...");
                if (isViewAttached()) {
                    Timber.d("Showing fetching view...");
                    getView().displayEmptyView(false);
                    getView().displayBaseView(false);
                    getView().showLoading(true);
                }
            }

            @Override
            public void onSuccess(@NonNull List<ComicCharacterList> list) {
                if (isViewAttached()) {
                    if (list.size() > 0) {
                        // Display content
                        Timber.d("Displaying Character info...");
                        getView().setData(list);
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
            public void onError(@NonNull Throwable e) {
                Timber.d("Error occured when fetching character data: " + e.getMessage());
                if (isViewAttached()) {
                    Timber.d("Showing error view...");
                    getView().showError(e, false);
                    getView().showLoading(false);
                }
            }
        };
    }
}
