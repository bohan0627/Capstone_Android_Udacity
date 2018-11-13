package com.bohan.android.capstone.Helper.Utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.model.ComicModel.ComicIssue;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.ComicModel.ComicVolume;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeShort;
import com.bohan.android.capstone.model.data.ComicContract.IssueEntry;
import com.bohan.android.capstone.model.data.ComicContract.LocalVolumeEntry;

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

    public static ComicVolumeList volumeWithShortInfo(ComicVolume volume) {
        return ComicVolumeList.builder()
                .volumeId(volume.volumeId())
                .volumeIssuesCount(volume.issuesCount())
                .volumeMainImage(volume.volumeMainImage())
                .volumeName(volume.volumeName())
                .mainPublisher(volume.mainPublisher())
                .volumeStartYear(volume.volumeStartYear())
                .build();
    }

    public static ContentValues contentValuesFromIssue(@NonNull ComicIssueList issueList) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(IssueEntry.COLUMN_ISSUE_ID, issueList.issueId());
        contentValues.put(IssueEntry.COLUMN_ISSUE_NUMBER, issueList.issueNumber());
        contentValues.put(IssueEntry.COLUMN_ISSUE_NAME, issueList.issueName());
        contentValues.put(IssueEntry.COLUMN_ISSUE_FIRST_STORE_DATE, issueList.issueFirstStoreDate());
        contentValues.put(IssueEntry.COLUMN_ISSUE_COVER_DATE, issueList.issueCoverDate());
        contentValues.put(IssueEntry.COLUMN_ISSUE_SMALL_IMAGE, issueList.issueMainImage().imageSmallUrl());
        contentValues.put(IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE, issueList.issueMainImage().imageMediumUrl());
        contentValues.put(IssueEntry.COLUMN_ISSUE_HD_IMAGE, issueList.issueMainImage().imageSuperUrl());
        contentValues.put(IssueEntry.COLUMN_ISSUE_VOLUME_ID, issueList.volume().volumeId());
        contentValues.put(IssueEntry.COLUMN_ISSUE_VOLUME_NAME, issueList.volume().volumeName());

        return contentValues;
    }

    public static ContentValues contentValuesFromVolume(@NonNull ComicVolumeList volumeList) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_ID, volumeList.volumeId());
        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_NAME, volumeList.volumeName());
        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_ISSUES_COUNT, volumeList.volumeIssuesCount());
        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_PUBLISHER_NAME, volumeList.mainPublisher().publisherName());
        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_START_YEAR, volumeList.volumeStartYear());
        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_SMALL_IMAGE, volumeList.volumeMainImage().imageSmallUrl());
        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_MEDIUM_IMAGE, volumeList.volumeMainImage().imageMediumUrl());
        contentValues.put(LocalVolumeEntry.COLUMN_VOLUME_HD_IMAGE, volumeList.volumeMainImage().imageSuperUrl());

        return contentValues;
    }

    public static Set<Long> idsFromCursor(Cursor cursor) {

        Set<Long> ids = new HashSet<>(cursor.getCount());

        cursor.moveToPosition(-1);

        while(cursor.moveToNext()) {
            long id = cursor.getLong(0);
            ids.add(id);
        }

        return ids;
    }


    public static List<ComicIssueList> issuesFromCursor(Cursor cursor) {

        List<ComicIssueList> issues = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(-1);

            while (cursor.moveToNext()) {

                long issueId = cursor
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
                        .issueId(issueId)
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
                                        .volumeId(volumeId)
                                        .volumeName(volumeName)
                                        .build())
                        .build();

                issues.add(issue);
            }
        }

        return issues;
    }




    public static Uri detailsUri(Uri baseUri, long id) {
        return baseUri.buildUpon()
                .appendPath(String.valueOf(id))
                .build();
    }
}
