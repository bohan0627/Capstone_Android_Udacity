package com.bohan.android.capstone.Helper.ModelHelper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.evernote.android.state.StateSaver;

import butterknife.ButterKnife;
import android.support.annotation.Nullable;

/**
 * Created by Bo Han.
 */

@SuppressWarnings({"deprecation", "WeakerAccess", "EmptyMethod"})
@SuppressLint("Registered")
public class ComicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //injectDependencies();
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
