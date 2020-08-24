package com.example.quanlysach.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlysach.Adapter.UserAdapter;
import com.example.quanlysach.Dao.UserDao;
import com.example.quanlysach.Model.User;
import com.example.quanlysach.R;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    public static UserAdapter adapter;
    ListView lvUser;
    UserDao dao;
    ArrayList<User> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvUser = view.findViewById(R.id.lvUser);


        dao = new UserDao(getContext());
        users = (ArrayList<User>) dao.getAll();
        adapter = new UserAdapter(getContext(), users);
        lvUser.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        capnhatLV();
    }

    public static void capnhatLV() {
        adapter.notifyDataSetChanged();
    }
}
