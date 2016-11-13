package com.abhimanbhau.prepgre.code;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class EtymologyRetriever extends AsyncTask<String, Void, String> {
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

			Elements elems = doc.getElementsByClass("highlight");
			if (elems.size() == 0) {
				return "No data received!";
			}
			for (Element e : elems) {
				result += (e.text());
			}
		} catch (Exception e) {
			return null;
		}
		if (result != null) {
			result = result.replace("null", "");
			return result;
		}
		return null;
	}
}