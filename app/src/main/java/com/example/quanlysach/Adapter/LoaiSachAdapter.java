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

import com.example.quanlysach.Dao.LoaiSachDao;
import com.example.quanlysach.Dao.UserDao;
import com.example.quanlysach.Model.LoaiSach;
import com.example.quanlysach.Model.User;
import com.example.quanlysach.R;

import java.util.ArrayList;

public class LoaiSachAdapter extends ArrayAdapter {
    TextView tvmaloai, tvtenloai;
    Context context;
    ArrayList<LoaiSach> loaiSaches;
    LoaiSachDao dao;

    public LoaiSachAdapter(@NonNull Context context, ArrayList<LoaiSach> loaiSaches) {
        super(context, 0, loaiSaches);
        this.context = context;
        this.loaiSaches = loaiSaches;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }
        final LoaiSach ls = loaiSaches.get(position);
        if (ls != null) {
            tvmaloai = v.findViewById(R.id.tvmaloai);
            tvtenloai = v.findViewById(R.id.tvtenloai);

            tvmaloai.setText(ls.getMaloai());
            tvtenloai.setText(ls.getTenloai());

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
                            dialog1.setContentView(R.layout.edit_loaisach_item);
                            final TextView tvmaloai = dialog1.findViewById(R.id.tveditmaloai);
                            final EditText edtedittenloai = dialog1.findViewById(R.id.edtedittenloai);


                            final Button btnupdate = dialog1.findViewById(R.id.btnUpdateloai);
                            final Button btncancel = dialog1.findViewById(R.id.btnCancelloai);

                            LoaiSach u = loaiSaches.get(position);

                            tvmaloai.setText(u.getMaloai());
                            edtedittenloai.setText(u.getTenloai());


                            btnupdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String maloai = tvmaloai.getText().toString();
                                    String tenloai = edtedittenloai.getText().toString();

                                    LoaiSach us = new LoaiSach(maloai,tenloai);
                                    dao = new LoaiSachDao(getContext());
                                    dao.update(us);
                                    dialog1.dismiss();

                                }
                            });

                            btncancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(),"ĐÃ HỦY",Toast.LENGTH_LONG).show();
                                    dialog1.dismiss();
                                }
                            });


                            dialog1.show();
                        }

                        if (i==1){
                            CharSequence[] item = {"KHÔNG", "CÓ"};

                            final AlertDialog.Builder dialog3 = new AlertDialog.Builder(getContext());
                            dialog3.setTitle("BẠN CÓ CHẮC MUỐN XÓA LOẠI SÁCH NÀY KHÔNG ?");
                            dialog3.setItems(item, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i==0){
                                        Toast.makeText(getContext(),"ĐÃ HỦY",Toast.LENGTH_LONG).show();
                                    }
                                    if (i==1){
                                        LoaiSach nd = loaiSaches.get(position);
                                        dao = new LoaiSachDao(getContext());
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
