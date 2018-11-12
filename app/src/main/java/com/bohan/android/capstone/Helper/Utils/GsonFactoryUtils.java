package com.bohan.android.capstone.Helper.Utils;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by Bo Han.
 * About this AutoValueGson, please refer to https://github.com/rharter/auto-value-gson
 * It is an extension for Google's AutoValue that creates a simple Gson TypeAdapterFactory
 * for each AutoValue annotated object.
 */
@GsonTypeAdapterFactory
public abstract class GsonFactoryUtils implements TypeAdapterFactory {

    public static TypeAdapterFactory create() {
        return new AutoValueGson_GsonFactoryUtils();
    }
}
