package com.example.quanlysach.Model;

public class Sach {

    private String maSach;
    private String tenSach;
    private String loaiSach;
    private String gia;
    private String img;
    private String soluong;
    private String baove;

    public Sach(){

    }

    public Sach(String maSach, String tenSach, String loaiSach, String gia, String soluong, String img,String baove) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.loaiSach = loaiSach;
        this.gia = gia;
        this.img = img;
        this.soluong = soluong;
        this.baove = baove;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getLoaiSach() {
        return loaiSach;
    }

    public void setLoaiSach(String loaiSach) {
        this.loaiSach = loaiSach;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getBaove() {
        return baove;
    }

    public void setBaove(String baove) {
        this.baove = baove;
    }
}
