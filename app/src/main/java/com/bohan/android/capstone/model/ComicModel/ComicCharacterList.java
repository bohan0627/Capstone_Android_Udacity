package com.bohan.android.capstone.model.ComicModel;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicPublisherHelper;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * Please refer to the ComicCharacter.class for the related variable description
 */
@AutoValue
public abstract class ComicCharacterList {

    @Nullable
    public abstract long characterId();
    @Nullable
    public abstract String characterName();
    @Nullable
    public abstract ComicImageHelper characterMainImage();
    @Nullable
    public abstract ComicPublisherHelper characterMainPublisher();

    public static TypeAdapter<ComicCharacterList> typeAdapter(Gson gson) {
        return new AutoValue_ComicCharacterList.GsonTypeAdapter(gson);
    }
}