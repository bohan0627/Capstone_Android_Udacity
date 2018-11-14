package com.bohan.android.capstone.model.Prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.bohan.android.capstone.Helper.Utils.TextUtils;
import com.bohan.android.capstone.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bo Han.
 */
public class ComicPrefsHelper {
    private final Context context;
    private final SharedPreferences sharedPreferences;

    public ComicPrefsHelper(Context context) {
        this.context = context;
        sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.prefs_file_name), Context.MODE_PRIVATE);
    }

    public void markIssuesViewAsShowcased() {
        sharedPreferences
                .edit()
                .putBoolean(context.getString(R.string.prefs_issues_showcased_key), true)
                .apply();
    }

    public boolean wasIssuesViewShowcased() {
        return sharedPreferences
                .getBoolean(context.getString(R.string.prefs_issues_showcased_key), false);
    }

    public String getSyncPeriod() {
        return sharedPreferences
                .getString(context.getString(R.string.prefs_sync_period_key), "24");
    }

    public Set<String> getDisplayedVolumesIdList() {
        return sharedPreferences
                .getStringSet(context.getString(R.string.prefs_displayed_notifications), new HashSet<>());
    }

    public void setLastSyncDate(String date) {
        sharedPreferences
                .edit()
                .putString(context.getString(R.string.prefs_last_sync_date), date)
                .apply();
    }

    private String getLastSyncDate() {
        return sharedPreferences.getString(context.getString(R.string.prefs_last_sync_date), "");
    }

    public void saveDisplayedVolumeId(long id) {

        Set<String> alreadySavedIds = getDisplayedVolumesIdList();
        alreadySavedIds.add(String.valueOf(id));

        sharedPreferences
                .edit()
                .putStringSet(context.getString(R.string.prefs_displayed_notifications), alreadySavedIds)
                .apply();
    }

    public void clearDisplayedVolumesIdList() {

        if (isLastSyncWasToday()) {
            return;
        }

        sharedPreferences
                .edit()
                .putStringSet(context.getString(R.string.prefs_displayed_notifications), new HashSet<>())
                .apply();
    }

    private boolean isLastSyncWasToday() {
        String lastSyncDate = getLastSyncDate();
        String today = TextUtils.dateStringForToday();

        return lastSyncDate.equals(today);
    }
}
