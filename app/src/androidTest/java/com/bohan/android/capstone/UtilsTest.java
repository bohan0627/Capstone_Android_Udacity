package com.bohan.android.capstone;

import android.content.ContentValues;
import android.database.Cursor;

import com.bohan.android.capstone.Helper.ModelHelper.ComicImageHelper;
import com.bohan.android.capstone.Helper.ModelHelper.ComicPublisherHelper;
import com.bohan.android.capstone.model.ComicModel.ComicIssueList;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.bohan.android.capstone.model.ComicModel.ComicVolumeShort;

import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Bo Han.
 */
class UtilsTest {

    static ComicIssueList getDummyIssueInfo() {
        return ComicIssueList.builder()
                .issueId(123)
                .issueNumber(321)
                .issueName("name")
                .issueFirstStoreDate("store_date")
                .issueCoverDate("cover_date")
                .issueMainImage(
                        ComicImageHelper.builder()
                                .imageIconUrl("icon_url")
                                .imageMediumUrl("medium_url")
                                .imageScreenUrl("screen_url")
                                .imageSmallUrl("small_url")
                                .imageSuperUrl("super_url")
                                .imageThumbUrl("thumb_url")
                                .imageTinyurl("tiny_url")
                                .build())
                .volume(
                        ComicVolumeShort.builder()
                                .volumeId(123)
                                .volumeName("name")
                                .build()
                )
                .build();
    }

    static ComicVolumeList getDummyVolumeInfo() {
        return ComicVolumeList.builder()
                .volumeIssuesCount(123)
                .volumeId(321)
                .volumeMainImage(
                        ComicImageHelper.builder()
                                .imageIconUrl("icon_url")
                                .imageMediumUrl("medium_url")
                                .imageScreenUrl("screen_url")
                                .imageSmallUrl("small_url")
                                .imageSuperUrl("super_url")
                                .imageThumbUrl("thumb_url")
                                .imageTinyurl("tiny_url")
                                .build())
                .volumeName("Tets issue")
                .mainPublisher(ComicPublisherHelper.builder()
                        .publisherName("DC Comics")
                        .publisherId(11111)
                        .build())
                .volumeStartYear(2003)
                .build();
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor,
                                      ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }
}
