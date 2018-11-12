package com.bohan.android.capstone.model.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.bohan.android.capstone.Helper.Utils.ContentUtils;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.model.data.ComicContract.TrackedVolumeEntry;
import com.bohan.android.capstone.model.data.ComicContract.IssueEntry;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * Created by Bo Han.
 * This is for fetching data from local
 */
public class ComicLocalSource {
    private final ContentResolver contentResolver;

    @Inject
    public ComicLocalSource(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void issuesTodayToDB(@NonNull List<ComicIssueList> issueList) {
        for (ComicIssueList issue : issueList) {
            contentResolver.insert(IssueEntry.CONTENT_URI_TODAY_ISSUES,
                    ContentUtils.contentValuesFromIssue(issue));
        }
    }

    public Single<List<ComicIssueList>> issuesTodayFromDB() {

        return Single.create(e -> { Cursor cursor = contentResolver
                    .query(IssueEntry.CONTENT_URI_TODAY_ISSUES, null, null, null, null);
            if (cursor != null) {
                List<ComicIssueList> list = ContentUtils.issuesFromCursor(cursor);
                cursor.close();
                e.onSuccess(list);
            } });
    }

    public Single<List<ComicIssueList>> localIssueFromDB() {

        return Single.create(e -> { Cursor cursor = contentResolver
                    .query(IssueEntry.CONTENT_URI_OWNED_ISSUES,
                            null,
                            null,
                            null,
                            IssueEntry.COLUMN_ISSUE_VOLUME_ID + "," + IssueEntry.COLUMN_ISSUE_NUMBER);
            if (cursor != null) {
                List<ComicIssueList> list = ContentUtils.issuesFromCursor(cursor);
                cursor.close();
                e.onSuccess(list);
            } });
    }

    public void deleteIssuesTodayFromDB() {
        contentResolver.delete(IssueEntry.CONTENT_URI_TODAY_ISSUES, null, null);
    }


    public Set<Long> volumesIdsTodayFromDB() {
        Set<Long> volumeIds = null;
        Cursor cursor = contentResolver.query(IssueEntry.CONTENT_URI_TODAY_ISSUES,
                new String[]{IssueEntry.COLUMN_ISSUE_VOLUME_ID},
                null,
                null,
                null);

        if (cursor != null) {
            volumeIds = ContentUtils.idsFromCursor(cursor);
            cursor.close();
        }

        return volumeIds;
    }

    public Set<Long> localVolumeIdsFromDB() {
        Set<Long> volumeIds = null;
        Cursor cursor = contentResolver.query(TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                new String[]{TrackedVolumeEntry.COLUMN_VOLUME_ID},
                null,
                null,
                null);

        if (cursor != null) {
            volumeIds = ContentUtils.idsFromCursor(cursor);
            cursor.close();
        }

        return volumeIds;
    }

    public String localVolumeById(long volumeId) {

        String volumeName = "";
        Cursor cursor = contentResolver.query(TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                new String[]{TrackedVolumeEntry.COLUMN_VOLUME_NAME},
                TrackedVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                new String[]{String.valueOf(volumeId)},
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            volumeName = cursor.getString(0);
            cursor.close();
        }
        return volumeName;
    }

    public boolean isIssueMarked(long issueId) {

        boolean marked = false;
        Cursor cursor = contentResolver.query(IssueEntry.CONTENT_URI_OWNED_ISSUES,
                null,
                IssueEntry.COLUMN_ISSUE_ID + " = ?",
                new String[]{String.valueOf(issueId)},
                null);
        if (cursor != null) {
            marked = cursor.getCount() > 0;
            cursor.close();
        }

        return marked;
    }

    public boolean isVolumeLocaled(long volumeId) {
        boolean localed = false;
        Cursor cursor = contentResolver.query(TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                null,
                TrackedVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                new String[]{String.valueOf(volumeId)},
                null);

        if (cursor != null) {
            localed = cursor.getCount() > 0;
            cursor.close();
        }
        return localed;
    }

    public void localIssueToDB(@NonNull ComicIssueList issueList) {
        contentResolver.insert(IssueEntry.CONTENT_URI_OWNED_ISSUES,
                ContentUtils.contentValuesFromIssue(issueList));
    }

    public void deleteLocalIssueFromDB(long issueId) {
        Uri deletionUri = ContentUtils
                .detailsUri(IssueEntry.CONTENT_URI_OWNED_ISSUES, issueId);
        contentResolver.delete(deletionUri, null, null);
    }

    public void localVolumeToDB(@NonNull ComicVolumeList volumeList) {
        contentResolver.insert(
                TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES,
                ContentUtils.contentValuesFromVolume(volumeList));
    }

    public void deleteLocalVolumeFromDB(long volumeId) {
        Uri deletionUri = ContentUtils
                .detailsUri(TrackedVolumeEntry.CONTENT_URI_TRACKED_VOLUMES, volumeId);
        contentResolver.delete(deletionUri, null, null);
    }
}

