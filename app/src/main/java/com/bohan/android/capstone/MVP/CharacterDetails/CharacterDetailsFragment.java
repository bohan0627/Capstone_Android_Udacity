package com.bohan.android.capstone.MVP.CharacterDetails;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicLceFragment;
import com.bohan.android.capstone.Helper.Utils.ImageUtils;
import com.bohan.android.capstone.Helper.Utils.NetworkUtils;
import com.bohan.android.capstone.Helper.Utils.TextUtils;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicCharacter;
import com.bohan.android.capstone.model.ComicModel.ComicOrigin;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceModule;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
@FragmentWithArgs
public class CharacterDetailsFragment extends
        ComicLceFragment<CardView, ComicCharacter, CharacterDetailsView, CharacterDetailsPresenter>
        implements CharacterDetailsView {

    @Arg
    long characterId;

    @BindView(R.id.character_details_screen)
    ImageView characterPoster;
    @BindView(R.id.character_details_name)
    TextView characterName;
    @BindView(R.id.character_detail_real_name)
    TextView characterRealName;
    @BindView(R.id.character_detail_aliases)
    TextView characterAliases;
    @BindView(R.id.character_detail_birthdate)
    TextView characterBirthdate;
    @BindView(R.id.character_detail_origin)
    TextView characterOrigin;
    @BindView(R.id.character_detail_gender)
    TextView characterGender;
    @BindView(R.id.character_details_description)
    TextView characterDescription;

    private CharacterDetailsComponent characterDetailsComponent;
    private ComicCharacter currentCharacterInfo;

    // --- FRAGMENT LIFECYCLE ---

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);

        if (savedInstanceState != null) {
            loadData(false);
        }
    }

    // --- BASE LCE FRAGMENT ---

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_character_details;
    }

    @NonNull
    @Override
    public CharacterDetailsPresenter createPresenter() {
        return characterDetailsComponent.characterDetailsPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    protected void injectDependencies() {
        characterDetailsComponent = ComicsLoverApp.getAppComponent()
                .plusRemoteComponent(new ComicRemoteSourceModule())
                .plusCharacterDetailsComponent();

        characterDetailsComponent.inject(this);
    }

    // --- MVP VIEW STATE ---

    @Override
    public ComicCharacter getData() {
        return currentCharacterInfo;
    }

    @NonNull
    @Override
    public LceViewState<ComicCharacter, CharacterDetailsView> createViewState() {
        return new RetainingLceViewState<>();
    }

    // --- MVP VIEW ---


    @Override
    public void showContent() {
        contentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        errorView.setText(R.string.error_issue_not_available);
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (pullToRefresh) {
            contentView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
        } else {
            contentView.setVisibility(View.VISIBLE);
            errorView.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(ComicCharacter data) {
        currentCharacterInfo = data;
        bindVolumeToUi(currentCharacterInfo);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.getCharacterDetailsById(characterId);
    }

    // --- UI BINDING UTILS ---

    private void bindVolumeToUi(ComicCharacter character) {
        loadHeaderImage(characterPoster, character.characterMainImage());
        setUsualText(characterName, character.characterName());
        setUsualText(characterRealName, character.characterRealName());
        setUsualText(characterAliases, character.characterAliases());
        setUsualText(characterBirthdate, character.characterBirth());
        setOrigin(characterOrigin, character.characterOrigin());
        setGender(characterGender, character.characterGender());
        setDescription(characterDescription, character.characterDescription());
    }

    private void loadHeaderImage(ImageView header, ComicImageHelper imageHelper) {
        if (imageHelper != null) {
            String imageUrl = imageHelper.imageSmallUrl();
            ImageUtils.imageWithCropOnTop(header, imageUrl);
        } else {
            header.setVisibility(View.GONE);
        }
    }

    private void setUsualText(TextView textView, String text) {
        if (text != null) {
            textView.setText(text);
        } else {
            textView.setText("-");
        }
    }

    private void setOrigin(TextView textView, ComicOrigin origin) {
        if (origin != null) {
            textView.setText(origin.originName());
        } else {
            textView.setText("-");
        }
    }

    private void setGender(TextView textView, int gender) {
        if (gender == 1 ) {
            textView.setText(R.string.character_details_gender_male);
        } else if (gender == 2){
            textView.setText(R.string.character_details_gender_female);
        } else {
            textView.setText(R.string.character_details_gender_unknown);
        }
    }

    private void setDescription(TextView textView, String description) {
        if (description != null) {
            textView.setText(TextUtils.spannedHtmlText(description));
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}

