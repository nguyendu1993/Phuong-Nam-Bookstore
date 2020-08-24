package com.example.quanlysach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysach.Model.HDCTModel;
import com.example.quanlysach.Model.Sach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditHDCT extends AppCompatActivity {
    TextView tvmahdct, tvmahd, tvngay, tvgia, tvslsp, tvtongtien;
    Spinner spsanpham;
    ImageButton btncong, btntru;
    Button btnEdit, btnCancel;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String tensach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hdct);
        AnhXa();

        Intent intent = getIntent();
        final String key = intent.getStringExtra("HDCT");

        final int sli = 1;
        tvslsp.setText("" + sli);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("HDCT");

        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    final HDCTModel hd = dataSnapshot.getValue(HDCTModel.class);
                    hd.setMahdct(dataSnapshot.getKey());
                    tvmahdct.setText(hd.getMahdct());
                    tvmahd.setText(hd.getMahd());
                    tvngay.setText(hd.getNgay());
//                    tvgia.setText(hd.getGiasp());
                    tvslsp.setText(hd.getSlsanpham());
//                    tvtongtien.setText(hd.getTongtien());


                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Sach");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final List<String> listsp = new ArrayList<>();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                String tensach = data.child("tenSach").getValue(String.class);
                                listsp.add(tensach);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditHDCT.this, android.R.layout.simple_list_item_1, listsp);
                            spsanpham.setAdapter(arrayAdapter);

                            for (int i = 0; i < spsanpham.getCount(); i++) {
                                if (spsanpham.getItemAtPosition(i).toString().equals(hd.getSanpham())) {
                                    spsanpham.setSelection(i);
                                }
                            }

                            spsanpham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    tensach = adapterView.getItemAtPosition(i).toString();
                                    final String tens = spsanpham.getSelectedItem().toString();

                                    database = FirebaseDatabase.getInstance();
                                    myRef = database.getReference("Sach");

                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            List<Sach> list = new ArrayList<>();

                                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                Sach ss = dataSnapshot1.getValue(Sach.class);
                                                list.add(ss);

                                                for (Sach sc : list){
                                                    if ((ss.getTenSach()).equals(tens)){
                                                        tvgia.setText(sc.getGia());
                                                    }
                                                }

                                            }

                                            int sl = Integer.parseInt(tvslsp.getText().toString());
                                            int gia = Integer.parseInt(tvgia.getText().toString());
                                            int tt = sl*gia;
                                            tvtongtien.setText(""+tt);

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });






                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                } catch (Exception ex) {
                    Log.e("LOI_JSON", ex.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tru = Integer.parseInt(tvslsp.getText().toString());
                if (tru > 1) {
                    int t = tru - 1;
                    tvslsp.setText("" + t);
                    int gia = Integer.parseInt(tvgia.getText().toString());
                    int tongtien = t * gia;
                    tvtongtien.setText("" + tongtien);
                } else {
                    Toast.makeText(EditHDCT.this, "Số Lượng phải lớn hơn 1", Toast.LENGTH_SHORT).show();
                    tvslsp.setText("" + 1);
                }
            }
        });


        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cong = Integer.parseInt(tvslsp.getText().toString());
                int p = cong + 1;
                tvslsp.setText("" + p);

                int gia = Integer.parseInt(tvgia.getText().toString());
                int tongtien = p * gia;
                tvtongtien.setText("" + tongtien);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditHDCT.this,"ĐÃ HỦY",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public void AnhXa() {
        tvmahdct = findViewById(R.id.tveditmahdct);
        tvmahd = findViewById(R.id.tveditmahoadonhdct);
        tvngay = findViewById(R.id.tveditngayhdct);
        tvgia = findViewById(R.id.tveditgiasphdct);
        tvslsp = findViewById(R.id.tveditslsphdct);
        tvtongtien = findViewById(R.id.tvedittongtien);
        spsanpham = findViewById(R.id.speditsphdct);
        btncong = findViewById(R.id.btneditcong);
        btntru = findViewById(R.id.btnedittru);
        btnEdit = findViewById(R.id.btnEditHDCT);
        btnCancel = findViewById(R.id.btnCanCelEditHDCT);

    }


    public void XuLyEdit(View view){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("HDCT");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String mahdct = tvmahdct.getText().toString();
                String mahd = tvmahd.getText().toString();
                String ngay = tvngay.getText().toString();
                String tensp = tensach;
                String gia = tvgia.getText().toString();
                String slsp = tvslsp.getText().toString();
                String tongtien = tvtongtien.getText().toString();
                try {


                    myRef.child(mahdct).child("mahd").setValue(mahd);
                    myRef.child(mahdct).child("ngay").setValue(ngay);
                    myRef.child(mahdct).child("sanpham").setValue(tensp);
                    myRef.child(mahdct).child("giasp").setValue(gia);
                    myRef.child(mahdct).child("slsanpham").setValue(slsp);
                    myRef.child(mahdct).child("tongtien").setValue(tongtien);

                    Toast.makeText(EditHDCT.this,"EDIT THÀNH CÔNG",Toast.LENGTH_SHORT).show();
                    finish();


                }catch (Exception ex){
                    Toast.makeText(EditHDCT.this, "Error:" + ex.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    public void getdataspinner() {
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("Sach");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final List<String> listsp = new ArrayList<>();
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    String tensach = data.child("tenSach").getValue(String.class);
//                    listsp.add(tensach);
//                }
//
//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditHDCT.this, android.R.layout.simple_list_item_1, listsp);
//                spsanpham.setAdapter(arrayAdapter);
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
