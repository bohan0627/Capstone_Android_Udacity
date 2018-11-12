package com.bohan.android.capstone.MVP.VolumeDetails;

import com.bohan.android.capstone.model.ComicModel.ComicVolume;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

/**
 * Created by Bo Han.
 */
public interface VolumeDetailsView extends MvpLceView<ComicVolume> {

        void mark();
        void unmark();
        void onClick();
        }
