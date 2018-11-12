package com.bohan.android.capstone.model.ComicModel;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 * This is for Character
 */
@AutoValue
public abstract class ComicCharacter {

    //List of aliases the character is known by
    @Nullable
    public abstract String characterAliases();

    //A date, if one exists, that the character was born on. Not an origin date
    @Nullable
    public abstract String characterBirth();

    //Number of issues this character appears in
    @Nullable
    public abstract int characterAppearancesInIssues();

    //Description of the character
    @Nullable
    public abstract String characterDescription();

    //Gender of the character. Available options are: Male, Female, Other
    @Nullable
    public abstract int characterGender();

    //Unique ID of the character
    @Nullable
    public abstract long characterId();

    //Main image of the character
    @Nullable
    public abstract ComicImageHelper characterMainImage();

    //Name of the character
    @Nullable
    public abstract String characterName();

    //The origin of the character. Human, Alien, Robot ...etc
    @Nullable
    public abstract ComicOrigin characterOrigin();

    //Real name of the character
    @Nullable
    public abstract String characterRealName();

    public static TypeAdapter<ComicCharacter> typeAdapter(Gson gson) {
        return new AutoValue_ComicCharacter.GsonTypeAdapter(gson);
    }
}
