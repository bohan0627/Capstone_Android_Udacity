package com.bohan.android.capstone.Authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Bo Han.
 */
public class AuthenticationService extends Service {

    private AccountAuthentication authentication;

    @Override
    public void onCreate() {
        authentication = new AccountAuthentication(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authentication.getIBinder();
    }
}