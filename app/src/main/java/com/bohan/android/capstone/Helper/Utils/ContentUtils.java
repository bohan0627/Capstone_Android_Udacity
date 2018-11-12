package com.bohan.android.capstone.Helper.Utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.model.ComicModel.ComicIssue;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeShort;
import com.bohan.android.capstone.model.data.ComicContract.IssueEntry;
import com.bohan.android.capstone.model.data.ComicContract.TrackedVolumeEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.annotations.NonNull;

/**
 * Created by Bo Han.
 */
public class ContentUtils {
    public static ComicIssueList issueWithShortInfo(ComicIssue issue) {
        return ComicIssueList.builder()
                .issueId(issue.issueId())
                .issueMainImage(issue.issueMainImage())
                .issueNumber(issue.issueNumber())
                .issueName(issue.issueName())
                .issueFirstStoreDate(issue.issueFirstStoreDate())
                .issueCoverDate(issue.issueCoverDate())
                .volume(issue.volume())
                .build();
    }

    public static ComicVolumeList shortenVolumeInfo(ComicVolumeInfo volume) {
        return ComicVolumeList.builder()
                .id(volume.id())
                .count_of_issues(volume.count_of_issues())
                .image(volume.image())
                .name(volume.name())
                .publisher(volume.publisher())
                .start_year(volume.start_year())
                .build();
    }

    public static ContentValues issueInfoToContentValues(@NonNull ComicIssueList issue) {

        ContentValues values = new ContentValues();
        values.put(IssueEntry.COLUMN_ISSUE_ID, issue.id());
        values.put(IssueEntry.COLUMN_ISSUE_NUMBER, issue.issue_number());
        values.put(IssueEntry.COLUMN_ISSUE_NAME, issue.name());
        values.put(IssueEntry.COLUMN_ISSUE_STORE_DATE, issue.store_date());
        values.put(IssueEntry.COLUMN_ISSUE_COVER_DATE, issue.cover_date());
        values.put(IssueEntry.COLUMN_ISSUE_SMALL_IMAGE, issue.image().small_url());
        values.put(IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE, issue.image().medium_url());
        values.put(IssueEntry.COLUMN_ISSUE_HD_IMAGE, issue.image().super_url());
        values.put(IssueEntry.COLUMN_ISSUE_VOLUME_ID, issue.volume().id());
        values.put(IssueEntry.COLUMN_ISSUE_VOLUME_NAME, issue.volume().name());

        return values;
    }

    public static Set<Long> getIdsFromCursor(Cursor cursor) {

        Set<Long> result = new HashSet<>(cursor.getCount());

        cursor.moveToPosition(-1);

        while(cursor.moveToNext()) {
            long id = cursor.getLong(0);
            result.add(id);
        }

        return result;
    }


    public static List<ComicIssueList> issueInfoFromCursor(Cursor cursor) {

        List<ComicIssueList> issues = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(-1);

            while (cursor.moveToNext()) {

                long id = cursor
                        .getLong(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_ID));
                int number = cursor
                        .getInt(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_NUMBER));
                String name = cursor
                        .getString(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_NAME));
                String storeDate = cursor
                        .getString(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_FIRST_STORE_DATE));
                String coverDate = cursor
                        .getString(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_COVER_DATE));
                String small = cursor
                        .getString(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_SMALL_IMAGE));
                String medium = cursor
                        .getString(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE));
                String hd = cursor
                        .getString(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_HD_IMAGE));
                long volumeId = cursor
                        .getLong(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_VOLUME_ID));
                String volumeName = cursor
                        .getString(cursor.getColumnIndexOrThrow(IssueEntry.COLUMN_ISSUE_VOLUME_NAME));

                ComicIssueList issue = ComicIssueList.builder()
                        .issueId(id)
                        .issueMainImage(
                                ComicImageHelper.builder()
                                        .imageIconUrl("")
                                        .imageMediumUrl(medium)
                                        .imageScreenUrl("")
                                        .imageSmallUrl(small)
                                        .imageSuperUrl(hd)
                                        .imageThumbUrl("")
                                        .imageTinyurl("")
                                        .build())
                        .issueNumber(number)
                        .issueName(name)
                        .issueFirstStoreDate(storeDate)
                        .issueCoverDate(coverDate)
                        .volume(
                                ComicVolumeShort.builder()
                                        .id(volumeId)
                                        .name(volumeName)
                                        .build())
                        .build();

                issues.add(issue);
            }
        }

        return issues;
    }

    public static ContentValues volumeInfoToContentValues(@NonNull ComicVolumeList volume) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_ID, volume.volumeId());
        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_NAME, volume.volumeName());
        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_ISSUES_COUNT, volume.volumeIssuesCount());
        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_PUBLISHER_NAME, volume.mainPublisher().publisherName());
        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_START_YEAR, volume.volumeStartYear());
        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_SMALL_IMAGE, volume.volumeMainImage().imageSmallUrl());
        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_MEDIUM_IMAGE, volume.volumeMainImage().imageMediumUrl());
        contentValues.put(TrackedVolumeEntry.COLUMN_VOLUME_HD_IMAGE, volume.volumeMainImage().imageSuperUrl());

        return contentValues;
    }


    public static Uri buildDetailsUri(Uri baseUri, long recordId) {
        return baseUri.buildUpon()
                .appendPath(String.valueOf(recordId))
                .build();
    }
}
