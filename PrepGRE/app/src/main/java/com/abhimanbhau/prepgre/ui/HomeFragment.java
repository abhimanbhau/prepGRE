package com.abhimanbhau.prepgre.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.abhimanbhau.prepgre.R;
import com.abhimanbhau.prepgre.code.EtymologyRetriever;
import com.abhimanbhau.prepgre.code.MnemonicRetriever;
import com.abhimanbhau.prepgre.code.Utility;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class HomeFragment extends Fragment {
    private AutoCompleteTextView searchBox;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        searchBox = (AutoCompleteTextView) view
                .findViewById(R.id.autoSearchBox);
        tv = (TextView) view.findViewById(R.id.txtMainDataView);

        Button btnMnemonic = (Button) view.findViewById(R.id.btnMnemonic);
        Button btnEtymology = (Button) view.findViewById(R.id.btnEtymology);

        btnMnemonic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Utility.hideKeyBoard(getActivity());
                String result = Utility.checkCacheAndRetrieve(searchBox
                        .getText().toString().trim(), true, getActivity());
                if (result != null) {
                    tv.setText(result);
                } else if (checkNetworkConnectionAndShowErrorMessage()) {
                } else if (searchBox.getText().toString().trim().equals("")) {
                    Utility.makeCrouton(1, getActivity());
                } else {
                    try {
                        Utility.makeCrouton(111, getActivity());
                        result = new MnemonicRetriever()
                                .execute(
                                        "http://mnemonicdictionary.com/word/"
                                                + searchBox.getText()
                                                .toString().trim())
                                .get();
                        if (result == null) {
                            Utility.makeCrouton(2, getActivity());
                        } else {
                            tv.setText(result);
                            saveToCache(result, true, searchBox.getText()
                                    .toString().trim());
                        }
                    } catch (Exception e) {
                        Utility.makeCrouton(3, getActivity());
                    }
                }
            }
        });

        btnEtymology.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Utility.hideKeyBoard(getActivity());
                String result = Utility.checkCacheAndRetrieve(searchBox
                        .getText().toString().trim(), false, getActivity());
                if (result != null) {
                    tv.setText(result);
                } else if (checkNetworkConnectionAndShowErrorMessage()) {
                } else if (searchBox.getText().toString().trim().equals("")) {
                    Utility.makeCrouton(1, getActivity());
                } else {
                    Utility.makeCrouton(111, getActivity());
                    try {
                        result = new EtymologyRetriever().execute(
                                "http://www.etymonline.com/index.php?term="
                                        + searchBox.getText().toString().trim()
                                        + "&allowed_in_frame=0").get();
                        if (result == null) {
                            Utility.makeCrouton(2, getActivity());
                        } else {
                            tv.setText(result);
                            saveToCache(result, false, searchBox.getText()
                                    .toString().trim());
                        }
                    } catch (Exception e) {
                        Utility.makeCrouton(3, getActivity());
                    }
                }
            }
        });
        try {
            searchBox.setAdapter(getSearchSuggestionsAdapter());
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchBox.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Utility.hideKeyBoard(getActivity());
            }
        });
        Bundle b = getArguments();
        if (b != null) {
            searchBox.setText(getArguments().getString("Word"));
        }
        if (savedInstanceState != null) {
            searchBox.setText(savedInstanceState.getString("Word"));
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        searchBox = (AutoCompleteTextView) getActivity().findViewById(
                R.id.autoSearchBox);
        outState.putString("Word", searchBox.getText().toString().trim());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }

    // Utility/Helper Methods

    private ArrayAdapter<String> getSearchSuggestionsAdapter() {
        try {
            String line;
            List<String> lines = new ArrayList<>();
            InputStream is = getResources().getAssets().open("wordlist.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            String[] arr = lines.toArray(new String[lines.size()]);
            return new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line, arr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayAdapter<>(getActivity(), 0);
    }

    private void saveToCache(String data, boolean isMnemonic, String word) {
        String fileName;
        if (isMnemonic) {
            fileName = word + "-m";
        } else {
            fileName = word + "-e";
        }
        try {
            FileOutputStream out = getActivity().openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            byte[] ff = data.getBytes();
            for (int i = 0; i < ff.length; ++i) {
                if (ff[i] == 10) {
                    ff[i] = 96;
                }
            }
            out.write(ff);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkNetworkConnectionAndShowErrorMessage() {
        ConnectivityManager connectivity = (ConnectivityManager) getActivity()
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return false;
                    }
        }
        Utility.makeCrouton(4, getActivity());
        return true;
    }
}
