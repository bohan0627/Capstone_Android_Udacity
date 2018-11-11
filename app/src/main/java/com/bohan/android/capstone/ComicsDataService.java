package com.bohan.android.capstone;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Bo Han.
 */
public class ComicsDataService {

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
    Observable<ServerResponse<List<ComicIssueInfoList>>> getIssuesList(
            @QueryMap Map<String, String> options);

    // Request characters list
    @GET("/api/characters/")
    Observable<ServerResponse<List<ComicCharacterInfoList>>> getCharactersList(
            @QueryMap Map<String, String> options);

    // Request volumes list
    @GET("/api/volumes/")
    Observable<ServerResponse<List<ComicVolumeInfoList>>> getVolumesList(
            @QueryMap Map<String, String> options);

    // Request issue details
    @GET("/api/issue/" + ISSUE_TYPE_CODE + "-{id}/")
    Observable<ServerResponse<ComicIssueInfo>> getIssueDetails(
            @Path("id") long issueId,
            @QueryMap Map<String, String> options);

    // Request character details
    @GET("/api/character/" + CHARACTER_TYPE_CODE + "-{id}/")
    Observable<ServerResponse<ComicCharacterInfo>> getCharacterDetails(
            @Path("id") long characterId,
            @QueryMap Map<String, String> options);

    // Request volume details
    @GET("/api/volume/" + VOLUME_TYPE_CODE + "-{id}/")
    Observable<ServerResponse<ComicVolumeInfo>> getVolumeDetails(
            @Path("id") long volumeId,
            @QueryMap Map<String, String> options);



}
