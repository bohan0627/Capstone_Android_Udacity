package com.bohan.android.capstone.model.data.Local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.data.ComicContract;
import com.bohan.android.capstone.model.data.ComicContract.IssueEntry;
import com.bohan.android.capstone.model.data.ComicContract.LocalVolumeEntry;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Bo Han.
 */
public class ComicProvider extends ContentProvider {

    public static final int CODE_TODAY_ISSUES = 100;
    public static final int CODE_LOCAL_ISSUES = 200;
    public static final int CODE_LOCAL_ISSUES_WITH_ID = 201;
    public static final int CODE_LOCAL_VOLUMES = 300;
    public static final int CODE_LOCAL_VOLUMES_WITH_ID = 301;

    private static final UriMatcher uriMatcher = buildMatcher();

    @Inject
    ComicsDBHelper dbHelper;

    public static UriMatcher buildMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ComicContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ComicContract.PATH_TODAY_ISSUES, CODE_TODAY_ISSUES);
        matcher.addURI(authority, ComicContract.PATH_LOCAL_ISSUES, CODE_LOCAL_ISSUES);
        matcher.addURI(authority, ComicContract.PATH_LOCAL_ISSUES + "/#", CODE_LOCAL_ISSUES_WITH_ID);
        matcher.addURI(authority, ComicContract.PATH_LOCAL_VOLUMES, CODE_LOCAL_VOLUMES);
        matcher.addURI(authority, ComicContract.PATH_LOCAL_VOLUMES + "/#", CODE_LOCAL_VOLUMES_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {

        ComicsLoverApp
                .getAppComponent()
                .plusDbHelperComponent(new ComicsLocalDBHelperModule())
                .inject(this);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        String recordId;

        switch (uriMatcher.match(uri)) {

            case CODE_TODAY_ISSUES:
                cursor = dbHelper.getReadableDatabase().query(
                        IssueEntry.TABLE_NAME_TODAY_ISSUES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_LOCAL_ISSUES:
                cursor = dbHelper.getReadableDatabase().query(
                        IssueEntry.TABLE_NAME_LOCAL_ISSUES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_LOCAL_VOLUMES:
                cursor = dbHelper.getReadableDatabase().query(
                        LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_LOCAL_ISSUES_WITH_ID:
                recordId = uri.getLastPathSegment();
                cursor = dbHelper.getReadableDatabase().query(
                        IssueEntry.TABLE_NAME_LOCAL_ISSUES,
                        projection,
                        IssueEntry.COLUMN_ISSUE_ID + " = ?",
                        new String[]{recordId},
                        null,
                        null,
                        sortOrder);
                break;

            case CODE_LOCAL_VOLUMES_WITH_ID:
                recordId = uri.getLastPathSegment();
                cursor = dbHelper.getReadableDatabase().query(
                        LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES,
                        projection,
                        LocalVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                        new String[]{recordId},
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri (query): " + uri);
        }

        //noinspection ConstantConditions
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;
        long ids;

        switch (uriMatcher.match(uri)) {

            case CODE_TODAY_ISSUES:
                ids = db.insert(IssueEntry.TABLE_NAME_TODAY_ISSUES, null, values);
                if (ids > 0) {
                    returnUri = ContentUris.withAppendedId(IssueEntry.CONTENT_URI_TODAY_ISSUES, ids);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            case CODE_LOCAL_ISSUES:
                ids = db.insert(IssueEntry.TABLE_NAME_LOCAL_ISSUES, null, values);
                if (ids > 0) {
                    returnUri = ContentUris.withAppendedId(IssueEntry.CONTENT_URI_LOCAL_ISSUES, ids);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            case CODE_LOCAL_VOLUMES:
                ids = db.insert(LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES, null, values);
                if (ids > 0) {
                    returnUri = ContentUris.withAppendedId(LocalVolumeEntry.CONTENT_URI_LOCAL_VOLUMES, ids);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri (insert): " + uri);
        }

        //noinspection ConstantConditions
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsInserted = 0;

        switch (uriMatcher.match(uri)) {

            case CODE_TODAY_ISSUES:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long ids = db.insert(IssueEntry.TABLE_NAME_TODAY_ISSUES, null, value);
                        if (ids != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    //noinspection ConstantConditions
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            case CODE_LOCAL_ISSUES:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long ids = db.insert(IssueEntry.TABLE_NAME_LOCAL_ISSUES, null, value);
                        if (ids != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    //noinspection ConstantConditions
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            case CODE_LOCAL_VOLUMES:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long ids = db.insert(LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES, null, value);
                        if (ids != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    //noinspection ConstantConditions
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;
        String recordId;

        if (null == selection) {
            selection = "1";
        }

        switch(uriMatcher.match(uri)) {

            case CODE_TODAY_ISSUES:
                rowsDeleted = db.delete(
                        IssueEntry.TABLE_NAME_TODAY_ISSUES,
                        selection,
                        selectionArgs);
                break;

            case CODE_LOCAL_ISSUES:
                rowsDeleted = db.delete(
                        IssueEntry.TABLE_NAME_LOCAL_ISSUES,
                        selection,
                        selectionArgs);
                break;

            case CODE_LOCAL_VOLUMES:
                rowsDeleted = db.delete(
                        LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES,
                        selection,
                        selectionArgs);
                break;

            case CODE_LOCAL_ISSUES_WITH_ID:
                recordId = uri.getLastPathSegment();
                rowsDeleted = db.delete(
                        IssueEntry.TABLE_NAME_LOCAL_ISSUES,
                        IssueEntry.COLUMN_ISSUE_ID  + " = ?",
                        new String[]{recordId});
                break;

            case CODE_LOCAL_VOLUMES_WITH_ID:
                recordId = uri.getLastPathSegment();
                rowsDeleted = db.delete(
                        LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES,
                        LocalVolumeEntry.COLUMN_VOLUME_ID + " = ?",
                        new String[]{recordId});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri (delete): " + uri);
        }

        if (rowsDeleted != 0) {
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("update method not implemented");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("getType method not implemented");
    }
}

