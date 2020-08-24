package com.example.quanlysach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysach.Adapter.SachAdapter;
import com.example.quanlysach.Model.Sach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditBook extends AppCompatActivity {
    ImageView imvHinhEdit, imveditchoose, imveditchup;
    TextView edteditmasach;
    EditText edtedittensach, edteditgia, edteditsoluong,edteditbaove;
    Spinner speditloai;
    Button btnEdit, btnCancel;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String tenloai;
    Bitmap selectedBitmap;
    SachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        AnhXa();


        Intent intent = getIntent();
        final String key = intent.getStringExtra("KEY");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Sach");


        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    final Sach sach = dataSnapshot.getValue(Sach.class);
                    sach.setMaSach(dataSnapshot.getKey());
                    edteditmasach.setText(sach.getMaSach());
                    edtedittensach.setText(sach.getTenSach());
                    edteditgia.setText(sach.getGia());
                    edteditsoluong.setText(sach.getSoluong());
                    edteditbaove.setText(sach.getBaove());



                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("LoaiSach");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<String> list = new ArrayList<String>();
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                String tenloai = dataSnapshot1.child("tenloai").getValue(String.class);
                                list.add(tenloai);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditBook.this, android.R.layout.simple_list_item_1, list);
                            speditloai.setAdapter(arrayAdapter);

                            for (int i = 0; i < speditloai.getCount(); i++) {
                                if (speditloai.getItemAtPosition(i).toString().equals(sach.getLoaiSach())) {
                                    speditloai.setSelection(i);
                                }
                            }

                            speditloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


                    if (sach.getImg() != null) {
                        byte[] decodedString = Base64.decode(sach.getImg(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imvHinhEdit.setImageBitmap(decodedByte);
                    }


                } catch (Exception ex) {
                    Log.e("LOI_JSON", ex.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        HinhAnh();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void AnhXa() {
        imvHinhEdit = findViewById(R.id.imvEDITHinh);
        imveditchoose = findViewById(R.id.imveditchoose);
        imveditchup = findViewById(R.id.imveditchup);
        edteditmasach = findViewById(R.id.edteditmasach);
        speditloai = findViewById(R.id.speditloaisach);
        edtedittensach = findViewById(R.id.edtedittensach);
        edteditgia = findViewById(R.id.edteditgiasach);
        edteditsoluong = findViewById(R.id.edteditsoluongsach);
        btnEdit = findViewById(R.id.btnEditSach);
        btnCancel = findViewById(R.id.btnHuyEditSach);
        edteditbaove = findViewById(R.id.edteditbaove);
    }


    public void HinhAnh() {
        imveditchup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuphinh();
            }
        });

        imveditchoose.setOnClickListener(new View.OnClickListener() {
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
            imvHinhEdit.setImageBitmap(selectedBitmap);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            try {
                //xử lý lấy ảnh chọn từ điện thoại:
                Uri imageUri = data.getData();
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imvHinhEdit.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void Edit(View view) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Sach");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String masach = edteditmasach.getText().toString();
                String tensach = edtedittensach.getText().toString();
                String loaisach = tenloai;
                String giasach = edteditgia.getText().toString();
                String soluong = edteditsoluong.getText().toString();
                String baove = edteditbaove.getText().toString();

                try {

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


                    Toast.makeText(EditBook.this, "EDIT THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception ex) {
                    Toast.makeText(EditBook.this, "Error:" + ex.toString(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                finish();
            }
        });


    }
}
