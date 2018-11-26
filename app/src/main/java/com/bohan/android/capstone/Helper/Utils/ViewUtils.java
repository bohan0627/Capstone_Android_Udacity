package com.bohan.android.capstone.Helper.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bohan.android.capstone.Helper.ModelHelper.ComicLceFragment;

import io.reactivex.annotations.NonNull;

/**
 * Created by Bo Han.
 */
public class ViewUtils {
    /**
     * Tints menu item in the given menu with chosen color.
     *
     * @param context Current context
     * @param menu Target menu
     * @param itemId Target menu item
     * @param color Desired color
     */
    public static void colorMenu(Context context, Menu menu, int itemId, int color) {
        Drawable icon = menu.findItem(itemId).getIcon();
        icon = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(icon, ContextCompat.getColor(context, color));
        menu.findItem(itemId).setIcon(icon);
    }

    /**
     * Sets ListView height dynamically based on all items heights sum.
     * Function assumes that all items have the same height.
     *
     * @param listView Target listView
     */
    public static void setListViewHeight(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        //Corner case
        if (listAdapter == null)
            return;

        int itemsCount = listAdapter.getCount();
        View listItem = listAdapter.getView(0, null, listView);

        listItem.measure(0, 0);
        int singleItemHeight = listItem.getMeasuredHeight();

        int totalHeight = singleItemHeight * itemsCount;
        int dividersHeight = listView.getDividerHeight() * (itemsCount - 1);

        totalHeight += dividersHeight;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void addFragment(@NonNull FragmentManager fragmentManager, @NonNull ComicLceFragment comicFragment, int id) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(id, comicFragment);
        transaction.commit();
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, String tag, int id, boolean instruction) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, fragment, tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (instruction)
            transaction.addToBackStack(tag);
        transaction.commit();
    }
}
