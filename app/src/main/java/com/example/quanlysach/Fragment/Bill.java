package com.example.quanlysach.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysach.Adapter.BillAdapter;
import com.example.quanlysach.Dao.BillDao;
import com.example.quanlysach.Model.BillModel;
import com.example.quanlysach.R;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bill extends Fragment {

    FloatingActionButton btnAddBill;
    ListView lvBill;
    DatabaseReference myRef;
    FirebaseDatabase database;

    BillDao dao;
    ArrayList<BillModel> billModels;

    public static BillAdapter adapter;

    public Bill() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("Bill");

        //load data


        lvBill = view.findViewById(R.id.lvBill);
        btnAddBill = view.findViewById(R.id.btnAddBill);



        dao = new BillDao(getContext());
        billModels = (ArrayList<BillModel>)dao.getAll();
        adapter = new BillAdapter(getContext(),billModels);
        lvBill.setAdapter(adapter);



        btnAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_bill);

                final EditText edtmahoadon = dialog.findViewById(R.id.edtmahoadon);
                final TextView tvngaytao = dialog.findViewById(R.id.tvngaytao);
                Button btnAddBill = dialog.findViewById(R.id.btnDialogAddBill);
                Button btnCancel = dialog.findViewById(R.id.btnDialogAddBillCancel);

                tvngaytao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar calendar = Calendar.getInstance();
                        int ngay = calendar.get(Calendar.DATE);
                        int thang = calendar.get(Calendar.MONTH);
                        int nam = calendar.get(Calendar.YEAR);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                calendar.set(i, i1, i2);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                tvngaytao.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, nam, thang, ngay);
                        datePickerDialog.show();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"ĐÃ HỦY",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnAddBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database = FirebaseDatabase.getInstance();
                        myRef = database.getReference("Bill");


                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String mahoadon = edtmahoadon.getText().toString();
                                String ngaytao = tvngaytao.getText().toString();
                                if (dataSnapshot.child(mahoadon).child("mahoadon").exists()){
                                    Toast.makeText(getContext(),"MÃ HÓA ĐƠN ĐÃ TỒN TẠI",Toast.LENGTH_SHORT).show();
                                }else {
                                    try {
                                        myRef.child(mahoadon).child("mahoadon").setValue(mahoadon);
                                        myRef.child(mahoadon).child("ngaytao").setValue(ngaytao);
                                        Toast.makeText(getContext(),"TẠO THÀNH CÔNG",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }catch (Exception ex){
                                        Toast.makeText(getContext(),"ERROR "+ex.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });





                dialog.show();
            }
        });
    }

    public static void capnhatLV() {
        adapter.notifyDataSetChanged();
    }
}
