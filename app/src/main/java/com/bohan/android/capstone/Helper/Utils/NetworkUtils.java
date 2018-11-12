package com.bohan.android.capstone.Helper.Utils;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.bohan.android.capstone.R;

/**
 * Created by Bo Han.
 */
@SuppressWarnings("deprecation")
public class NetworkUtils {

    private static final int NOTIFICATION_ID = 123123;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void updateNotification(Context context, String notificationText) {

        Bitmap iconBitmap = BitmapFactory.decodeResource(
                context.getResources(),
                R.mipmap.ic_launcher);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_library_books_black_24dp)
                .setLargeIcon(iconBitmap)
                .setContentTitle(context.getString(R.string.notification_message_text))
                .setContentText(notificationText)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
