package com.abhimanbhau.prepgre.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.abhimanbhau.prepgre.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordListFragment extends Fragment {
    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wordlist, container,
                false);
        list = (ListView) view.findViewById(R.id.lstWords);
        try {
            String line;
            List<String> lines = new ArrayList<>();
            InputStream is = getResources().getAssets().open("wordlist.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            String[] arr = lines.toArray(new String[lines.size()]);
            ArrayAdapter<String> a = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line, arr);
            list.setAdapter(a);
            list.setScrollbarFadingEnabled(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                list = (ListView) getActivity().findViewById(R.id.lstWords);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                HomeFragment f = new HomeFragment();
                Bundle b = new Bundle();
                b.putString("Word", list.getItemAtPosition(arg2).toString());
                f.setArguments(b);
                ft.replace(R.id.container, f).commit();
            }
        });

        return view;
    }
}
