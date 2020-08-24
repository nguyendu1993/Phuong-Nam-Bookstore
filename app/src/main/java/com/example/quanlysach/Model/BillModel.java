package com.example.quanlysach.Model;

public class BillModel {
    private String mahoadon;
    private String ngaytao;

    public BillModel(){

    }

    public BillModel(String mahoadon, String ngaytao) {
        this.mahoadon = mahoadon;
        this.ngaytao = ngaytao;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }
}
