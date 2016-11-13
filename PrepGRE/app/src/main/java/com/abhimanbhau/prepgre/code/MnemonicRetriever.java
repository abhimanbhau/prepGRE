package com.abhimanbhau.prepgre.code;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class MnemonicRetriever extends AsyncTask<String, Void, String> {
	@Override
	protected void onPreExecute() {
	}

	@Override
	protected String doInBackground(String... arg) {
		String result = null;
		try {
			Document doc = Jsoup
					.connect(arg[0])
					.timeout(7000)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36")
					.get();
			Elements elems = doc.getElementsByClass("span9");
			if (elems.size() == 0) {
				return "No data received!";
			}
			for (Element e : elems) {
				result += (e.text() + System.getProperty("line.separator") + System
						.getProperty("line.separator"));
			}
		} catch (Exception e) {
			return null;
		}
		result = result != null ? result.replace("null", "") : null;
		return result;
	}
}
