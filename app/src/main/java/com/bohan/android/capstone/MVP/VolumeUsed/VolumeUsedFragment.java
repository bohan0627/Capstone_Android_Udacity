package com.bohan.android.capstone.MVP.VolumeUsed;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import com.bohan.android.capstone.model.data.ComicContract.LocalVolumeEntry;

import com.bohan.android.capstone.Helper.ModelHelper.ComicFragment;
import com.bohan.android.capstone.Helper.NavigationHelper.NavigationActivity;
import com.bohan.android.capstone.Helper.Utils.ViewUtils;
import com.bohan.android.capstone.MVP.VolumeDetails.VolumeDetailsActivity;
import com.bohan.android.capstone.R;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 */

@FragmentWithArgs
@SuppressWarnings("deprecation")
public class VolumeUsedFragment extends ComicFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TRACKER_LOADER_ID = 1234;

    @BindView(R.id.contentView)
    RecyclerView contentView;
    @BindView(R.id.emptyView)
    TextView emptyView;

    @BindString(R.string.msg_no_volumes_tracked)
    String emptyViewText;
    @BindInt(R.integer.grid_columns_count)
    int gridColumnsCount;
    @BindBool(R.bool.is_tablet_layout)
    boolean twoPaneMode;

    private VolumeUsedAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new VolumeUsedAdapter(volumeId -> {
            if (twoPaneMode) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Fragment fragment = new VolumeDetailsFragmentBuilder(volumeId).build();

                ViewUtils.replaceFragment(
                        manager, fragment,"VolumeDetailsFragment",  R.id.content_frame, true);
            } else {
                startActivity(VolumeDetailsActivity.prepareIntent(getContext(), volumeId));
            }
        });

        emptyView.setText(emptyViewText);

        adapter.setHasStableIds(true);

        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(gridColumnsCount, StaggeredGridLayoutManager.VERTICAL);

        contentView.setLayoutManager(manager);
        contentView.setHasFixedSize(true);
        contentView.setAdapter(adapter);

        ActionBar supportActionBar = ((NavigationActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.volumes_tracker_fragment_title);
        }

        if (savedInstanceState == null) {
            hideToolbar();
            startToolbarAnimation();
        }

        getActivity().getSupportLoaderManager().initLoader(TRACKER_LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(TRACKER_LOADER_ID, null, this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_volume_used;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getContext()) {

            Cursor data = null;

            @Override
            protected void onStartLoading() {

                if (data != null) {
                    deliverResult(data);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                return getActivity().getContentResolver()
                        .query(LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES,
                                null, null, null, null);
            }

            @Override
            public void deliverResult(Cursor cursor) {
                data = cursor;
                super.deliverResult(cursor);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.getCount() > 0) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }

        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
