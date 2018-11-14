package com.bohan.android.capstone.model.data.Local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

import com.bohan.android.capstone.Helper.Utils.ContentUtils;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.model.data.ComicContract.IssueEntry;
import com.bohan.android.capstone.model.data.ComicContract.LocalVolumeEntry;

import java.util.List;
import java.util.Set;

/**
 * Created by Bo Han.
 */
public class ComicLocalSourceHelper {

    private final ContentResolver contentResolver;

    @Inject
    public ComicLocalSourceHelper(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void issuesTodayToDB(@NonNull List<ComicIssueList> issueList) {

        for (ComicIssueList issue : issueList) {
            contentResolver.insert(IssueEntry.CONTENT_URI_TODAY_ISSUES,
                    ContentUtils.contentValuesFromIssue(issue));
        }
    }

    public Single<List<ComicIssueList>> issuesTodayFromDB() {
        return Single.create(e -> {
            Cursor cursor = contentResolver
                    .query(IssueEntry.CONTENT_URI_TODAY_ISSUES, null, null, null, null);

            if (cursor != null) {
                List<ComicIssueList> list = ContentUtils.issuesFromCursor(cursor);
                cursor.close();
                e.onSuccess(list);
            } });
    }

    public Single<List<ComicIssueList>> issuesLocalFromDB() {
        return Single.create(e -> {
            Cursor cursor = contentResolver
                    .query(IssueEntry.CONTENT_URI_LOCAL_ISSUES,
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

    public boolean isIssueMarked(long issueId) {
        boolean marked = false;
        Cursor cursor = contentResolver.query(
                IssueEntry.CONTENT_URI_LOCAL_ISSUES,
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

    public void issueTodayToDB(@NonNull ComicIssueList issueList) {
        contentResolver.insert(
                IssueEntry.CONTENT_URI_LOCAL_ISSUES,
                ContentUtils.contentValuesFromIssue(issueList));
    }

    public void deleteLocalIssueFromDB(long issueId) {
        Uri deletionUri = ContentUtils
                .detailsUri(IssueEntry.CONTENT_URI_LOCAL_ISSUES, issueId);
        contentResolver.delete(deletionUri, null, null);
    }

    public Set<Long> volumeIdsTodayFromDB() {
        Set<Long> volumeIds = null;

        Cursor cursor = contentResolver.query(
                IssueEntry.CONTENT_URI_TODAY_ISSUES,
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

        Cursor cursor = contentResolver.query(
                LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES,
                new String[]{LocalVolumeEntry.COLUMN_VOLUME_ID},
                null,
                null,
                null);

        if (cursor != null) {
            volumeIds = ContentUtils.idsFromCursor(cursor);
            cursor.close();
        }

        return volumeIds;
    }

    public String localVolumeNameById(long volumeId) {

        String volumeName = "";

        Cursor cursor = contentResolver.query(
                LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES,
                new String[]{LocalVolumeEntry.COLUMN_VOLUME_NAME},
                LocalVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                new String[]{String.valueOf(volumeId)},
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            volumeName = cursor.getString(0);
            cursor.close();
        }
        return volumeName;
    }

    public boolean isVolumeLocal(long volumeId) {
        boolean local = false;
        Cursor cursor = contentResolver.query(
                LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES,
                null,
                LocalVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                new String[]{String.valueOf(volumeId)},
                null);

        if (cursor != null) {
            local = cursor.getCount() > 0;
            cursor.close();
        }
        return local;
    }

    public void localVolumeToDB(@NonNull ComicVolumeList volumeList) {
        contentResolver.insert(
                LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES,
                ContentUtils.contentValuesFromVolume(volumeList));
    }

    public void deleteLocalVolumeFromDB(long volumeId) {
        Uri deletion = ContentUtils
                .detailsUri(LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES, volumeId);
        contentResolver.delete(deletion, null, null);
    }
}
