package com.example.nguoi_dung.molder;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

//Cart
public class Cart {
    private String id;
    private String hinhanh;
    private String tensp;
    private double giasp;
    private int soluong;

    public Cart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cart(String id, String hinhanh, String tensp, double giasp) {
        this.id = id;
        this.hinhanh = hinhanh;
        this.tensp = tensp;
        this.giasp = giasp;
    }

    public Cart(String hinhanh, String tensp, double giasp, int soluong) {
        this.hinhanh = hinhanh;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
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

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("tensp", tensp);
        result.put("giasp", giasp);
        result.put("hinhanh", hinhanh);
        result.put("soluong", soluong);

        return result;
    }
}
