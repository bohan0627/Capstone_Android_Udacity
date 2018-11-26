package com.bohan.android.capstone;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.test.runner.AndroidJUnit4;

import com.bohan.android.capstone.Helper.Utils.ContentUtils;
import com.bohan.android.capstone.model.data.ComicContract;
import com.bohan.android.capstone.model.data.Local.ComicsDBHelper;
import com.bohan.android.capstone.model.data.Local.ComicProvider;
import com.bohan.android.capstone.model.data.ComicContract.IssueEntry;
import com.bohan.android.capstone.model.data.ComicContract.LocalVolumeEntry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

//import static android.support.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Bo Han.
 */

@RunWith(AndroidJUnit4.class)
public class ComicLocalProviderTest {

    private static final Uri TEST_TODAY_ISSUES = IssueEntry.CONTENT_URI_TODAY_ISSUES;
    private static final Uri TEST_LOCAL_ISSUES = IssueEntry.CONTENT_URI_LOCAL_ISSUES;
    private static final Uri TEST_LOCAL_VOLUMES = LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES;

    private static final long TEST_ISSUE_ID = 123;
    private static final long TEST_VOLUME_ID = 321;

    private static final Uri TEST_LOCAL_ISSUES_WITH_ID = ContentUtils
            .detailsUri(TEST_LOCAL_ISSUES, TEST_ISSUE_ID);

    private static final Uri TEST_LOCAL_VOLUMES_WITH_ID = ContentUtils
            .detailsUri(TEST_LOCAL_VOLUMES, TEST_VOLUME_ID);

    private UriMatcher testMatcher;
    private Context context;
    private ContentResolver contentResolver;

    @Before
    public void setUp() {
        testMatcher = ComicProvider.buildMatcher();
        context = getTargetContext();
        contentResolver = context.getContentResolver();
    }

    @Test
    public void testUriMatcher() {
        assertEquals("Error: Today's issues uri was matched incorrectly.",
                testMatcher.match(TEST_TODAY_ISSUES),
                ComicProvider.CODE_TODAY_ISSUES);

        assertEquals("Error: Owned issues uri was matched incorrectly.",
                testMatcher.match(TEST_LOCAL_ISSUES),
                ComicProvider.CODE_LOCAL_ISSUES);

        assertEquals("Error: Tracked volumes uri was matched incorrectly.",
                testMatcher.match(TEST_LOCAL_VOLUMES),
                ComicProvider.CODE_LOCAL_VOLUMES);

        assertEquals("Error: Owned issue by id uri was matched incorrectly.",
                testMatcher.match(TEST_LOCAL_ISSUES_WITH_ID),
                ComicProvider.CODE_LOCAL_ISSUES_WITH_ID);

        assertEquals("Error: Tracked volume by id uri was matched incorrectly.",
                testMatcher.match(TEST_LOCAL_VOLUMES_WITH_ID),
                ComicProvider.CODE_LOCAL_VOLUMES_WITH_ID);
    }

