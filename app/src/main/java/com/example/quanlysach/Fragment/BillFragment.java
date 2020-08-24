package com.example.quanlysach.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlysach.R;

public class BillFragment extends Fragment {
    Button btnFragmentBill,btnFragMentDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bill_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnFragmentBill = view.findViewById(R.id.btnFragmentBill);
        btnFragMentDetail = view.findViewById(R.id.btnFragmentDetail);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Bill()).commit();


        btnFragmentBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Bill()).commit();
            }
        });

        btnFragMentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new DetailBill()).commit();
            }
        });


    }
}
