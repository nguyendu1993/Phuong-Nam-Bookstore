package com.example.quanlysach.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quanlysach.Adapter.HDDetailAdapter;
import com.example.quanlysach.Dao.HDCTDao;
import com.example.quanlysach.Model.HDCTModel;
import com.example.quanlysach.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailBill extends Fragment {

    public static HDDetailAdapter adapter;
    ListView lvDetail;
    DatabaseReference myRef;
    FirebaseDatabase database;

    HDCTDao dao;
    ArrayList<HDCTModel> hdctModels;


    public DetailBill() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_bill, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvDetail = view.findViewById(R.id.lvDetail);

        dao = new HDCTDao(getContext());
        hdctModels = (ArrayList<HDCTModel>)dao.getAll();
        adapter = new HDDetailAdapter(getContext(),hdctModels);
        lvDetail.setAdapter(adapter);
    }

    public static void capnhatLV() {
        adapter.notifyDataSetChanged();
    }

}
