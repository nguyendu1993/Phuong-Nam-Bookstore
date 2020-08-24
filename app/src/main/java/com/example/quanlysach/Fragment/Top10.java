package com.example.quanlysach.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.quanlysach.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Top10 extends Fragment {

    ListView lvTop10;
    ImageButton btnsearch;
    EditText edtsearch;

    public Top10() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }



}
