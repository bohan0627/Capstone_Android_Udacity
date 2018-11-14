package com.bohan.android.capstone.model.ComicModel;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * This is for the Issue
 */
@AutoValue
public abstract class ComicIssue {

    //A list of characters that appear in this issue
    @Nullable
    public abstract List<ComicCharacterShort> charactersInIssue();

    //The publish date printed on the cover of an issue
    @Nullable
    public abstract String issueCoverDate();

    //Description of the issue
    @Nullable
    public abstract String issueDescription();

    //Unique ID of the issue
    //@Nullable
    public abstract long issueId();

    //Main image of the issue
    @Nullable
    public abstract ComicImageHelper issueMainImage();

    //The number assigned to the issue within the volume set
    //@Nullable
    public abstract int issueNumber();

    //Name of the issue
    @Nullable
    public abstract String issueName();

    //The date the issue was first sold in stores
    @Nullable
    public abstract String issueFirstStoreDate();

    //The volume this issue is a part of
    @Nullable
    public abstract ComicVolumeShort volume();

    public static TypeAdapter<ComicIssue> typeAdapter(Gson gson) {
        return new AutoValue_ComicIssue.GsonTypeAdapter(gson);
    }
}
