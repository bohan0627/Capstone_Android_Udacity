package com.bohan.android.capstone.MVP.VolumeList;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicPublisherHelper;
import com.bohan.android.capstone.Helper.Utils.ImageUtils;
import com.bohan.android.capstone.R;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.MVP.VolumeList.VolumeListAdapter.VolumeViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
class VolumeListAdapter extends RecyclerView.Adapter<VolumeViewHolder> {

    private List<ComicVolumeList> volumes;
    final OnVolumeClickListener listener;

    VolumeListAdapter(OnVolumeClickListener listener) {
        volumes = new ArrayList<>(0);
        this.listener = listener;
    }

    @Override
    public VolumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_volume_list_item, parent, false);

        return new VolumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VolumeViewHolder holder, int position) {
        holder.bindTo(volumes.get(position));
    }

    @Override
    public int getItemCount() {
        return volumes == null ? 0 : volumes.size();
    }

    List<ComicVolumeList> getVolumes() {
        return volumes;
    }

    void setVolumes(List<ComicVolumeList> volumes) {
        this.volumes = volumes;
    }

    class VolumeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private long currentVolumeId;

        @BindView(R.id.volume_image_layout)
        FrameLayout imageLayout;
        @BindView(R.id.volume_cover)
        ImageView volumeCover;
        @BindView(R.id.volume_cover_progressbar)
        ProgressBar progressBar;
        @BindView(R.id.volume_name)
        TextView volumeName;
        @BindView(R.id.volume_publisher)
        TextView volumePublisher;
        @BindString(R.string.volumes_publisher_text)
        String publisherFormat;
        @BindView(R.id.volume_issues_count)
        TextView issuesCount;
        @BindString(R.string.volumes_count_text)
        String issuesCountFormat;

        VolumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bindTo(ComicVolumeList volume) {

            currentVolumeId = volume.volumeId();

            ComicImageHelper image = volume.volumeMainImage();
            if (image != null) {
                String url = image.imageSmallUrl();
                ImageUtils.fetchingImageWithProgress(progressBar,volumeCover, url);
            } else {
                imageLayout.setVisibility(View.GONE);
            }

            volumeName.setText(volume.volumeName());

            ComicPublisherHelper publisher = volume.mainPublisher();

            if (publisher != null) {
                String publisherName = String.format(Locale.US, publisherFormat, publisher.publisherName());
                volumePublisher.setText(publisherName);
            } else {
                volumePublisher.setVisibility(View.GONE);
            }

            String yearCount = String.format(Locale.US, issuesCountFormat, volume.volumeIssuesCount());
            issuesCount.setText(yearCount);
        }

        @Override
        public void onClick(View v) {
            listener.volumeClicked(currentVolumeId);
        }
    }

    interface OnVolumeClickListener {

        void volumeClicked(long volumeId);
    }
}
