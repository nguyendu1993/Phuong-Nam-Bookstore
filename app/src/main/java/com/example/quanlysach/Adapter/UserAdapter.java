package com.example.quanlysach.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.quanlysach.Dao.UserDao;
import com.example.quanlysach.Model.User;
import com.example.quanlysach.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter {
    TextView tvUser, tvPass;
    Context context;
    ArrayList<User> users;
    UserDao dao;

    public UserAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        }
        final User user = users.get(position);
        if (user != null) {
            tvUser = v.findViewById(R.id.tvUser);
            tvPass = v.findViewById(R.id.tvPass);

            tvUser.setText(user.getUsername());
            tvPass.setText(user.getPassword());

        }


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] item = {"UPDATE", "DELETE"};

                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("MỜI CHỌN TÁC VỤ");
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            final Dialog dialog1 = new Dialog(getContext());
                            dialog1.setContentView(R.layout.edit_user_item);
                            final TextView tvtenus = dialog1.findViewById(R.id.tvedituser);
                            final EditText edtEditpass = dialog1.findViewById(R.id.edtEditPass);
                            final Button btnupdate = dialog1.findViewById(R.id.btnUpdate);
                            final Button btncancel = dialog1.findViewById(R.id.btnCancel);

                            User u = users.get(position);

                            tvtenus.setText(u.getUsername());
                            edtEditpass.setText(u.getPassword());


                            btnupdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String ten = tvtenus.getText().toString();
                                    String p = edtEditpass.getText().toString();

                                    User us = new User(ten,p);
                                    dao = new UserDao(getContext());
                                    dao.update(us);
                                    dialog1.dismiss();

                                }
                            });


                            btncancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(),"ĐÃ HỦY",Toast.LENGTH_LONG).show();
                                }
                            });


                            dialog1.show();
                        }

                        if (i==1){
                            CharSequence[] item = {"KHÔNG", "CÓ"};

                            final AlertDialog.Builder dialog3 = new AlertDialog.Builder(getContext());
                            dialog3.setTitle("BẠN CÓ CHẮC MUỐN XÓA TÀI KHOẢN NÀY KHÔNG ?");
                            dialog3.setItems(item, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i==0){
                                        Toast.makeText(getContext(),"ĐÃ HỦY",Toast.LENGTH_LONG).show();
                                    }
                                    if (i==1){
                                        User nd = users.get(position);
                                        dao = new UserDao(getContext());
                                        dao.delete(nd);
                                    }
                                }
                            });

                            dialog3.show();
                        }

                    }
                });
                dialog.show();

            }
        });

        return v;


    }
}
