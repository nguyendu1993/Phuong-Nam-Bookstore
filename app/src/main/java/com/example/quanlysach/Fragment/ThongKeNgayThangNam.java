package com.example.quanlysach.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quanlysach.Model.BillModel;
import com.example.quanlysach.Model.HDCTModel;
import com.example.quanlysach.Model.Sach;
import com.example.quanlysach.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThongKeNgayThangNam extends Fragment {


    public ThongKeNgayThangNam() {
        // Required empty public constructor
    }

    TextView tvtkngay, tvtkthang, tvtknam;

    BillModel bill;
    HDCTModel hdct;
    Sach sach;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;

    int thanhtien;
    int tongngay;

    int tongthang;
    int tongnam;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_ngay_thang_nam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvtkngay = view.findViewById(R.id.tvthongkengay);
        tvtkthang = view.findViewById(R.id.tvthongkethang);
        tvtknam = view.findViewById(R.id.tvthongkenam);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("HDCT");
        databaseReference2 = firebaseDatabase.getReference("Bill");
        databaseReference3 = firebaseDatabase.getReference("Sach");


        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot dataSnapshotSach : dataSnapshot.getChildren()) {
                    sach = dataSnapshotSach.getValue(Sach.class);
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (final DataSnapshot dataSnapshotBill : dataSnapshot.getChildren()) {

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (final DataSnapshot dataSnapshotHDCT : dataSnapshot.getChildren()) {
//                                            hdct = dataSnapshotHDCT.getValue(HDCTModel.class);
                                            List<String> listgia = new ArrayList<>();
                                            hdct = dataSnapshotHDCT.getValue(HDCTModel.class);
                                            bill = dataSnapshotBill.getValue(BillModel.class);
                                            sach = dataSnapshotSach.getValue(Sach.class);

                                            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                                            String currentDate = sf.format(new Date());
                                            String[] today = currentDate.split("/");


                                            String ngaymua = bill.getNgaytao();

                                            String[] out = ngaymua.split("/");


                                            List<String> listngay = new ArrayList<>();
                                            listngay.add(today[0]);

                                            List<String> listthang = new ArrayList<>();
                                            listthang.add(today[1]);


                                            String gia = dataSnapshotSach.child("gia").getValue(String.class);
                                            listgia.add(gia);

                                            for (String list : listgia) {
                                                if (hdct.getSanpham().equalsIgnoreCase(sach.getTenSach())) {
                                                    if (hdct.getMahd().equals(bill.getMahoadon())) {


                                                        if (out[0].equals(today[0])) {
                                                            thanhtien = Integer.valueOf(list) * Integer.valueOf(hdct.getSlsanpham());
                                                        }

                                                        if (hdct.getMahd().equals(bill.getMahoadon())) {

                                                            if (out[0].equals(today[0])) {
                                                                if (out[1].equals(today[1])) {
                                                                    if (out[2].equals(today[2])) {
                                                                        for (int i = 0; i < listngay.size(); i++) {
                                                                            tongngay += thanhtien;
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }

                                                        tvtkngay.setText("" + tongngay + " VND");


                                                        //tong thang

                                                        if (out[1].equals(today[1])) {
                                                            thanhtien = Integer.valueOf(list) * Integer.valueOf(hdct.getSlsanpham());
                                                        }

                                                        if (out[1].equals(today[1])) {
                                                            if (out[2].equals(today[2])) {
                                                                for (int i = 0; i < listthang.size(); i++) {
                                                                    tongthang += thanhtien;
                                                                }
                                                            }
                                                        }

                                                        tvtkthang.setText("" + tongthang +" VND");


                                                        //tong nam

                                                        if (out[2].equals(today[2])) {
                                                            thanhtien = Integer.valueOf(list) * Integer.valueOf(hdct.getSlsanpham());
                                                        }
                                                        if (out[2].equals(today[2])) {
                                                            for (int i = 0; i < listthang.size(); i++) {
                                                                tongnam += thanhtien;

                                                            }
                                                        }

                                                        tvtknam.setText(""+tongnam+" VND");


                                                    }
                                                }
                                            }


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
