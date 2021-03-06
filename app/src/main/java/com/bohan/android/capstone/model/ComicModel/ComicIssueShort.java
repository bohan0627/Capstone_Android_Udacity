package com.bohan.android.capstone.model.ComicModel;



import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import androidx.annotation.Nullable;

/**
 * Created by Bo Han.
 * Please refer to the ComicIssue.class for the related variable description
 * This is the issue info with id, name and issue_number
 */
@AutoValue
public abstract class ComicIssueShort {
    //@Nullable
    public abstract long issueId();
    @Nullable
    public abstract String issueName();
    //@Nullable
    public abstract int issueNumber();

    public static ComicIssueShort.Builder builder() {
        return new AutoValue_ComicIssueShort.Builder();
    }

    public static TypeAdapter<ComicIssueShort> typeAdapter(Gson gson) {
        return new AutoValue_ComicIssueShort.GsonTypeAdapter(gson);
    }



    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder issueId(long issueId);
        public abstract Builder issueName(String issueName);
        public abstract Builder issueNumber(int issueNumber);

        public abstract ComicIssueShort build();
    }
}
