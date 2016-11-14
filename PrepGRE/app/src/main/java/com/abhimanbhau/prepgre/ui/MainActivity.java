package com.abhimanbhau.prepgre.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.abhimanbhau.prepgre.R;

public class MainActivity extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new HomeFragment()).commit();

        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        //CharSequence mTitle = getTitle();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position + 1) {
            case 1:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, new HomeFragment()).commit();
                break;

            case 2:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, new WordListFragment()).commit();
                break;

            case 3:
                Intent settingActivity = new Intent(this, SettingsActivity.class);
                startActivity(settingActivity);

                break;

            case 4:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, new AboutFragment())
                        .commit();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
