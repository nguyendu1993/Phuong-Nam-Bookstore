package com.example.quanlysach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlysach.Model.LoaiSach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThemSach extends AppCompatActivity {
    ImageView imvHinh, imvchuphinh, imvchonhinh;
    EditText edtmasach, edttensach, edtgiasach, edtsoluongsach,edtBaoVe;
    Spinner sploaisach;
    Button btnThemSach, btnHuyThem;
    Bitmap selectedBitmap;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String tenloai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("LoaiSach");


        AnhXa();
        HinhAnh();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<String>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String tenloai = dataSnapshot1.child("tenloai").getValue(String.class);
                    list.add(tenloai);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ThemSach.this, android.R.layout.simple_list_item_1, list);
                sploaisach.setAdapter(arrayAdapter);

                sploaisach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        tenloai = adapterView.getItemAtPosition(i).toString();
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


        btnHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ThemSach.this, "ĐÃ HỦY", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public void AnhXa() {
        imvHinh = findViewById(R.id.imvHinh);
        imvchonhinh = findViewById(R.id.imvchoose);
        imvchuphinh = findViewById(R.id.imvchup);
        edtmasach = findViewById(R.id.edtmasach);
        edttensach = findViewById(R.id.edttensach);
        edtgiasach = findViewById(R.id.edtgiasach);
        edtsoluongsach = findViewById(R.id.edtsoluongsach);
        sploaisach = findViewById(R.id.sploaisach);
        btnThemSach = findViewById(R.id.btnThemSach);
        btnHuyThem = findViewById(R.id.btnHuyThemSach);
        edtBaoVe = findViewById(R.id.edtbaove);

    }


    public void HinhAnh() {
        imvchuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuphinh();
            }
        });

        imvchonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonhinh();
            }
        });

    }

    private void chonhinh() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 200);
    }

    private void chuphinh() {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //xử lý lấy ảnh trực tiếp lúc chụp hình:
            selectedBitmap = (Bitmap) data.getExtras().get("data");
            imvHinh.setImageBitmap(selectedBitmap);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            try {
                //xử lý lấy ảnh chọn từ điện thoại:
                Uri imageUri = data.getData();
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imvHinh.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ThemSach(View view) {

        myRef = database.getReference("Sach");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String masach = edtmasach.getText().toString();
                String tensach = edttensach.getText().toString();
                String loaisach = tenloai;
                String giasach = edtgiasach.getText().toString();
                String soluong = edtsoluongsach.getText().toString();
                String baove = edtBaoVe.getText().toString();

                if (dataSnapshot.child(masach).exists()) {
                    Toast.makeText(ThemSach.this, "MÃ SÁCH ĐÃ TỒN TẠI", Toast.LENGTH_SHORT).show();
                }else {
                    try {

                        myRef.child(masach).child("maSach").setValue(masach);
                        myRef.child(masach).child("tenSach").setValue(tensach);
                        myRef.child(masach).child("loaiSach").setValue(loaisach);
                        myRef.child(masach).child("gia").setValue(giasach);
                        myRef.child(masach).child("soluong").setValue(soluong);
                        myRef.child(masach).child("baove").setValue(baove);

                        //đưa bitmap về base64string:
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String imgeEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        myRef.child(masach).child("img").setValue(imgeEncoded);

                        finish();
                        Toast.makeText(ThemSach.this, "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(ThemSach.this, "Error:" + ex.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
