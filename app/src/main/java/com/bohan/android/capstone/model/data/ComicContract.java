package com.bohan.android.capstone.model.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("WeakerAccess")
public class ComicContract {

    public static final String CONTENT_AUTHORITY = "com.bohan.comicsLover";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TODAY_ISSUES = "today_issues";
    public static final String PATH_LOCAL_ISSUES = "local_issues";
    public static final String PATH_LOCAL_VOLUMES = "local_volumes";

    public static final class IssueEntry implements BaseColumns {

        public static final Uri CONTENT_URI_TODAY_ISSUES = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TODAY_ISSUES)
                .build();

        public static final String TABLE_NAME_TODAY_ISSUES = "today_issues";
        public static final String COLUMN_ISSUE_ID = "issue_id";
        public static final String COLUMN_ISSUE_NAME = "issue_name";
        public static final String COLUMN_ISSUE_NUMBER = "issue_number";
        public static final String COLUMN_ISSUE_FIRST_STORE_DATE = "issue_first_store_date";
        public static final String COLUMN_ISSUE_COVER_DATE = "issue_cover_date";
        public static final String COLUMN_ISSUE_SMALL_IMAGE = "issue_small_image";
        public static final String COLUMN_ISSUE_MEDIUM_IMAGE = "issue_medium_image";
        public static final String COLUMN_ISSUE_HD_IMAGE = "issue_super_image";
        public static final String COLUMN_ISSUE_VOLUME_ID = "issue_volume_id";
        public static final String COLUMN_ISSUE_VOLUME_NAME = "issue_volume_name";

        public static final String TABLE_NAME_LOCAL_ISSUES = " local_issues";

        public static final Uri CONTENT_URI_LOCAL_ISSUES = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_LOCAL_ISSUES)
                .build();
    }

    public static final class LocalVolumeEntry implements BaseColumns {

        public static final Uri CONTENT_URI_LOCAL_VOLUMES = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_LOCAL_VOLUMES)
                .build();

        public static final String TABLE_NAME_LOCAL_VOLUMES = "local_volumes";
        public static final String COLUMN_VOLUME_ID = "volume_id";
        public static final String COLUMN_VOLUME_NAME = "volume_name";
        public static final String COLUMN_VOLUME_ISSUES_COUNT = "volume_issues_count";
        public static final String COLUMN_VOLUME_PUBLISHER_NAME = "volume_publisher_name";
        public static final String COLUMN_VOLUME_START_YEAR = "volume_start_year";
        public static final String COLUMN_VOLUME_SMALL_IMAGE = "volume_small_image";
        public static final String COLUMN_VOLUME_MEDIUM_IMAGE = "volume_medium_image";
        public static final String COLUMN_VOLUME_HD_IMAGE = "volume_super_image";
    }
}