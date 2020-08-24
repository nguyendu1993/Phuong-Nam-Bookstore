package com.example.quanlysach.Dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quanlysach.Fragment.Category_Fragment;
import com.example.quanlysach.Fragment.UserFragment;
import com.example.quanlysach.Model.LoaiSach;
import com.example.quanlysach.Model.NonUI;
import com.example.quanlysach.Model.User;
import com.example.quanlysach.Register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDao {

    private DatabaseReference mDatabase;

    Context context;
    NonUI nonUI;

    String loaisachid;

    public LoaiSachDao(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("LoaiSach");
        this.context = context;
        this.nonUI = new NonUI(context);

    }



    public List<LoaiSach> getAll() {

        final List<LoaiSach> list = new ArrayList<LoaiSach>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    LoaiSach s = data.getValue(LoaiSach.class);
                    list.add(s);
                }

                Category_Fragment.capnhatLV();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }



        public void update(final LoaiSach nd) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maloai").getValue(String.class).equalsIgnoreCase(nd.getMaloai())){
                        loaisachid = data.getKey();
                        Log.d("getKey", "onCreate: key :" + loaisachid);


                        mDatabase.child(loaisachid).setValue(nd)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Chỉnh sửa thành công");
                                        Log.d("update","update Thanh cong");


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("update That bai");
                                        Log.d("update","update That bai");
                                    }
                                });

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    public void delete(final LoaiSach nd) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maloai").getValue(String.class).equalsIgnoreCase(nd.getMaloai())){
                        loaisachid = data.getKey();
                        Log.d("getKey", "onCreate: key :" + loaisachid);


                        mDatabase.child(loaisachid).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Xóa thành công");
                                        Log.d("delete","delete Thanh cong");


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("delete That bai");
                                        Log.d("delete","delete That bai");
                                    }
                                });

                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }





}
