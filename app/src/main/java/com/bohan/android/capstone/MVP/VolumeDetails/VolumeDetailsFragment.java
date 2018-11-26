package com.bohan.android.capstone.MVP.VolumeDetails;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicLceFragment;
import com.bohan.android.capstone.Helper.ModelHelper.ComicPublisherHelper;
import com.bohan.android.capstone.Helper.Utils.ImageUtils;
import com.bohan.android.capstone.Helper.Utils.TextUtils;
import com.bohan.android.capstone.Helper.Utils.ViewUtils;
import com.bohan.android.capstone.MVP.IssueDetails.IssueDetailsActivity;
import com.bohan.android.capstone.MVP.IssueDetails.IssueDetailsFragmentBuilder;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicIssueShort;
import com.bohan.android.capstone.model.ComicModel.ComicVolume;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.data.Local.ComicLocalSourceModule;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceModule;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Bo Han.
 */
@SuppressWarnings({"WeakerAccess", "deprecation"})
@FragmentWithArgs
public class VolumeDetailsFragment extends
        ComicLceFragment<LinearLayout, ComicVolume, VolumeDetailsView, VolumeDetailsPresenter>
        implements VolumeDetailsView {

    @Arg
    long volumeId;

    @BindView(R.id.volume_details_screen)
    ImageView volumeCover;
    @BindView(R.id.volume_details_name)
    TextView volumeName;
    @BindView(R.id.volume_details_publisher)
    TextView volumePublisher;
    @BindView(R.id.volume_details_year)
    TextView volumeYear;
    @BindView(R.id.volume_details_description)
    TextView volumeDescription;
    @BindView(R.id.volume_details_issues_card)
    CardView issuesView;
    @BindView(R.id.volume_details_issues_view)
    RecyclerView issuesRecyclerView;

    @BindString(R.string.msg_tracked)
    String messageTracked;
    @BindString(R.string.msg_track_removed)
    String messageUntracked;
    @BindBool(R.bool.is_tablet_layout)
    boolean twoPaneMode;

    private VolumeDetailsComponent volumeDetailsComponent;
    private ComicVolume currentVolumeInfo;
    private VolumeDetailsIssueAdapter issuesAdapter;
    private Menu currentMenu;

    // --- FRAGMENT LIFECYCLE ---

    @Override
    public void onResume() {
        super.onResume();
        // Force recyclerView update so it can display actual bookmarks
        issuesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        issuesAdapter = new VolumeDetailsIssueAdapter(new VolumeDetailsIssueAdapter.IssuesAdapterCallbacks() {
            @Override
            public void issueClicked(long issueId) {
                if (twoPaneMode) {
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Fragment fragment = new IssueDetailsFragmentBuilder(issueId).build();

                    ViewUtils.replaceFragment(
                            manager, fragment,"IssueDetailsFragment",  R.id.content_frame, true);
                } else {
                    startActivity(IssueDetailsActivity.prepareIntent(getContext(), issueId));
                }
            }

            @Override
            public boolean isIssueTracked(long issueId) {
                return presenter.ifTargetIssueOwned(issueId);
            }
        });

        issuesAdapter.setHasStableIds(true);

        LinearLayoutManager manager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        issuesRecyclerView.setLayoutManager(manager);
        issuesRecyclerView.setHasFixedSize(true);
        issuesRecyclerView.setNestedScrollingEnabled(false);
        issuesRecyclerView.setAdapter(issuesAdapter);

        issuesRecyclerView
                .addItemDecoration(new DividerItemDecoration(getContext(), manager.getOrientation()));

        if (savedInstanceState != null) {
            loadData(false);
        }
    }

    // --- OPTIONS MENU ---

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_volume_details, menu);

        currentMenu = menu;

        presenter.setUpTrackIconState(volumeId);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_track:
                onClickVolumeDetails();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // --- BASE LCE FRAGMENT ---

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_volume_details;
    }

    @NonNull
    @Override
    public VolumeDetailsPresenter createPresenter() {
        return volumeDetailsComponent.volumeDetailsPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    protected void injectDependencies() {
        volumeDetailsComponent = ComicsLoverApp.getAppComponent()
                .plusRemoteComponent(new ComicRemoteSourceModule())
                .plusLocalComponent(new ComicLocalSourceModule())
                .plusVolumeDetailsComponent();

        volumeDetailsComponent.inject(this);
    }

    // --- MVP VIEW STATE ---

    @Override
    public ComicVolume getData() {
        return currentVolumeInfo;
    }

    @NonNull
    @Override
    public LceViewState<ComicVolume, VolumeDetailsView> createViewState() {
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
    public void setData(ComicVolume data) {
        currentVolumeInfo = data;
        bindVolumeToUi(currentVolumeInfo);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadVolumeDetails(volumeId);
    }

    @Override
    public void mark() {
        currentMenu.findItem(R.id.action_track).setIcon(R.drawable.ic_notifications_black_24dp);

        ViewUtils.colorMenu(getContext(), currentMenu, R.id.action_track,
                R.color.material_color_white);
    }

    @Override
    public void unMark() {
        currentMenu.findItem(R.id.action_track).setIcon(R.drawable.ic_notifications_none_black_24dp);

        ViewUtils.colorMenu(getContext(), currentMenu, R.id.action_track,
                R.color.material_color_white);
    }

    @Override
    public void onClickVolumeDetails() {

        if (currentVolumeInfo == null) {
            return;
        }

        String message;

        boolean isTrackedNow = presenter.isCurrentVolumeTracked(volumeId);

        if (isTrackedNow) {
            presenter.removeTracking(volumeId);
            message = messageUntracked;
        } else {
            presenter.trackVolume(currentVolumeInfo);
            message = messageTracked;
        }

        presenter.setUpTrackIconState(volumeId);

        int parentLayoutId = (twoPaneMode) ?
                R.id.main_coordinator_layout :
                R.id.volume_details_activity_layout;

        Snackbar.make(
                ButterKnife.findById(getActivity(), parentLayoutId),
                message,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    // --- UI BINDING UTILS ---

    private void bindVolumeToUi(ComicVolume volume) {

        loadHeaderImage(volumeCover, volume.volumeMainImage());

        String name = volume.volumeName();
        volumeName.setText(name);

        int year = volume.volumeStartYear();
        volumeYear.setText(String.valueOf(year));

        ComicPublisherHelper publisher = volume.mainPublisher();
        setUpPublisher(volumePublisher, publisher);
        setUpDescription(volumeDescription, volume.volumeDescription());
        setUpIssuesList(issuesView, volume.issueList());
    }

    private void loadHeaderImage(ImageView header, ComicImageHelper image) {
        if (image != null) {
            String imageUrl = image.imageSmallUrl();
            ImageUtils.imageWithCropOnTop(header, imageUrl);
        } else {
            header.setVisibility(View.GONE);
        }
    }

    private void setUpPublisher(TextView textView, ComicPublisherHelper publisher) {
        if (publisher != null) {
            textView.setText(publisher.publisherName());
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

    private void setUpIssuesList(CardView parent, List<ComicIssueShort> issues) {
        if (issues != null && !issues.isEmpty()) {
            issuesAdapter.setIssues(issues);
            issuesAdapter.notifyDataSetChanged();
        } else {
            parent.setVisibility(View.GONE);
        }
    }
}
