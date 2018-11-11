package com.bohan.android.capstone;

import com.bohan.android.capstone.Utils.AutoValueUtils;
import com.bohan.android.capstone.Utils.RxUtils;
import com.bohan.android.capstone.model.ComicCharacter;
import com.bohan.android.capstone.model.ComicCharacterList;
import com.bohan.android.capstone.model.ComicIssue;
import com.bohan.android.capstone.model.ComicIssueList;
import com.bohan.android.capstone.model.ComicVolume;
import com.bohan.android.capstone.model.ComicVolumeList;
import com.bohan.android.capstone.model.ModelHelper.ServerHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Bo Han.
 * This is for fetching data from remote API(Comic Vine)
 */
public class ComicRemoteSource {

    //It will automatically invoke the API_KEY in the gradle file
    private static final String API_KEY = BuildConfig.COMIC_API_KEY;

    private final ComicsDataService comicService;

    @Inject
    public ComicRemoteSource(ComicsDataService comicService) {
        this.comicService = comicService;
    }

    /**
     * Request volumes list (search by: specified name)
     *
     * @param name Target volume name.
     * @return Volume info list.
     */
    public Single<List<ComicVolumeList>> volumeListByVolumeName(String volumeName) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicVolumeList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("filter", "name:" + volumeName);
        options.put("field_list", fields);
        options.put("format", "json");

        return ComicsDataService
                .getVolumesList(options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();
    }

    /**
     * Request volume details (search by: volume id).
     *
     * @param volumeId Target volume id.
     * @return Detailed volume info.
     */
    public Single<ComicVolume> volumeDetailsByVolumeId(long volumeId) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicVolume.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return ComicsDataService
                .getVolumeDetails(volumeId, options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();

    }


    /**
     * Request issues list (search by: current date).
     *
     * @param date Date string in YYYY-MM-DD format.
     * @return Issue info list.
     */
    public Single<List<ComicIssueList>> issueListByDate(String date) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicIssueList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("filter", "store_date:" + date);
        options.put("sort", "name:asc");
        options.put("format", "json");

        return ComicsDataService
                .getIssuesList(options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();

    }

    /**
     * Request issue details (search by: issue id).
     *
     * @param issueId Target issue id (!= issue number).
     * @return Detailed issue info.
     */
    public Single<ComicIssue> issueDetailsByIssueId(long issueId) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicIssue.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return ComicsDataService
                .getIssueDetails(issueId, options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();
    }


    /**
     * Request characters list (search by: specified name)
     *
     * @param name Target character name to perform search.
     * @return Characters info list.
     */
    public Single<List<ComicCharacterList>> characterListByCharacterName(String characterName) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicCharacterList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("filter", "name:" + characterName);
        options.put("field_list", fields);
        options.put("format", "json");

        return ComicsDataService
                .getCharactersList(options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();
    }

    /**
     * Request character details (search by: character id)
     *
     * @param characterId Target character ud.
     * @return Detailed character info.
     */
    public Single<ComicCharacter> characterDetailsByCharacterId(long characterId) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicCharacter.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return ComicsDataService
                .getCharacterDetails(characterId, options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();
    }
}
