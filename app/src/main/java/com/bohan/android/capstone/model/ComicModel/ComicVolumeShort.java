package com.bohan.android.capstone.model.ComicModel;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * This is for Volume with only id and name info
 * Please refer to the ComicVolume.class for the related variable description
 */
@AutoValue
public abstract class ComicVolumeShort {

    @Nullable
    public abstract long volumeId();
    @Nullable
    public abstract String volumeName();

    public static Builder builder() {
        return new AutoValue_ComicVolumeShort.Builder();
    }

    public static TypeAdapter<ComicVolumeShort> typeAdapter(Gson gson) {
        return new AutoValue_ComicVolumeShort.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder volumeId(long volumeId);
        public abstract Builder volumeName(String volumeName);

        public abstract ComicVolumeShort build();
    }
}