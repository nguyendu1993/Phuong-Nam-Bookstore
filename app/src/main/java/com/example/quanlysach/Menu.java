package com.example.quanlysach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlysach.Fragment.BillFragment;
import com.example.quanlysach.Fragment.BookFragment;
import com.example.quanlysach.Fragment.Category_Fragment;
import com.example.quanlysach.Fragment.ThongKeFragment;
import com.example.quanlysach.Fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {

    BottomNavigationView bvn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserFragment()).commit();

        bvn =findViewById(R.id.bottom_navigation);

        bvn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.nav_user){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserFragment()).commit();

                }
                if (menuItem.getItemId()==R.id.nav_book){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BookFragment()).commit();

                }
                if (menuItem.getItemId()==R.id.nav_category){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Category_Fragment()).commit();

                }
                if (menuItem.getItemId()==R.id.nav_bill){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BillFragment()).commit();

                }
                if (menuItem.getItemId()==R.id.nav_thongke){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ThongKeFragment()).commit();

                }



                return true;
            }
        });



    }
}
