package com.example.nguoi_dung.molder;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Laptop {
    private String id;
    private String tensp;
    private double giasp;
    private String hinhanh;

    private int soLuong = 0;//add to cart


    public Laptop() {
    }

    public Laptop(String id, String tensp, double giasp, String hinhanh) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhanh = hinhanh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public double getGiasp() {
        return giasp;
    }

    public void setGiasp(double giasp) {
        this.giasp = giasp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("tensp", tensp);
        result.put("giasp", giasp);
        result.put("hinhanh", hinhanh);

        return result;
    }
}
