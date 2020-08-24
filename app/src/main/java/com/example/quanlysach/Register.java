package com.example.quanlysach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlysach.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText edtReUsername, edtRePass, edtReEnterpass;
    Button btnRegis, btnReCancel;

    DatabaseReference data;
    FirebaseDatabase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();

        ref = FirebaseDatabase.getInstance();
        data = ref.getReference("User");


        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edtReUsername.getText().toString();
                String pass = edtRePass.getText().toString();
                final User user = new User(username, pass);
                data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUsername()).exists()) {
                            Toast.makeText(Register.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else if (!edtRePass.getText().toString().equals(edtReEnterpass.getText().toString())) {
                            Toast.makeText(Register.this, "Nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {

                            data.child(user.getUsername()).setValue(user);
                            Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            finish();


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        btnReCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Register.this, "Đã hủy", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    public void AnhXa() {
        edtReUsername = findViewById(R.id.edtResUsername);
        edtRePass = findViewById(R.id.edtResPassword);
        edtReEnterpass = findViewById(R.id.edtReEnterPass);
        btnRegis = findViewById(R.id.btnRegis);
        btnReCancel = findViewById(R.id.btnReCancel);
    }
}
