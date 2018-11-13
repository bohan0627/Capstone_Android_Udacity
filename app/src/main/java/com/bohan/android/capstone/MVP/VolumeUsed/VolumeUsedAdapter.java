package com.bohan.android.capstone.MVP.VolumeUsed;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bohan.android.capstone.Helper.Utils.ImageUtils;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.data.ComicContract.LocalVolumeEntry;

import com.bohan.android.capstone.MVP.VolumeUsed.VolumeUsedAdapter.VolumeViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Bo Han.
 */
public class VolumeUsedAdapter extends RecyclerView.Adapter<VolumeViewHolder> {

    final OnVolumeClickListener listener;
    Cursor cursor;

    VolumeUsedAdapter(OnVolumeClickListener listener) {
        this.listener = listener;
    }

    @Override
    public VolumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_volumes_tracker_item, parent, false);

        return new VolumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VolumeViewHolder holder, int position) {

        holder.bindTo(position);
    }

    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    @SuppressWarnings("UnusedReturnValue")
    Cursor swapCursor(Cursor data) {

        Timber.d("Swapping cursor...");

        if (data != null) {
            Timber.d("Cursor size: " + data.getCount());
        } else {
            Timber.d("Cursor is null.");
        }


        if (cursor == data) {
            return null;
        }

        Cursor temp = cursor;
        this.cursor = data;

        if (data != null) {
            notifyDataSetChanged();
        }
        return temp;
    }

    class VolumeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private long currentId;

        @BindView(R.id.volume_cover)
        ImageView volumeCover;
        @BindView(R.id.volume_cover_progressbar)
        ProgressBar progressBar;
        @BindView(R.id.volume_name)
        TextView volumeName;

        VolumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void bindTo(int position) {

            int idIndex = cursor.getColumnIndexOrThrow(LocalVolumeEntry.COLUMN_VOLUME_ID);
            int coverIndex = cursor.getColumnIndexOrThrow(LocalVolumeEntry.COLUMN_VOLUME_SMALL_IMAGE);
            int nameIndex = cursor.getColumnIndexOrThrow(LocalVolumeEntry.COLUMN_VOLUME_NAME);

            Timber.d("Cursor size is " + cursor.getCount());
            Timber.d("Cursor: id index is " + idIndex);
            Timber.d("Cursor: cover index is " + coverIndex);
            Timber.d("Cursor: name index is " + nameIndex);

            cursor.moveToPosition(position);

            Timber.d("Cursor current position" + cursor.getPosition());

            Timber.d("Trying to get id");
            currentId = cursor.getLong(idIndex);
            Timber.d("Trying to get cover");
            String coverUrl = cursor.getString(coverIndex);
            Timber.d("Trying to get name");
            String name = cursor.getString(nameIndex);

            ImageUtils.fetchingImageWithProgress(progressBar,volumeCover, coverUrl);
            volumeName.setText(name);
        }

        @Override
        public void onClick(View v) {
            listener.volumeClicked(currentId);
        }
    }

    interface OnVolumeClickListener {

        void volumeClicked(long volumeId);
    }
}
