package com.bohan.android.capstone.Helper.ModelHelper;

import com.google.auto.value.AutoValue;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by Bo Han.
 * This is for tracking server response
 */
@AutoValue
public abstract class ServerHelper<T> {
    public abstract String error();
    public abstract long limit();
    public abstract long offset();
    public abstract long number_of_page_results();
    public abstract long number_of_total_results();
    public abstract long status_code();
    public abstract T results();
    public abstract double version();

    public static <T> TypeAdapter<ServerHelper<T>> typeAdapter(Gson gson,
                                                               TypeToken<? extends ServerHelper<T>> typeToken) {
        return new AutoValue_ServerHelper.GsonTypeAdapter<>(gson, typeToken);
    }
}
