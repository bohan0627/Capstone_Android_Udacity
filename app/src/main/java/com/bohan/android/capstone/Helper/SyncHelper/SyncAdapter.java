package com.bohan.android.capstone.Helper.SyncHelper;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;

import com.bohan.android.capstone.model.Prefs.ComicPrefsHelper;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.data.Local.ComicLocalSource;

import java.util.Set;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Bo Han.
 */
@SuppressWarnings({"deprecation", "WeakerAccess"})
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String ACTION_DATA_UPDATED =
            "com.bohan.comicslover.ACTION_DATA_UPDATED";

    @Inject
    ComicRemoteSource remoteSource;
    @Inject
    ComicLocalSource localSource;

    @Inject
    ComicPrefsHelper prefsHelper;



    ComicSyncAdapter(Context context) {
        super(context, true);

        ComicsLoverApp
                .getAppComponent()
                .plusRemoteComponent(new ComicRemoteDataModule())
                .plusLocalComponent(new ComicLocalDataModule())
                .inject(this);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {

        Timber.d("Scheduled sync started...");

        String date = DateTextUtils.getTodayDateString();

        remoteDataHelper
                .getIssuesListByDate(date)
                .subscribe(
                        // onSuccess
                        list -> {
                            localSource.removeAllTodayIssuesFromDb();
                            localSource.saveTodayIssuesToDb(list);
                            prefsHelper.setLastSyncDate(date);
                            prefsHelper.clearDisplayedVolumesIdList();
                            sendDataUpdatedBroadcast();
                            checkForTrackedVolumesUpdates();
                            Timber.d("Scheduled sync completed.");
                        },
                        // onError
                        throwable ->
                                Timber.d("Scheduled sync error!")
                );
    }

    private void checkForTrackedVolumesUpdates() {

        Set<Long> trackedVolumes = localSource.getTrackedVolumesIdsFromDb();
        Set<Long> todayVolumes = localSource.getTodayVolumesIdsFromDb();
        Set<String> alreadyDisplayedVolumes = prefsHelper.getDisplayedVolumesIdList();

        StringBuilder notificationText = new StringBuilder();

        for (Long trackedVolumeId : trackedVolumes) {
            // Tracked volume detected
            if (todayVolumes.contains(trackedVolumeId)) {
                // Volume notification was already displayed, continue
                if (alreadyDisplayedVolumes.contains(String.valueOf(trackedVolumeId))) {
                    continue;
                }

                // Add volume name to notification
                String volumeName = localSource.getTrackedVolumeNameById(trackedVolumeId);
                notificationText.append(volumeName);
                notificationText.append(", ");

                // Mark volume as displayed
                prefsHelper.saveDisplayedVolumeId(trackedVolumeId);
            }
        }

        // If notification text is not empty, display it
        if (notificationText.length() > 0) {
            NotificationUtils.updateNotification(
                    getContext(),
                    notificationText.deleteCharAt(notificationText.length() - 2).toString());
        }
    }

    private void sendDataUpdatedBroadcast() {
        Context context = getContext();
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED).setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }
}

