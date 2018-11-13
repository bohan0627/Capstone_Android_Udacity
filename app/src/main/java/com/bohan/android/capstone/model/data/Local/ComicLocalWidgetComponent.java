package com.bohan.android.capstone.model.data.Local;

import com.bohan.android.capstone.MVP.widget.WidgetFactory;
import com.bohan.android.capstone.model.data.Local.ComicLocalScope;
import com.bohan.android.capstone.model.data.Local.ComicLocalSourceModule;
import com.bohan.android.capstone.model.data.Local.ComicProvider;
import com.bohan.android.capstone.model.data.Local.ComicsLocalDBHelperComponent;

import dagger.Subcomponent;

/**
 * Created by Bo Han.
 */
@ComicLocalScope
@Subcomponent(modules = {ComicLocalSourceModule.class})
public interface ComicLocalWidgetComponent {

    void inject(WidgetFactory widgetfactory);
}
