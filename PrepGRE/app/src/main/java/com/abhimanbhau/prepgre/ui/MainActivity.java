package com.abhimanbhau.prepgre.ui;

import com.abhimanbhau.prepgre.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

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
		CharSequence mTitle = getTitle();
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
					.replace(R.id.container, new HomeFragment())
					.setCustomAnimations(R.anim.abc_slide_in_bottom,
							R.anim.abc_slide_in_top).commit();
			break;

		case 2:
			fragmentManager
					.beginTransaction()
					.replace(R.id.container, new WordListFragment())
					.setCustomAnimations(R.anim.abc_slide_in_bottom,
							R.anim.abc_slide_in_top).commit();
			break;

		case 3:
			Intent settingActivity = new Intent(this, SettingsActivity.class);
			startActivity(settingActivity);

			break;

		case 4:
			fragmentManager
					.beginTransaction()
					.replace(R.id.container, new AboutFragment())
					.setCustomAnimations(R.anim.abc_slide_in_bottom,
							R.anim.abc_slide_in_top).commit();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// if (!mNavigationDrawerFragment.isDrawerOpen()) {
		// Only show items in the action bar relevant to this screen
		// if the drawer is not showing. Otherwise, let the drawer
		// decide what to show in the action bar.
		// getMenuInflater().inflate(R.menu.main, menu);
		// restoreActionBar();
		// return true;
		// }
		// return super.onCreateOptionsMenu(menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		// return super.onOptionsItemSelected(item);
		return false;
	}
}
