package com.bohan.android.capstone.MVP.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Bo Han.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplicationContext());
    }
}

