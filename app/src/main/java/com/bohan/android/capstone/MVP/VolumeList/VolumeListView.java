package com.bohan.android.capstone.MVP.VolumeList;

import com.bohan.android.capstone.model.ComicModel.ComicVolumeList;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Bo Han.
 */
public interface VolumeListView extends MvpLceView<List<ComicVolumeList>> {

    void fetchVolumeByName(String volumeName);
    void displayBaseView(boolean instruction);
    void displayEmptyView(boolean instruction);
    void setVolumeTitle(String title);
}