package com.example.quanlysach.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlysach.Adapter.LoaiSachAdapter;
import com.example.quanlysach.Dao.LoaiSachDao;
import com.example.quanlysach.Model.LoaiSach;
import com.example.quanlysach.R;
import com.example.quanlysach.Register;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Category_Fragment extends Fragment {

    public static LoaiSachAdapter adapter;

    ListView lvloaisach;
    FloatingActionButton btnAdd;
    EditText edtsearch;
    LoaiSachDao dao;
    ArrayList<LoaiSach> loaiSaches;


    ArrayAdapter<String> arrayAdapter;

    DatabaseReference data;
    FirebaseDatabase ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ref = FirebaseDatabase.getInstance();
        data = ref.getReference("LoaiSach");

        lvloaisach = view.findViewById(R.id.lvCategory);
        btnAdd = view.findViewById(R.id.btnAdd);
        edtsearch = view.findViewById(R.id.edtfindloai);





        dao = new LoaiSachDao(getContext());
        loaiSaches = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(getContext(), loaiSaches);
        lvloaisach.setAdapter(adapter);









        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Category_Fragment.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.addloaisach_dialog);
                final EditText edtmaloaisach = dialog.findViewById(R.id.edtaddmaloai);
                final EditText edttenloaisach = dialog.findViewById(R.id.edtaddtenloai);

                final Button btnadd = dialog.findViewById(R.id.btnaddloai);
                final Button btncancel = dialog.findViewById(R.id.btncancelloai);


                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maloai = edtmaloaisach.getText().toString();
                        String tenloai = edttenloaisach.getText().toString();

                        final LoaiSach ls = new LoaiSach(maloai, tenloai);
                        data.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(ls.getMaloai()).exists()) {
                                    Toast.makeText(getContext(), "Mã Loại Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                                } else {

                                    data.child(ls.getMaloai()).setValue(ls);
                                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });



                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "ĐÃ HỦY", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

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
