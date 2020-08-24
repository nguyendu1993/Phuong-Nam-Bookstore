package com.example.quanlysach.Model;

public class HDCTModel {
    private String mahdct;
    private String mahd;
    private String ngay;
    private String sanpham;
    private String giasp;
    private String slsanpham;
    private String tongtien;

    public HDCTModel(){}

    public HDCTModel(String mahdct, String mahd, String ngay, String sanpham, String giasp, String slsanpham, String tongtien) {
        this.mahdct = mahdct;
        this.mahd = mahd;
        this.ngay = ngay;
        this.sanpham = sanpham;
        this.giasp = giasp;
        this.slsanpham = slsanpham;
        this.tongtien = tongtien;
    }

    public String getMahdct() {
        return mahdct;
    }

    public void setMahdct(String mahdct) {
        this.mahdct = mahdct;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getSanpham() {
        return sanpham;
    }

    public void setSanpham(String sanpham) {
        this.sanpham = sanpham;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getSlsanpham() {
        return slsanpham;
    }

    public void setSlsanpham(String slsanpham) {
        this.slsanpham = slsanpham;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }
}
