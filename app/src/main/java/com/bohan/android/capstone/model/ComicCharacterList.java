package com.bohan.android.capstone.model;

import com.google.auto.value.AutoValue;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 */
@AutoValue
public abstract class ComicCharacterList {
    public abstract long comicId();
    @Nullable
    public abstract String name();
    @Nullable
    public abstract ComicImages image();
    @Nullable
    public abstract ComicPublisherInfo publisher();

    public static TypeAdapter<ComicCharacterInfoList> typeAdapter(Gson gson) {
        return new AutoValue_ComicCharacterInfoList.GsonTypeAdapter(gson);
    }
}