    @Test
    public void testProviderRegistration() {

        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        ComponentName component = new ComponentName(packageName, ComicProvider.class.getName());

        try {
            ProviderInfo providerInfo = packageManager.getProviderInfo(component, 0);
            assertEquals("Error: Detected ComicProvider authority: " + providerInfo.authority +
                            ", required ComicProvider authority: " + ComicContract.CONTENT_AUTHORITY,
                    providerInfo.authority, ComicContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: ComicProvider not registered at " + context.getPackageName(), false);
        }
    }

    @Test
    public void testTodayIssuesQuery() {
        ComicsDBHelper comicDbHelper = new ComicsDBHelper(context);
        SQLiteDatabase database = comicDbHelper.getWritableDatabase();
        ContentValues testValues = ContentUtils.contentValuesFromIssue(UtilsTest.getDummyIssueInfo());

        // Insert content via database
        database.insert(IssueEntry.TABLE_NAME_TODAY_ISSUES, null, testValues);
        database.close();

        // Query content via provider
        Cursor queryCursor = contentResolver.query(
                IssueEntry.CONTENT_URI_TODAY_ISSUES,
                null,
                null,
                null,
                null);

        UtilsTest.validateCursor("testTodayIssuesQuery", queryCursor, testValues);
    }

    @Test
    public void testOwnedIssuesQuery() {
        ComicsDBHelper comicDbHelper = new ComicsDBHelper(context);
        SQLiteDatabase database = comicDbHelper.getWritableDatabase();
        ContentValues testValues = ContentUtils
                .contentValuesFromIssue(UtilsTest.getDummyIssueInfo());

        // Insert content via database
        database.insert(IssueEntry.TABLE_NAME_LOCAL_ISSUES, null, testValues);
        database.close();

        // Query content via provider
        Cursor queryCursor = contentResolver.query(
                IssueEntry.CONTENT_URI_LOCAL_ISSUES,
                null,
                null,
                null,
                null);

        UtilsTest.validateCursor("testOwnedIssuesQuery", queryCursor, testValues);
    }

    @Test
    public void testTrackedVolumesQuery() {
        ComicsDBHelper comicDbHelper = new ComicsDBHelper(context);
        SQLiteDatabase database = comicDbHelper.getWritableDatabase();
        ContentValues testValues = ContentUtils
                .contentValuesFromVolume(UtilsTest.getDummyVolumeInfo());

        // Insert content via database
        database.insert(LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES, null, testValues);
        database.close();

        // Query content via provider
        Cursor queryCursor = contentResolver.query(
                LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES,
                null,
                null,
                null,
                null);

        UtilsTest.validateCursor("testTrackedVolumesQuery", queryCursor, testValues);
    }

    @Test
    public void testRecordsDeletion() {

        deleteAllRecordsViaProvider();

        Cursor todayIssues = contentResolver
                .query(IssueEntry.CONTENT_URI_TODAY_ISSUES, null, null, null, null);
        assertEquals("Error: Records were not deleted from today issues table", 0,
                todayIssues.getCount());
        todayIssues.close();

        Cursor ownedIssues = contentResolver
                .query(IssueEntry.CONTENT_URI_LOCAL_ISSUES, null, null, null, null);
        assertEquals("Error: Records were not deleted from owned issues table", 0,
                ownedIssues.getCount());
        ownedIssues.close();

        Cursor trackedVolumes = contentResolver
                .query(LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES, null, null, null, null);
        assertEquals("Error: Records were not deleted from owned issues table", 0,
                trackedVolumes.getCount());
        trackedVolumes.close();

    }

    @Test
    public void testTodayIssuesInsert() {

        deleteAllRecordsViaProvider();

        ContentValues testValues = ContentUtils.contentValuesFromIssue(UtilsTest.getDummyIssueInfo());

        // Insert content via provider
        contentResolver.insert(IssueEntry.CONTENT_URI_TODAY_ISSUES, testValues);

        // Query content via provider
        Cursor queryCursor = contentResolver.query(
                IssueEntry.CONTENT_URI_TODAY_ISSUES,
                null,
                null,
                null,
                null);

        UtilsTest.validateCursor("testTodayIssuesInsert", queryCursor, testValues);
    }

    @Test
    public void testOwnedIssuesInsert() {

        deleteAllRecordsViaProvider();

        ContentValues testValues = ContentUtils
                .contentValuesFromIssue(UtilsTest.getDummyIssueInfo());

        // Insert content via provider
        contentResolver.insert(IssueEntry.CONTENT_URI_LOCAL_ISSUES, testValues);

        // Query content via provider
        Cursor queryCursor = contentResolver.query(
                IssueEntry.CONTENT_URI_LOCAL_ISSUES,
                null,
                null,
                null,
                null);

        UtilsTest.validateCursor("testOwnedIssuesInsert", queryCursor, testValues);
    }

    @Test
    public void testTrackedVolumesInsert() {

        deleteAllRecordsViaProvider();

        ContentValues testValues = ContentUtils
                .contentValuesFromVolume(UtilsTest.getDummyVolumeInfo());

        // Insert content via provider
        contentResolver.insert(LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES, testValues);

        // Query content via provider
        Cursor queryCursor = contentResolver.query(
                LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES,
                null,
                null,
                null,
                null);

        UtilsTest.validateCursor("testTrackedVolumesInsert", queryCursor, testValues);
    }

    @After
    public void cleanUp() {
        deleteAllRecordsViaProvider();
    }

    private void deleteAllRecordsViaProvider() {
        contentResolver.delete(IssueEntry.CONTENT_URI_TODAY_ISSUES, null, null);
        contentResolver.delete(IssueEntry.CONTENT_URI_LOCAL_ISSUES, null, null);
        contentResolver.delete(LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES, null, null);
    }
}
