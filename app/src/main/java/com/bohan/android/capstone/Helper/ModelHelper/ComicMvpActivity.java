package com.bohan.android.capstone.Helper.ModelHelper;

import android.os.Bundle;

import com.evernote.android.state.StateSaver;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 */
@SuppressWarnings({"deprecation", "WeakerAccess", "EmptyMethod"})
public abstract class ComicMvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        StateSaver.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateSaver.saveInstanceState(this, outState);
    }

    protected void injectDependencies() {

    }
}
