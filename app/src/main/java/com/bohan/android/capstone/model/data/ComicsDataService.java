package com.bohan.android.capstone.model.data;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import com.bohan.android.capstone.model.ComicModel.ComicCharacter;
import com.bohan.android.capstone.model.ComicModel.ComicCharacterList;
import com.bohan.android.capstone.model.ComicModel.ComicIssue;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.ComicModel.ComicVolume;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.Helper.ModelHelper.ServerHelper;

/**
 * Created by Bo Han.
 */
public interface ComicsDataService {

    // This info from Comic Vine Website
    final String ENDPOINT = "https://comicvine.gamespot.com/";
    final String ISSUE_TYPE_CODE = "4000";
    final String CHARACTER_TYPE_CODE = "4005";
    final String VOLUME_TYPE_CODE = "4050";

    /**
     * Implement request based on @GET
     * Issues, Characters and Volumes
     */

    // Request issues list
    @GET("/api/issues/")
    Observable<ServerHelper<List<ComicIssueList>>> getIssuesList(
            @QueryMap Map<String, String> options);

    // Request characters list
    @GET("/api/characters/")
    Observable<ServerHelper<List<ComicCharacterList>>> getCharactersList(
            @QueryMap Map<String, String> options);

    // Request volumes list
    @GET("/api/volumes/")
    Observable<ServerHelper<List<ComicVolumeList>>> getVolumesList(
            @QueryMap Map<String, String> options);

    // Request issue details
    @GET("/api/issue/" + ISSUE_TYPE_CODE + "-{id}/")
    Observable<ServerHelper<ComicIssue>> getIssueDetails(
            @Path("id") long issueId,
            @QueryMap Map<String, String> options);

    // Request character details
    @GET("/api/character/" + CHARACTER_TYPE_CODE + "-{id}/")
    Observable<ServerHelper<ComicCharacter>> getCharacterDetails(
            @Path("id") long characterId,
            @QueryMap Map<String, String> options);

    // Request volume details
    @GET("/api/volume/" + VOLUME_TYPE_CODE + "-{id}/")
    Observable<ServerHelper<ComicVolume>> getVolumeDetails(
            @Path("id") long volumeId,
            @QueryMap Map<String, String> options);



}
