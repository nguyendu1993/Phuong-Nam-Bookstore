package com.example.quanlysach.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.quanlysach.Dao.BillDao;
import com.example.quanlysach.Dao.LoaiSachDao;
import com.example.quanlysach.Model.BillModel;
import com.example.quanlysach.Model.LoaiSach;
import com.example.quanlysach.R;
import com.example.quanlysach.ThemDetailHD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BillAdapter extends ArrayAdapter {

    TextView tvmahoadon,tvngay;
    Context context;
    ArrayList<BillModel> billModels;
    BillDao dao;



    public BillAdapter(@NonNull Context context, ArrayList<BillModel> billModels) {
        super(context, 0, billModels);
        this.context = context;
        this.billModels = billModels;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        }
        final BillModel bill = billModels.get(position);
        if (bill != null) {
           tvmahoadon = v.findViewById(R.id.tvmahoadon);
           tvngay = v.findViewById(R.id.tvngay);

           tvmahoadon.setText(bill.getMahoadon());
           tvngay.setText(bill.getNgaytao());

        }



        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] item = {"CREATE BILL DETAIL","UPDATE","DELETE"};

                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("MỜI CHỌN TÁC VỤ");
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i==0){
                            BillModel bil = billModels.get(position);
                            Intent intent = new Intent(getContext(), ThemDetailHD.class);
                            intent.putExtra("BILL",bil.getMahoadon());
                            context.startActivity(intent);
                        }
                        if (i==1){
                            final Dialog dialog2 = new Dialog(getContext());
                            dialog2.setContentView(R.layout.dialog_update_bill);
                            final TextView tvupdatema = dialog2.findViewById(R.id.tvupdatemabill);
                            final TextView tvupdatengay = dialog2.findViewById(R.id.tvupdatengaybill);
                            final Button btnupdate = dialog2.findViewById(R.id.btnupdatebill);
                            final Button btncancel = dialog2.findViewById(R.id.btncancelupdatebill);

                            BillModel b = billModels.get(position);

                            tvupdatema.setText(b.getMahoadon());
                            tvupdatengay.setText(b.getNgaytao());

                            tvupdatengay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final Calendar calendar = Calendar.getInstance();
                                    int ngay = calendar.get(Calendar.DATE);
                                    int thang = calendar.get(Calendar.MONTH);
                                    int nam = calendar.get(Calendar.YEAR);
                                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                            calendar.set(i, i1, i2);
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                            tvupdatengay.setText(simpleDateFormat.format(calendar.getTime()));
                                        }
                                    }, nam, thang, ngay);
                                    datePickerDialog.show();
                                }
                            });


                            btnupdate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String mahd = tvupdatema.getText().toString();
                                    String ngay = tvupdatengay.getText().toString();

                                    BillModel bi = new BillModel(mahd,ngay);
                                    dao = new BillDao(getContext());
                                    dao.update(bi);
                                    dialog2.dismiss();
                                }
                            });

                            btncancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(),"ĐÃ HỦY",Toast.LENGTH_LONG).show();
                                    dialog2.dismiss();
                                }
                            });





                            dialog2.show();
                        }
                        if (i==2){
                            CharSequence[] item = {"KHÔNG", "CÓ"};

                            final AlertDialog.Builder dialog3 = new AlertDialog.Builder(getContext());
                            dialog3.setTitle("BẠN CÓ CHẮC MUỐN XÓA HÓA ĐƠN NÀY KHÔNG ?");
                            dialog3.setItems(item, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i==0){
                                        Toast.makeText(getContext(),"ĐÃ HỦY",Toast.LENGTH_LONG).show();
                                    }
                                    if (i==1){
                                        BillModel nd = billModels.get(position);
                                        dao = new BillDao(getContext());
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
