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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysach.Fragment.DetailBill;
import com.example.quanlysach.Model.BillModel;
import com.example.quanlysach.Model.Sach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThemDetailHD extends AppCompatActivity {

    TextView tvmahdct, tvmahoadon, tvngay, tvgiasp, tvtongtien, edtslsp;
    ImageButton btncong, btntru;
    Button btnThemHDCT, btnHuyThem;
    Spinner spsanpham;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference RefSach;
    //    String tensach;
    String tensach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_detail_hd);
        AnhXa();
//        tensach = "Du an Mau";

        Intent intent = getIntent();
        final String key = intent.getStringExtra("BILL");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bill");
        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    final BillModel bill = dataSnapshot.getValue(BillModel.class);
                    bill.setMahoadon(dataSnapshot.getKey());
                    tvmahdct.setText(bill.getMahoadon());
                    tvmahoadon.setText(bill.getMahoadon());
                    tvngay.setText(bill.getNgaytao());
                } catch (Exception ex) {
                    Toast.makeText(ThemDetailHD.this, "ERROR " + ex.toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getdataspinner();
        final int sli = 1;
        edtslsp.setText("" + sli);


        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cong = Integer.parseInt(edtslsp.getText().toString());
                int p = cong + 1;
                edtslsp.setText("" + p);

                int gia = Integer.parseInt(tvgiasp.getText().toString());
                int tongtien = p * gia;
                tvtongtien.setText("" + tongtien);

            }
        });


        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tru = Integer.parseInt(edtslsp.getText().toString());
                if (tru > 1) {
                    int t = tru - 1;
                    edtslsp.setText("" + t);
                    int gia = Integer.parseInt(tvgiasp.getText().toString());
                    int tongtien = t * gia;
                    tvtongtien.setText("" + tongtien);
                } else {
                    Toast.makeText(ThemDetailHD.this, "Số Lượng phải lớn hơn 1", Toast.LENGTH_SHORT).show();
                    edtslsp.setText("" + 1);
                }
            }
        });


        btnHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ThemDetailHD.this, "ĐÃ HỦY", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public void AnhXa() {

        tvmahdct = findViewById(R.id.tvmahdct);
        tvmahoadon = findViewById(R.id.tvmahoadonct);
        tvngay = findViewById(R.id.tvngayhoadonct);
        tvgiasp = findViewById(R.id.tvgiaspdetal);
        tvtongtien = findViewById(R.id.tvtongtien);
        edtslsp = findViewById(R.id.edtslsp);
        spsanpham = findViewById(R.id.spdetailsp);
        btncong = findViewById(R.id.btntru);
        btntru = findViewById(R.id.btnplus);
        btnThemHDCT = findViewById(R.id.btnThemHDCT);
        btnHuyThem = findViewById(R.id.btnhuythemhdct);


    }


    public void getdataspinner() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Sach");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> list = new ArrayList<String>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String tenloai = dataSnapshot1.child("tenSach").getValue(String.class);
                    list.add(tenloai);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ThemDetailHD.this, android.R.layout.simple_list_item_1, list);
                spsanpham.setAdapter(arrayAdapter);

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
                                List<Sach> listsach = new ArrayList<Sach>();
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    Sach ss = data.getValue(Sach.class);
                                    listsach.add(ss);

                                    for (Sach sc : listsach) {

                                        if ((ss.getTenSach().equals(tens))) {
                                            tvgiasp.setText(sc.getGia());
                                        }
                                    }

                                }

                                int g = Integer.parseInt(tvgiasp.getText().toString());
                                int sls = Integer.parseInt(edtslsp.getText().toString());
                                int tt = g * sls;
                                tvtongtien.setText("" + tt);
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

    }

    public void XuLyThem(View view) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("HDCT");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String mahdct = tvmahdct.getText().toString();
                String mahd = tvmahoadon.getText().toString();
                String ngay = tvngay.getText().toString();
                String tensp = tensach;
                String giasp = tvgiasp.getText().toString();
                String sl = edtslsp.getText().toString();
                String tongtien = tvtongtien.getText().toString();
                if (dataSnapshot.child(mahdct).exists()) {
                    Toast.makeText(ThemDetailHD.this,"HÓA ĐƠN ĐÃ TỒN TẠI",Toast.LENGTH_SHORT).show();
                } else {
                    try {

                        myRef.child(mahdct).child("mahdct").setValue(mahdct);
                        myRef.child(mahdct).child("mahd").setValue(mahd);
                        myRef.child(mahdct).child("ngay").setValue(ngay);
                        myRef.child(mahdct).child("sanpham").setValue(tensp);
                        myRef.child(mahdct).child("giasp").setValue(giasp);
                        myRef.child(mahdct).child("slsanpham").setValue(sl);
                        myRef.child(mahdct).child("tongtien").setValue(tongtien);

                        Toast.makeText(ThemDetailHD.this, "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        finish();

                    } catch (Exception ex) {
                        Toast.makeText(ThemDetailHD.this, "Error:" + ex.toString(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}



