package com.example.quanlysach.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.quanlysach.Dao.HDCTDao;
import com.example.quanlysach.EditHDCT;
import com.example.quanlysach.Model.BillModel;
import com.example.quanlysach.Model.HDCTModel;
import com.example.quanlysach.R;

import java.util.ArrayList;

public class HDDetailAdapter extends ArrayAdapter {
    TextView tvmahd, tvngay, tvtensp, tvslsp, tvgia, tvtongtien;

    Context context;
    ArrayList<HDCTModel> hdctModels;
    HDCTDao dao;

    public HDDetailAdapter(@NonNull Context context, ArrayList<HDCTModel> hdctModels) {
        super(context, 0, hdctModels);
        this.context = context;
        this.hdctModels = hdctModels;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_hdct, parent, false);
        }

        final HDCTModel hdct = hdctModels.get(position);
        if (hdct != null) {
            tvmahd = v.findViewById(R.id.tvitemmahdct);
            tvngay = v.findViewById(R.id.tvitemngayhdct);
            tvtensp = v.findViewById(R.id.tvitemtensachhdct);
            tvslsp = v.findViewById(R.id.tvitemslsphdct);
            tvgia = v.findViewById(R.id.tvitemgiasphdct);
            tvtongtien = v.findViewById(R.id.tvitemtongtienhdct);


            tvmahd.setText(hdct.getMahdct());
            tvngay.setText(hdct.getNgay());
            tvtensp.setText(hdct.getSanpham());
            tvslsp.setText(hdct.getSlsanpham());
            tvgia.setText(hdct.getGiasp());
            tvtongtien.setText(hdct.getTongtien());
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
                            HDCTModel hd = hdctModels.get(position);
                            Intent intent = new Intent(getContext(),EditHDCT.class);
                            intent.putExtra("HDCT",hd.getMahdct());
                            context.startActivity(intent);
                        }

                        if (i == 1) {
                            CharSequence[] item2 = {"CÓ", "KHÔNG"};
                            final AlertDialog.Builder dialog2 = new AlertDialog.Builder(getContext());
                            dialog2.setTitle("BẠN CÓ CHẮC MUỐN XÓA HÓA ĐƠN NÀY KHÔNG ?");
                            dialog2.setItems(item2, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        dao = new HDCTDao(getContext());
                                        HDCTModel hdct = hdctModels.get(position);
                                        dao.delete(hdct);
                                    }
                                    if (i == 1) {
                                        Toast.makeText(getContext(), "ĐÃ HỦY", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            dialog2.show();
                        }

                    }
                });

                dialog.show();
            }
        });


        return v;
    }
}
