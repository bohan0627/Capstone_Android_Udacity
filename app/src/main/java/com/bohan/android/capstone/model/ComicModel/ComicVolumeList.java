package com.bohan.android.capstone.model.ComicModel;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicPublisherHelper;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * This is for the volume list
 * Please refer to the ComicVolume.class for the related variable description
 */
@AutoValue
public abstract class ComicVolumeList {

    //@Nullable
    public abstract long volumeId();

    @Nullable
    public abstract String volumeName();

    @Nullable
    public abstract ComicImageHelper volumeMainImage();

    @Nullable
    public abstract ComicPublisherHelper mainPublisher();

    //@Nullable
    public abstract int volumeStartYear();

    //@Nullable
    public abstract int volumeIssuesCount();

    public static Builder builder() {
        return new AutoValue_ComicVolumeList.Builder();
    }

    public static TypeAdapter<ComicVolumeList> typeAdapter(Gson gson) {
        return new AutoValue_ComicVolumeList.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder volumeId(long volumeId);
        public abstract Builder volumeName(String volumeName);
        public abstract Builder volumeMainImage(ComicImageHelper volumeMainImage);
        public abstract Builder mainPublisher(ComicPublisherHelper mainPublisher);
        public abstract Builder volumeStartYear(int volumeStartYear);
        public abstract Builder volumeIssuesCount(int volumeIssuesCount);

        public abstract ComicVolumeList build();
    }
}