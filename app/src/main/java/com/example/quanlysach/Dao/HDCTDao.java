package com.example.quanlysach.Dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quanlysach.Fragment.Bill;
import com.example.quanlysach.Fragment.DetailBill;
import com.example.quanlysach.Model.BillModel;
import com.example.quanlysach.Model.HDCTModel;
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

public class HDCTDao {

    private DatabaseReference mDatabase;

    Context context;
    NonUI nonUI;

    String loaisachid;

    public HDCTDao(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("HDCT");
        this.context = context;
        this.nonUI = new NonUI(context);

    }
    public List<HDCTModel> getAll() {

        final List<HDCTModel> list = new ArrayList<HDCTModel>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HDCTModel s = data.getValue(HDCTModel.class);
                    list.add(s);
                }

                DetailBill.capnhatLV();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }




    public void delete(final HDCTModel nd) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("mahdct").getValue(String.class).equalsIgnoreCase(nd.getMahdct())){
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
