package com.bohan.android.capstone.MVP.IssueDetails;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicLceFragment;
import com.bohan.android.capstone.Helper.Utils.ImageUtils;
import com.bohan.android.capstone.Helper.Utils.TextUtils;
import com.bohan.android.capstone.Helper.Utils.ViewUtils;
import com.bohan.android.capstone.MVP.CharacterDetails.CharacterDetailsActivity;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicCharacterShort;
import com.bohan.android.capstone.model.ComicModel.ComicIssue;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.data.Local.ComicLocalSourceModule;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceModule;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static java.security.AccessController.getContext;

/**
 * Created by Bo Han.
 */
public class IssueDetailsFragment  extends ComicLceFragment<LinearLayout, ComicIssue, IssueDetailsView, IssueDetailsPresenter>
        implements IssueDetailsView {

    @Arg
    long issueId;

    @BindView(R.id.issue_details_screen)
    ImageView issueScreen;
    @BindView(R.id.issue_details_full_name)
    TextView issueFullTitleName;
    @BindView(R.id.issue_details_issue_name)
    TextView issueSeparateName;
    @BindView(R.id.issue_details_cover_date)
    TextView issueCoverDate;
    @BindView(R.id.issue_details_store_date)
    TextView issueStoreDate;
    @BindView(R.id.issue_details_description)
    TextView issueDescription;
    @BindView(R.id.issue_details_characters_card)
    CardView charactersView;
    @BindView(R.id.issue_details_characters_list)
    ListView charactersList;

    @BindString(R.string.msg_bookmarked)
    String messageBookmarked;
    @BindString(R.string.msg_bookmark_removed)
    String messageBookmarkRemoved;
    @BindBool(R.bool.is_tablet_layout)
    boolean twoPaneMode;

    private IssueDetailsComponent issueDetailsComponent;
    private ComicIssue currentIssue;
    private IssueCharacterAdapter listAdapter;
    private Menu currentMenu;

    // --- FRAGMENT LIFECYCLE ---

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        setHasOptionsMenu(true);

        listAdapter = new IssueCharacterAdapter(new ArrayList<>(0));

        charactersList.setAdapter(listAdapter);

        charactersList.setDivider(
                new ColorDrawable(ContextCompat.getColor(getContext(), R.color.colorAccentDark)));

        charactersList.setDividerHeight(1);

        charactersList.setOnItemClickListener((parent, view1, position, id) -> {
            if (twoPaneMode) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Fragment fragment = new CharacterDetailsFragmentBuilder(id).build();

               ViewUtils.replaceFragment(
                        manager, fragment, R.id.content_frame, "CharacterDetailsFragment", true);
            } else {
                startActivity(CharacterDetailsActivity.prepareIntent(getContext(), id));
            }
        });

        if (savedInstanceState != null) {
            loadData(false);
        }
    }

    // --- OPTIONS MENU ---

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_issue_details, menu);

        currentMenu = menu;

        presenter.isCurrentIssueBookmarked(issueId);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bookmark:
                onBookmarkClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // --- BASE LCE FRAGMENT ---

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_issue_details;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @NonNull
    @Override
    public IssueDetailsPresenter createPresenter() {
        return issueDetailsComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        issueDetailsComponent = ComicsLoverApp.getAppComponent()
                .plusRemoteComponent(new ComicRemoteSourceModule())
                .plusLocalComponent(new ComicLocalSourceModule())
                .plusIssueDetailsComponent();
        issueDetailsComponent.inject(this);
    }
    // --- MVP VIEW STATE ---

    @Override
    public ComicIssue getData() {
        return currentIssue;
    }

    @NonNull
    @Override
    public LceViewState<ComicIssue, IssueDetailsView> createViewState() {
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
    public void setData(ComicIssue data) {
        currentIssue = data;
        bindIssueDataToUi(currentIssue);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.fetchIssueDetails(issueId);
    }

    @Override
    public void mark() {
        currentMenu.findItem(R.id.action_bookmark).setIcon(R.drawable.ic_bookmark_black_24dp);

        ViewUtils.colorMenu(getContext(), currentMenu, R.id.action_bookmark,
                R.color.material_color_white);
    }

    @Override
    public void unMark() {
        currentMenu.findItem(R.id.action_bookmark).setIcon(R.drawable.ic_bookmark_border_black_24dp);

        ViewUtils.colorMenu(getContext(), currentMenu, R.id.action_bookmark,
                R.color.material_color_white);
    }

    @Override
    public void onClickBookmark() {

        if (currentIssue == null) {
            return;
        }

        String message;

        boolean isBookmarkedNow = presenter.isCurrentIssueBookmarked(issueId);

        if (isBookmarkedNow) {
            presenter.removeBookmarkIssue(issueId);
            message = messageBookmarkRemoved;
        } else {
            presenter.addBookmarkIssue(currentIssue);
            message = messageBookmarked;
        }

        presenter.setBookmarkState(issueId);

        int parentLayoutId = (twoPaneMode) ?
                R.id.main_coordinator_layout :
                R.id.issue_details_activity_layout;

        Snackbar.make(
                ButterKnife.findById(getActivity(), parentLayoutId),
                message,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    // --- UI BINDING UTILS ---

    private void bindIssueDataToUi(ComicIssue issue) {

        loadHeaderImage(issueScreen, issue.issueMainImage());

        String volumeName = issue.volume().volumeName();
        int issueNumber = issue.issueNumber();
        setUpHeaderText(issueFullTitleName, volumeName, issueNumber);
        setUpOtherText(issueSeparateName, issue.issueName());
        setUpDate(issueCoverDate, issue.issueCoverDate());
        setUpDate(issueStoreDate, issue.issueFirstStoreDate());
        setUpDescription(issueDescription, issue.issueDescription());
        setUpCharactersList(charactersView, charactersList, issue.charactersInIssue());
    }

    private void loadHeaderImage(ImageView header, ComicImageHelper image) {
        if (image != null) {
            String imageUrl = image.imageSmallUrl();
            ImageUtils.imageWithCropOnTop(header, imageUrl);
        } else {
            header.setVisibility(View.GONE);
        }
    }

    private void setUpHeaderText(TextView textView, String volumeName, int number) {
        textView.setText(TextUtils.issueTitleFromVolume(volumeName, number));
    }

    private void setUpOtherText(TextView textView, String name) {
        if (name != null) {
            textView.setText(name);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void setUpDate(TextView textView, String date) {
        if (date != null) {
            textView.setText(date);
        } else {
            textView.setText("-");
        }
    }

    private void setUpDescription(TextView textView, String description) {
        if (description != null) {
            textView.setText(TextUtils.spannedHtmlText(description));
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void setUpCharactersList(CardView parent, ListView listView,
                                     List<ComicCharacterShort> characters) {
        if (characters != null && !characters.isEmpty()) {
            listAdapter.replaceCharacterListShort(characters);
            ViewUtils.setListViewHeight(listView);
        } else {
            parent.setVisibility(View.GONE);
        }
    }

}
