package com.example.nguoi_dung.fragment;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguoi_dung.MainActivity;
import com.example.nguoi_dung.R;
import com.example.nguoi_dung.adapter.CheckBoxItem;
import com.example.nguoi_dung.adapter.GioHangAdapter;
import com.example.nguoi_dung.molder.Cart;
import com.example.nguoi_dung.molder.GioHang;
import com.example.nguoi_dung.molder.Laptop;
import com.example.nguoi_dung.utils.AppUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GioHangFragment extends Fragment {

    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private GioHangAdapter gioHangAdapter;
    private List<Cart> list;
    private CheckBox ckbAll;
    private int tongtien = 0;
    private TextView txtTongtienAll,muaHang;
    private Laptop laptop;

    private ArrayList<Laptop> spOrders = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spOrders = MainActivity.spCart;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        setupRecyclerView(view);
        setupUI(view);//Khoi tao giao dien: dinh nghia view, set gia tri cho view
        onListener(view);//Set cac su kien: click,touch,scroll....
        setupCartInfo();
        return view;
    }
    private void setupUI(View view){
        ckbAll= view.findViewById(R.id.ckbAll);
        txtTongtienAll = view.findViewById(R.id.txtTongtienAll);
        muaHang = view.findViewById(R.id.txtMuahang);
        //Set value for views
        setupCartInfo();
    }
    private void onListener(View view) {
        ckbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.itemOrders.clear();
                if (!ckbAll.isChecked()){
                    ckbAll.setChecked(false);
                    setupUI(view);


                }else{
                    MainActivity.itemOrders.addAll(MainActivity.spCart);
                    ckbAll.setChecked(true);
                    setupCartInfo();
                    setupUI(view);
                }
                gioHangAdapter.notifyDataSetChanged();

            }
        });


        muaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddedToOrders();

            }
        });
    }
    //
    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerQLCard);
        LinearLayoutManager linearLayout = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(linearLayout);
        list = new ArrayList<>();
        gioHangAdapter = new GioHangAdapter(MainActivity.spCart,getContext());
        recyclerView.setAdapter(gioHangAdapter);
        gioHangAdapter.setListener(new GioHangAdapter.ItemChangedListener() {
            @Override
            public void onChanged() {
                setupCartInfo();
            }
        });

    }
    private void isAddedToOrders(){
        if (MainActivity.itemOrders.size() == 0){
            Toast.makeText(getActivity(), "Bạn chưa có sản phẩm nào trong giỏ hàng!!", Toast.LENGTH_SHORT).show();
            return;
        }
        showDiaLogMuaHang();

    }
    private void showDiaLogMuaHang(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_muahang,null);

        EditText edtHoten = view.findViewById(R.id.edt_hoTen);
        EditText edtEmail = view.findViewById(R.id.edt_email);
        EditText edtSdt = view.findViewById(R.id.edt_sdt);
        EditText edtDiachi = view.findViewById(R.id.edt_diaChi);

        builder.setView(view);

        builder.setPositiveButton("Mua hàng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                addToGioHang(edtHoten.getText().toString(), edtEmail.getText().toString(), edtSdt.getText().toString(), edtDiachi.getText().toString(),MainActivity.itemOrders);
                for (Laptop laptop: MainActivity.itemOrders){
                    MainActivity.spCart.remove(laptop);
                }
                MainActivity.itemOrders.clear();


                setupCartInfo();

                MainActivity.updateBadge();

                gioHangAdapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void addToGioHang(String hoten, String email, String sdt, String diachi, ArrayList<Laptop> list){
        mDatabase = FirebaseDatabase.getInstance().getReference("GioHang");
        String id = mDatabase.push().getKey();
        GioHang hang = new GioHang(id,hoten, email, sdt, diachi, list);
        mDatabase.child(id).setValue(hang);

    }
    //Hien thi thong tin cua  GioHang
    private void setupCartInfo(){
        long total = 0;
        for (Laptop laptop: MainActivity.itemOrders){
            total += laptop.getGiasp()*laptop.getSoLuong();
        }
        txtTongtienAll.setText(AppUtils.formatNumber(total)+"₫");
    }



}