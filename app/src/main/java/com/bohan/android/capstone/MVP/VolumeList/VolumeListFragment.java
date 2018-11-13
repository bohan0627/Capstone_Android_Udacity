package com.bohan.android.capstone.MVP.VolumeList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bohan.android.capstone.Helper.ModelHelper.ComicLceFragment;
import com.bohan.android.capstone.Helper.NavigationHelper.NavigationActivity;
import com.bohan.android.capstone.Helper.Utils.ViewUtils;
import com.bohan.android.capstone.MVP.VolumeDetails.VolumeDetailsActivity;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceModule;
import com.evernote.android.state.State;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
@FragmentWithArgs
public class VolumeListFragment extends
        ComicLceFragment<RecyclerView, List<ComicVolumeList>, VolumeListView, VolumeListPresenter>
        implements VolumeListView {

    @BindInt(R.integer.grid_columns_count)
    int gridColumnsCount;
    @BindString(R.string.volumes_fragment_title)
    String fragmentTitle;
    @BindString(R.string.msg_no_volumes_found)
    String emptyViewText;
    @BindString(R.string.msg_volumes_start)
    String initialViewText;
    @BindBool(R.bool.is_tablet_layout)
    boolean twoPaneMode;

    @BindView(R.id.initialView)
    TextView initialView;
    @BindView(R.id.emptyView)
    TextView emptyView;

    @State
    String chosenName;

    @State
    String title;

    private VolumeListComponent volumesComponent;
    private VolumeListAdapter adapter;
    private boolean pendingStartupAnimation;

    // --- FRAGMENTS LIFECYCLE ---

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            pendingStartupAnimation = true;
        }

        adapter = new VolumeListAdapter(volumeId -> {
            if (twoPaneMode) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Fragment fragment = new VolumeDetailsFragmentBuilder(volumeId).build();

                ViewUtils.replaceFragment(
                        manager, fragment, R.id.content_frame, "VolumeDetailsFragment", true);
            } else {
                startActivity(VolumeDetailsActivity.prepareIntent(getContext(), volumeId));
            }
        });

        adapter.setHasStableIds(true);

        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(gridColumnsCount, StaggeredGridLayoutManager.VERTICAL);

        contentView.setLayoutManager(manager);
        contentView.setHasFixedSize(true);
        contentView.setAdapter(adapter);

        setHasOptionsMenu(true);

        if (chosenName != null && chosenName.length() > 0) {
            loadDataByName(chosenName);
            setTitle(chosenName);
        } else {
            setTitle(fragmentTitle);
            showInitialView(true);
        }
    }

    // --- OPTIONS MENU ---

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_volume_list, menu);

        ViewUtils.colorMenu(getContext(), menu, R.id.action_search, R.color.material_color_white);

        setUpSearchItem(menu);

        if (pendingStartupAnimation) {
            hideToolbar();
            pendingStartupAnimation = false;
            startToolbarAnimation();
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    // --- BASE LCE FRAGMENT ---

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_volumes;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @NonNull
    @Override
    public VolumeListPresenter createPresenter() {
        return volumesComponent.volumeListPresenter();
    }

    @Override
    protected void injectDependencies() {
        volumesComponent = ComicsLoverApp.getAppComponent()
                .plusRemoteComponent(new ComicRemoteSourceModule())
                .plusVolumesComponent();
        volumesComponent.inject(this);
    }

    // --- MVP VIEW STATE ---

    @Override
    public List<ComicVolumeList> getData() {
        return adapter == null ? null : adapter.getVolumes();
    }

    @NonNull
    @Override
    public LceViewState<List<ComicVolumeList>, VolumeListView> createViewState() {
        return new RetainingLceViewState<>();
    }

    // --- MVP VIEW ---

    @Override
    public void setData(List<ComicVolumeList> data) {
        adapter.setVolumes(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        showLoading(false);
        showInitialView(true);
    }

    @Override
    public void loadDataByName(String name) {
        presenter.fetchVolumeByName(name);
    }

    private void setUpSearchItem(Menu menu) {
        // Find items
        MaterialSearchView searchView = ButterKnife.findById(getActivity(), R.id.search_view);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        // Tweaks
        searchView.setVoiceSearch(false);
        searchView.setMenuItem(menuItem);

        // Listeners
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                chosenName = query;

                if (chosenName.length() > 0) {
                    loadDataByName(chosenName);
                    setTitle(chosenName);
                    presenter.logVolumeSearchEvent(chosenName);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
    }

    @Override
    public void showContent() {
        showInitialView(false);
        showEmptyView(false);
        super.showContent();
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if (pullToRefresh) {
            errorView.setVisibility(View.GONE);
            contentView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
        } else {
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void displayBaseView(boolean show) {
        if (show) {
            initialView.setText(initialViewText);
            initialView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
        } else {
            initialView.setVisibility(View.GONE);
        }
    }

    @Override
    public void displayEmptyView(boolean show) {
        if (show) {
            emptyView.setText(emptyViewText);
            emptyView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setVolumeTitle(String title) {
        this.title = title;
        updateTitle();
    }

    private void updateTitle() {
        ActionBar supportActionBar = ((NavigationActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }
}
