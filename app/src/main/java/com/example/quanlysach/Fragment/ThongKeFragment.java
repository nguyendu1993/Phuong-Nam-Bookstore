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

public class ThongKeFragment extends Fragment {

    Button btnthongkengay,btntop10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.thongke_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnthongkengay = view.findViewById(R.id.btnthongkengay);
        btntop10 = view.findViewById(R.id.btntop10);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.con, new ThongKeNgayThangNam()).commit();

        btnthongkengay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.con, new ThongKeNgayThangNam()).commit();
            }
        });

        btntop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.con, new Top10()).commit();
            }
        });
    }
}
