package com.bohan.android.capstone.Helper.NavigationHelper;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import com.bohan.android.capstone.Helper.ModelHelper.ComicMvpActivity;
import com.bohan.android.capstone.Helper.SyncHelper.SyncManager;
import com.bohan.android.capstone.Helper.Utils.ViewUtils;
import com.bohan.android.capstone.model.ComicsLoverApp.ComicsLoverApp;
import com.bohan.android.capstone.model.Prefs.ComicPrefsHelper;
import com.evernote.android.state.State;

import com.bohan.android.capstone.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

import butterknife.BindBool;
import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Bo Han.
 */
public class NavigationActivity extends
        ComicMvpActivity<NavigationView, NavigationPresenter>
        implements NavigationView, OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindBool(R.bool.is_tablet_layout)
    boolean isTabletLayout;

    @State
    @ComicAppNavigation.Section
    int currentSection;

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Inject
    ComicPrefsHelper comicPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        ComicsLoverApp
                .getAppComponent()
                .inject(this);

        setSupportActionBar(toolbar);
        setUpNavigationDrawerParams();

        if (savedInstanceState == null) {
            navigateToCurrentSection();
        }

        String defaultSyncPeriod = comicPreferencesHelper.getSyncPeriod();
        SyncManager.createSyncAccount(this, Integer.parseInt(defaultSyncPeriod));
    }

    private void setUpNavigationDrawerParams() {

        if (!isTabletLayout) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_issues);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        chooseItem(id);

        if (!isTabletLayout) {
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    @NonNull
    @Override
    public NavigationPresenter createPresenter() {
        return new NavigationPresenter();
    }

    @Override
    public void chooseItem(int chosenMenuItem) {

        if (chosenMenuItem == R.id.nav_issues) {
            currentSection = ComicAppNavigation.ISSUES;
        } else if (chosenMenuItem == R.id.nav_volumes) {
            currentSection = ComicAppNavigation.VOLUMES;
        } else if (chosenMenuItem == R.id.nav_characters) {
            currentSection = ComicAppNavigation.CHARACTERS;
        } else if (chosenMenuItem == R.id.nav_collection) {
            currentSection = ComicAppNavigation.COLLECTION;
        } else if (chosenMenuItem == R.id.nav_tracker) {
            currentSection = ComicAppNavigation.TRACKER;
        } else if (chosenMenuItem == R.id.nav_settings) {
            currentSection = ComicAppNavigation.SETTINGS;
        }

        navigateToCurrentSection();
    }

    @Override
    public void navigateToCurrentSection() {

        logChosenNavigationSection(currentSection);

        FragmentManager manager = getSupportFragmentManager();

        Fragment fragment = FragmentsNavigationFactory.getFragment(manager, currentSection);

        ViewUtils.replaceFragment(
                manager, fragment,FragmentsNavigationFactory.getFragmentTag(currentSection), R.id.content_frame,
                false);

        restoreAppBarState();
    }

    private void logChosenNavigationSection(@ComicAppNavigation.Section int section) {

        Bundle bundle = new Bundle();
        bundle.putInt(Param.ITEM_ID, section);
        bundle.putString(Param.ITEM_NAME, getChosenSectionName(section));
        firebaseAnalytics.logEvent(Event.SELECT_CONTENT, bundle);
    }

    private String getChosenSectionName(@ComicAppNavigation.Section int section) {

        String chosenSection;

        switch (section) {
            case ComicAppNavigation.ISSUES:
                chosenSection = "issues";
                break;
            case ComicAppNavigation.VOLUMES:
                chosenSection = "volumes";
                break;
            case ComicAppNavigation.CHARACTERS:
                chosenSection = "characters";
                break;
            case ComicAppNavigation.COLLECTION:
                chosenSection = "collection";
                break;
            case ComicAppNavigation.TRACKER:
                chosenSection = "tracker";
                break;
            case ComicAppNavigation.SETTINGS:
                chosenSection = "settings";
                break;
            default:
                chosenSection = "unknown";
                break;
        }
        return chosenSection;
    }

    private void restoreAppBarState() {

        CoordinatorLayout coordinator = ButterKnife.findById(this, R.id.main_coordinator_layout);
        AppBarLayout appbar = ButterKnife.findById(this, R.id.appbar);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

        if (behavior != null) {
            behavior.onNestedFling(coordinator, appbar, null, 0, -1000, true);
        }
    }
}
