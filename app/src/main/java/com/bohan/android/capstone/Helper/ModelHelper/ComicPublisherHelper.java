package com.bohan.android.capstone.Helper.ModelHelper;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import androidx.annotation.Nullable;

/**
 * Created by Bo Han.
 * This is the publisher helper for the volume, issue and comic
 */
@AutoValue
public abstract class ComicPublisherHelper {

    //@Nullable
    public abstract long publisherId();
    @Nullable
    public abstract String publisherName();

    public static Builder builder() {
        return new AutoValue_ComicPublisherHelper.Builder();
    }

    public static TypeAdapter<ComicPublisherHelper> typeAdapter(Gson gson) {
        return new AutoValue_ComicPublisherHelper.GsonTypeAdapter(gson);
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder publisherId(long publisherId);
        public abstract Builder publisherName(String publisherName);

        public abstract ComicPublisherHelper build();
    }
}
