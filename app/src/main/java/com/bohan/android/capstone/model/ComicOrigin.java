package com.bohan.android.capstone.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * This is the origin Helper
 */
@AutoValue
public abstract class ComicOrigin {

    @Nullable
    public abstract long originId();
    @Nullable
    public abstract String originName();

    public static TypeAdapter<ComicOrigin> typeAdapter(Gson gson) {
        return new AutoValue_ComicOriginShort.GsonTypeAdapter(gson);
    }
}
