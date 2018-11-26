package com.bohan.android.capstone.model.data.Local;

import com.bohan.android.capstone.MVP.widget.WidgetFactory;


import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@ComicLocalScope
@Subcomponent(modules = {ComicLocalSourceModule.class})
public interface ComicLocalWidgetComponent {

    void inject(WidgetFactory widgetfactory);
}
