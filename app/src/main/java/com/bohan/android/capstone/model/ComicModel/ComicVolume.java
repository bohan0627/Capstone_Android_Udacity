package com.bohan.android.capstone.model.ComicModel;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicPublisherHelper;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Bo Han.
 * This is for the volume
 */
@AutoValue
public abstract class ComicVolume {

    //Name of the volume
    @Nullable
    public abstract String volumeName();

    //Unique ID of the volume
    //@Nullable
    public abstract long volumeId();

    //Description of the volume
    @Nullable
    public abstract String volumeDescription();

    //Main image of the volume
    @Nullable
    public abstract ComicImageHelper volumeMainImage();

    //The primary publisher a volume is attached to
    @Nullable
    public abstract ComicPublisherHelper mainPublisher();

    //The first year this volume appeared in comics
    //@Nullable
    public abstract int volumeStartYear();

    //A list of characters that appear in this volume
    @Nullable
    public abstract List<ComicCharacterShort> volumeCharacters();

    //Number of issues included in this volume
    //@Nullable
    public abstract int issuesCount();

    //A list of issues that appear in this volume
    @Nullable
    public abstract List<ComicIssueShort> issueList();


    public static TypeAdapter<ComicVolume> typeAdapter(Gson gson) {
        return new AutoValue_ComicVolume.GsonTypeAdapter(gson);
    }
}
