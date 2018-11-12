package com.bohan.android.capstone.MVP.CharacterDetails;

import com.bohan.android.capstone.model.data.ComicRemoteSource;
import com.bohan.android.capstone.model.ComicModel.ComicCharacter;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Bo Han.
 */
@SuppressWarnings( "deprecation" )
public class CharacterDetailsPresenter extends MvpBasePresenter<CharacterDetailsView> {

    private final ComicRemoteSource remoteSource;

    @Inject
    CharacterDetailsPresenter(ComicRemoteSource remoteSource){
        this.remoteSource = remoteSource;
    }

    public void getCharacterDetailsById(long characterId) {
        remoteSource
                .characterDetailsByCharacterId(characterId).subscribe(characterDetailsObserver());
    }

    private SingleObserver<ComicCharacter> characterDetailsObserver() {
        return new SingleObserver<ComicCharacter>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Timber.d("Start fetching character details...");
                getView().showLoading(true);
            }

            @Override
            public void onSuccess(@NonNull ComicCharacter comicCharacter) {
                Timber.d("Finished fetching character details.");
                if (isViewAttached()) {
                    getView().showLoading(false);
                    getView().setData(comicCharacter);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.d("Error occured when fetching character details: " + e.getMessage());
                getView().showError(e, false);
            }
        };
    }
}
