package com.bohan.android.capstone.model.data.Remote;

import com.bohan.android.capstone.BuildConfig;
import com.bohan.android.capstone.Helper.ModelHelper.ServerHelper;
import com.bohan.android.capstone.Helper.Utils.AutoValueUtils;
import com.bohan.android.capstone.Helper.Utils.RxUtils;
import com.bohan.android.capstone.model.ComicModel.ComicCharacter;
import com.bohan.android.capstone.model.ComicModel.ComicCharacterList;
import com.bohan.android.capstone.model.ComicModel.ComicIssue;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.ComicModel.ComicVolume;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Bo Han.
 */
public class ComicRemoteSourceHelper {
    private static final String API_KEY = BuildConfig.COMICVINE_API_KEY;

    private final ComicsDataService comicsService;

    @Inject
    public ComicRemoteSourceHelper(ComicsDataService comicsService) {
        this.comicsService = comicsService;
    }

    /**
     * Request issues list (search by: current date).
     *
     * @param date Date string in YYYY-MM-DD format.
     * @return Issue info list.
     */
    public Single<List<ComicIssueList>> getIssuesListByDate(String date) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicIssueList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("filter", "store_date:" + date);
        options.put("sort", "name:asc");
        options.put("format", "json");

        return comicsService
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
    public Single<ComicIssue> getIssueDetailsById(long issueId) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicIssue.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicsService
                .getIssueDetails(issueId, options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();
    }

    /**
     * Request volumes list (search by: specified name)
     *
     * @param name Target volume name.
     * @return Volume info list.
     */
    public Single<List<ComicVolumeList>> getVolumesListByName(String name) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicVolumeList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("filter", "name:" + name);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicsService
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
    public Single<ComicVolume> getVolumeDetailsById(long volumeId) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicVolume.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicsService
                .getVolumeDetails(volumeId, options)
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
    public Single<List<ComicCharacterList>> getCharactersListByName(String name) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicCharacterList.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("filter", "name:" + name);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicsService
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
    public Single<ComicCharacter> getCharacterDetailsById(long characterId) {

        String fields = AutoValueUtils.autoVauleMethodsList(ComicCharacter.class);

        Map<String, String> options = new HashMap<>();
        options.put("api_key", API_KEY);
        options.put("field_list", fields);
        options.put("format", "json");

        return comicsService
                .getCharacterDetails(characterId, options)
                .compose(RxUtils.applySchedulers())
                .map(ServerHelper::results)
                .singleOrError();
    }
}
