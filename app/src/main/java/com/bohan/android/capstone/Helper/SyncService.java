package com.bohan.android.capstone.Helper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 */
public class SyncService extends Service {

    private static SyncAdapter syncAdapter;
    private static final Object syncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new SyncAdapter(getApplicationContext());
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
