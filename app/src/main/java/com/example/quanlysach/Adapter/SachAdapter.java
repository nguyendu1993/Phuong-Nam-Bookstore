package com.example.quanlysach.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.quanlysach.Dao.LoaiSachDao;
import com.example.quanlysach.Dao.SachDao;
import com.example.quanlysach.EditBook;
import com.example.quanlysach.Model.LoaiSach;
import com.example.quanlysach.Model.Sach;
import com.example.quanlysach.R;

import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Sach> saches;
    ImageView imbPicture;
    TextView tvmasach, tvtensach, tvgia,tvbaove;
    SachDao dao;

    public SachAdapter(Context context, ArrayList<Sach> saches) {
        super(context, 0, saches);
        this.context = context;
        this.saches = saches;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
        }

        final Sach sach = saches.get(position);

        if (sach != null) {
            imbPicture = (ImageView) v.findViewById(R.id.imvPicture);
            tvmasach = (TextView) v.findViewById(R.id.tvMaSach);
            tvtensach = (TextView) v.findViewById(R.id.tvTenSach);
            tvgia = (TextView) v.findViewById(R.id.tvGiaSach);
            tvbaove = (TextView)v.findViewById(R.id.tvbaove);

            tvmasach.setText(sach.getMaSach());
            tvtensach.setText(sach.getTenSach());
            tvgia.setText("GIÁ: " + sach.getGia() + " VND");
            tvbaove.setText(sach.getBaove());

            if (sach.getImg() != null) {
                byte[] decodedString = Base64.decode(sach.getImg(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imbPicture.setImageBitmap(decodedByte);
            }


        }


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sach s = saches.get(position);
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_view_item);
                final ImageView imvDetailIMG = dialog.findViewById(R.id.imvshowbook);
                final TextView tvdetailma = dialog.findViewById(R.id.tvdetailmasach);
                final TextView tvdetailten = dialog.findViewById(R.id.tvdetailtensach);
                final TextView tvdetailloai = dialog.findViewById(R.id.tvdetailloaisach);
                final TextView tvdetailgia = dialog.findViewById(R.id.tvdetailgiasach);
                final TextView tvdetailsl = dialog.findViewById(R.id.tvdetailslsach);
                final Button btnEdit = dialog.findViewById(R.id.btnEditSach);
                final Button btnDelete = dialog.findViewById(R.id.btnDeleteSach);

                //Đổ dữ liệu

                if (s.getImg() != null) {
                    byte[] decodedString = Base64.decode(sach.getImg(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imvDetailIMG.setImageBitmap(decodedByte);
                }

                tvdetailma.setText("MÃ SÁCH: " + s.getMaSach());
                tvdetailten.setText("TÊN SÁCH: " + s.getTenSach());
                tvdetailloai.setText("LOAI: "+s.getLoaiSach());
                tvdetailgia.setText("GIÁ: " + s.getGia() + " VND");
                tvdetailsl.setText("SỐ LƯỢNG: " + s.getSoluong());


                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Sach sl = saches.get(position);
                        Intent intent = new Intent(getContext(), EditBook.class);
                        intent.putExtra("KEY", sl.getMaSach());
                        context.startActivity(intent);
                    }
                });


                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        CharSequence[] item = {"NO", "YES"};
                        final AlertDialog.Builder dialog3 = new AlertDialog.Builder(getContext());
                        dialog3.setTitle("BẠN CÓ CHẮC MUỐN XÓA LOẠI SÁCH NÀY KHÔNG ?");
                        dialog3.setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    Toast.makeText(getContext(), "ĐÃ HỦY", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                                if (i == 1) {
                                    Sach nd = saches.get(position);
                                    dao = new SachDao(getContext());
                                    dao.delete(nd);
                                    dialog.dismiss();
                                }
                            }
                        });

                        dialog3.show();
                    }

                });

                dialog.show();
            }
        });


        return v;
    }
}
