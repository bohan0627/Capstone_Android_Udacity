package com.bohan.android.capstone.model.ComicModel;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * Please refer to the ComicIssue.class for the related variable description
 */
@AutoValue
public abstract class ComicIssueList {
    //@Nullable
    public abstract long issueId();
    @Nullable
    public abstract ComicImageHelper issueMainImage();
    //@Nullable
    public abstract int issueNumber();
    @Nullable
    public abstract String issueName();
    @Nullable
    public abstract String issueFirstStoreDate();
    @Nullable
    public abstract String issueCoverDate();
    @Nullable
    public abstract ComicVolumeShort volume();

    public static Builder builder() {
        return new AutoValue_ComicIssueList.Builder();
    }

    public static TypeAdapter<ComicIssueList> typeAdapter(Gson gson) {
        return new AutoValue_ComicIssueList.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder issueId(long issueId);
        public abstract Builder issueMainImage(ComicImageHelper issueMainImage);
        public abstract Builder issueNumber(int issueNumber);
        public abstract Builder issueName(String issueName);
        public abstract Builder issueFirstStoreDate(String issueFirstStoreDate);
        public abstract Builder issueCoverDate(String issueCoverDate);
        public abstract Builder volume(ComicVolumeShort volume);

        public abstract ComicIssueList build();
    }
}
