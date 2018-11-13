package com.bohan.android.capstone.MVP.IssuelocalAsset;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.bohan.android.capstone.MVP.IssueDetails.IssueDetailsActivity;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;

import java.util.List;
import java.util.Locale;

import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.data.Local.ComicLocalSourceModule;
import com.bohan.android.capstone.model.data.Remote.ComicRemoteSourceModule;
import com.evernote.android.state.State;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("deprecation")
public class IssueLocalFragment extends
        ComicLceFragment<RecyclerView, List<ComicIssueList>, IssueLocalView, IssueLocalPresenter>
        implements IssueLocalView{

    private Menu currentMenu;
    private IssueLocalComponent localComponent;
    private IssueLocalAdapter localAdapter;
    private boolean pending;

    
    @BindString(R.string.msg_no_issues_owned)
    String textForEmptyView;
    @BindString(R.string.issues_title_format)
    String issueTitleFormat;
    @BindInt(R.integer.grid_columns_count)
    int gridColumnsCount;
    @BindBool(R.bool.is_tablet_layout)
    boolean twoPane;
    @BindView(R.id.emptyView)
    TextView emptyView;
    @BindView(R.id.contentView)
    RecyclerView contentView;
    @State
    String title;
    @State
    String search;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState == null)
            pending = true;

        localAdapter = new IssueLocalAdapter(issueId -> {
            if (twoPane) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Fragment fragment = new IssueDetailsFragmentBuilder(issueId).build();
                ViewUtils.replaceFragment(
                        manager, fragment, R.id.content_frame, "IssueDetailsFragment", true);
            } else
                startActivity(IssueDetailsActivity.prepareIntent(getContext(), issueId));
        });

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(
                gridColumnsCount, StaggeredGridLayoutManager.VERTICAL);

        setHasOptionsMenu(true);
        contentView.setLayoutManager(manager);
        contentView.setHasFixedSize(true);
        contentView.setlocalAdapter(localAdapter);

        if (isNotNullOrEmpty(search))
            loadDataFiltered(search);
        else if (savedInstanceState != null)
            loadData(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_owned_issues_list, menu);
        //Get current menu
        currentMenu = menu;
        ViewUtils.colorMenu(getContext(), menu, R.id.action_search, R.color.material_color_white);
        ViewUtils.colorMenu(getContext(), menu, R.id.action_clear_search_query,
                R.color.material_color_white);
        // Set menu
        setUpSearchItem(menu);

        if (isNotNullOrEmpty(search)) 
            showClearQueryMenuItem(true);
        else 
            showClearQueryMenuItem(false);

        if (pending) {
            hideToolbar();
            pending = false;
            startToolbarAnimation();
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_clear_search_query:
                showClearQueryMenuItem(false);
                loadData(false);
                break;
        }
        return true;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_local_issue_list_issues;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @NonNull
    @Override
    public IssueLocalPresenter createPresenter() {
        return localComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        localComponent = ComicsLoverApp.getAppComponent()
                .plusRemoteComponent(new ComicRemoteSourceModule())
                .plusLocalComponent(new ComicLocalSourceModule())
                .plusLocalComponent();
        localComponent.inject(this);
    }

    // --- MVP VIEW STATE ---

    @Override
    public List<ComicIssueList> getData() {
        return localAdapter == null ? null : localAdapter.getIssueList();
    }

    @NonNull
    @Override
    public LceViewState<List<ComicIssueList>, IssueLocalView> createViewState() {
        return new RetainingLceViewState<>();
    }

    // --- MVP VIEW ---

    @Override
    public void setIssueTitle(String date) {
        title = String.format(Locale.US, issueTitleFormat, date);
        updateTitle();
    }

    @Override
    public void displayEmptyView(boolean show) {

        if (show) {
            emptyView.setText(textForEmptyView);
            emptyView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
            errorView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }


    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);

        if (!pullToRefresh) {
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(List<ComicIssueList> data) {
        localAdapter.setissueList(data);
        localAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        setIssueTitle("My collection");
        presenter.fetchLocalIssue();
    }

    @Override
    public void fetchDataByFilters(String filter) {
        setIssueTitle(filter);
        presenter.fetchLocalIssueByName(filter);
    }

    // --- MISC UTILITY FUNCTIONS ---

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

                search = query;

                if (search.length() > 0) {
                    showClearQueryMenuItem(true);
                    fetchDataByFilters(search);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void updateTitle() {
        ActionBar supportActionBar = ((NavigationActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }

    private boolean isNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    void showClearQueryMenuItem(boolean show) {
        currentMenu.findItem(R.id.action_search).setVisible(!show);
        currentMenu.findItem(R.id.action_clear_search_query).setVisible(show);
    }
}

