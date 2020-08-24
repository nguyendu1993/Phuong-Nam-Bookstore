package com.example.quanlysach.Dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quanlysach.Fragment.UserFragment;
import com.example.quanlysach.Model.NonUI;
import com.example.quanlysach.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private DatabaseReference mDatabase;

    Context context;
    NonUI nonUI;

    String userid;

    public UserDao(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("User");
        this.context = context;
        this.nonUI = new NonUI(context);

    }

    public List<User> getAll() {

        final List<User> list = new ArrayList<User>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User s = data.getValue(User.class);
                    list.add(s);
                }

                UserFragment.capnhatLV();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }


    public void update(final User nd) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("username").getValue(String.class).equalsIgnoreCase(nd.getUsername())){
                        userid = data.getKey();
                        Log.d("getKey", "onCreate: key :" + userid);


                        mDatabase.child(userid).setValue(nd)
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




    public void delete(final User nd) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("username").getValue(String.class).equalsIgnoreCase(nd.getUsername())){
                        userid = data.getKey();
                        Log.d("getKey", "onCreate: key :" + userid);


                        mDatabase.child(userid).setValue(null)
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
