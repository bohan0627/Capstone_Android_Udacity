package com.bohan.android.capstone.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bohan.android.capstone.model.data.ComicContract.IssueEntry;
import com.bohan.android.capstone.model.data.ComicContract.LocalVolumeEntry;
/**
 * Created by Bo Han
 * This is for creating and updating the database and tables
 */
public class ComicsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "comics.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String LONG_TYPE = " BIGINT";
    private static final String SEPARATOR = ",";

    private static final String SQL_CREATE_TODAY_ISSUES_TABLE =
            "CREATE TABLE " + IssueEntry.TABLE_NAME_TODAY_ISSUES + " (" +
                    IssueEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    IssueEntry.COLUMN_ISSUE_ID + LONG_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_NUMBER + INTEGER_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_NAME + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_FIRST_STORE_DATE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_COVER_DATE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_SMALL_IMAGE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_HD_IMAGE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_VOLUME_ID + LONG_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_VOLUME_NAME + TEXT_TYPE + SEPARATOR +
                    " UNIQUE (" + IssueEntry.COLUMN_ISSUE_ID + ") ON CONFLICT REPLACE);";

    private static final String SQL_CREATE_OWNED_ISSUES_TABLE =
            "CREATE TABLE " + IssueEntry.TABLE_NAME_OWNED_ISSUES + " (" +
                    IssueEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    IssueEntry.COLUMN_ISSUE_ID + LONG_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_NUMBER + INTEGER_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_NAME + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_FIRST_STORE_DATE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_COVER_DATE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_SMALL_IMAGE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_MEDIUM_IMAGE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_HD_IMAGE + TEXT_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_VOLUME_ID + LONG_TYPE + SEPARATOR +
                    IssueEntry.COLUMN_ISSUE_VOLUME_NAME + TEXT_TYPE + SEPARATOR +
                    " UNIQUE (" + IssueEntry.COLUMN_ISSUE_ID + ") ON CONFLICT REPLACE);";

    private static final String SQL_CREATE_local_volumes_TABLE =
            "CREATE TABLE " + LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES + " (" +
                    LocalVolumeEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    LocalVolumeEntry.COLUMN_VOLUME_ID + LONG_TYPE + SEPARATOR +
                    LocalVolumeEntry.COLUMN_VOLUME_NAME + TEXT_TYPE + SEPARATOR +
                    LocalVolumeEntry.COLUMN_VOLUME_ISSUES_COUNT + INTEGER_TYPE + SEPARATOR +
                    LocalVolumeEntry.COLUMN_VOLUME_PUBLISHER_NAME + TEXT_TYPE + SEPARATOR +
                    LocalVolumeEntry.COLUMN_VOLUME_START_YEAR + TEXT_TYPE + SEPARATOR +
                    LocalVolumeEntry.COLUMN_VOLUME_SMALL_IMAGE + TEXT_TYPE + SEPARATOR +
                    LocalVolumeEntry.COLUMN_VOLUME_MEDIUM_IMAGE + TEXT_TYPE + SEPARATOR +
                    LocalVolumeEntry.COLUMN_VOLUME_HD_IMAGE + TEXT_TYPE + SEPARATOR +
                    " UNIQUE (" + LocalVolumeEntry.COLUMN_VOLUME_ID + ") ON CONFLICT REPLACE);";


    public ComicsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TODAY_ISSUES_TABLE);
        db.execSQL(SQL_CREATE_OWNED_ISSUES_TABLE);
        db.execSQL(SQL_CREATE_local_volumes_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocalVolumeEntry.TABLE_NAME_LOCAL_VOLUMES);
        db.execSQL("DROP TABLE IF EXISTS " + IssueEntry.TABLE_NAME_OWNED_ISSUES);
        db.execSQL("DROP TABLE IF EXISTS " + IssueEntry.TABLE_NAME_TODAY_ISSUES);
        onCreate(db);
    }
}
