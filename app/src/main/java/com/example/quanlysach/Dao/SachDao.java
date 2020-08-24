package com.example.quanlysach.Dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quanlysach.Fragment.BookFragment;
import com.example.quanlysach.Fragment.Category_Fragment;
import com.example.quanlysach.Model.LoaiSach;
import com.example.quanlysach.Model.NonUI;
import com.example.quanlysach.Model.Sach;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private DatabaseReference mDatabase;

    Context context;
    NonUI nonUI;

    String loaisachid;

    public SachDao(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Sach");
        this.context = context;
        this.nonUI = new NonUI(context);

    }
    public List<Sach> getAll() {

        final List<Sach> list = new ArrayList<Sach>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Sach s = data.getValue(Sach.class);
                    list.add(s);
                }

                BookFragment.capnhatLV();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }


    public void delete(final Sach nd) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maSach").getValue(String.class).equalsIgnoreCase(nd.getMaSach())){
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
