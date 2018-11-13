package com.bohan.android.capstone.Helper.ModelHelper;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * This is image helper for the issue, volume, character
 */
@AutoValue
public abstract class ComicImageHelper {
    @Nullable
    public abstract String imageIconUrl();
    @Nullable
    public abstract String imageSmallUrl();
    @Nullable
    public abstract String imageMediumUrl();
    @Nullable
    public abstract String imageScreenUrl();
    @Nullable
    public abstract String imageSuperUrl();
    @Nullable
    public abstract String imageThumbUrl();
    @Nullable
    public abstract String imageTinyurl();

    public static Builder builder() {
        return new AutoValue_ComicImageHelper.Builder();
    }

    public static TypeAdapter<ComicImageHelper> typeAdapter(Gson gson) {
        return new AutoValue_ComicImageHelper.GsonTypeAdapter(gson);
    }



    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder imageIconUrl(String comicIconUrl);
        public abstract Builder imageSmallUrl(String comicSmallUrl);
        public abstract Builder imageMediumUrl(String comicMediumUrl);
        public abstract Builder imageScreenUrl(String comicScreenUrl);
        public abstract Builder imageSuperUrl(String comicSuperUrl);
        public abstract Builder imageThumbUrl(String comicThumbUrl);
        public abstract Builder imageTinyurl(String comicTinyurl);

        public abstract ComicImageHelper build();
    }
}
