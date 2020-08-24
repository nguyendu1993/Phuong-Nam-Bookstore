package com.example.quanlysach.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysach.Adapter.SachAdapter;
import com.example.quanlysach.Dao.SachDao;
import com.example.quanlysach.Model.Sach;
import com.example.quanlysach.R;
import com.example.quanlysach.ThemSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class BookFragment extends Fragment {
    FloatingActionButton btnAddSach;
    ListView lvSach;
    public static SachAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    SachDao dao;
    ArrayList<Sach> saches;


    Bitmap selectedBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_fragment, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvSach = (ListView) view.findViewById(R.id.lvSach);
        btnAddSach = (FloatingActionButton) view.findViewById(R.id.btnAddSach);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Sach");


        dao = new SachDao(getContext());
        saches = (ArrayList<Sach>) dao.getAll();
        adapter = new SachAdapter(getContext(), saches);
        lvSach.setAdapter(adapter);




        btnAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThemSach.class);
                startActivity(intent);
            }

        });
    }


    public static void capnhatLV() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        capnhatLV();
    }
}
