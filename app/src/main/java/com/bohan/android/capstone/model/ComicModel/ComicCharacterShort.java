package com.bohan.android.capstone.model.ComicModel;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * Please refer to the ComicCharacter.class for the related variable description
 * This is for the character info with only id and name
 */
@AutoValue
public abstract class ComicCharacterShort {

    //@Nullable
    public abstract long characterId();
    @Nullable
    public abstract String characterName();

    public static TypeAdapter<ComicCharacterShort> typeAdapter(Gson gson) {
        return new AutoValue_ComicCharacterShort.GsonTypeAdapter(gson);
    }
}
