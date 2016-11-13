package com.abhimanbhau.prepgre.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("ResourceAsColor")
public class Utility {

	public static void makeCrouton(int id, Activity context) {
		Configuration configuration = new Configuration.Builder().setDuration(
				700).build();
		Style styleError = new Style.Builder()
				.setBackgroundColor(android.R.color.holo_red_dark)
				.setTextColor(android.R.color.black)
				.setConfiguration(configuration).build();
		Style styleInfo = new Style.Builder()
				.setBackgroundColor(android.R.color.holo_blue_bright)
				.setTextColor(android.R.color.black)
				.setConfiguration(configuration).build();
		switch (id) {
		case 1:
			Crouton.makeText(context, "Enter a word to search!", styleError)
					.show();
			break;
		case 2:
			Crouton.makeText(context, "No data received!", styleError).show();
			break;
		case 3:
			Crouton.makeText(context, "Error in processing!", styleError)
					.show();
			break;
		case 4:
			Crouton.makeText(context,
					"No network found, Connect to working internet.",
					styleError).show();
			break;
		default:
			Crouton.makeText(context, "Loading completed.", styleInfo).show();
			break;
		}
	}

	public static void hideKeyBoard(FragmentActivity a) {
		InputMethodManager inputManager = (InputMethodManager) a
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager == null) {
			return;
		}
		//noinspection ConstantConditions
		inputManager.hideSoftInputFromWindow(a.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

	}

	public static String checkCacheAndRetrieve(String wordName, boolean isMnemonic,
			Activity a) {
		try {
			String fileName;
			if (isMnemonic) {
				fileName = wordName + "-m";
			} else {
				fileName = wordName + "-e";
			}
			File file = a.getFileStreamPath(fileName);
			if (file.exists()) {
				String line;
				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				return sb.toString().replaceAll("`",
						System.getProperty("line.separator"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
