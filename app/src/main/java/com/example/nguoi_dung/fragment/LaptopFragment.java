package com.example.nguoi_dung.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nguoi_dung.MainActivity;
import com.example.nguoi_dung.R;
import com.example.nguoi_dung.adapter.AddCard;
import com.example.nguoi_dung.adapter.LaptopAdapter;
import com.example.nguoi_dung.molder.Laptop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LaptopFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private LaptopAdapter sanPhamAdapter;
    private List<Laptop> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laptop, container, false);
        getListDatabase();
        setupRecyclerView(view);


        return view;
    }
    private void getListDatabase(){
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("SanPham");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Laptop sanPham = dataSnapshot.getValue(Laptop.class);
                    list.add(sanPham);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerQLSanPham);
        LinearLayoutManager linearLayout = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayout);
        list = new ArrayList<>();
        sanPhamAdapter = new LaptopAdapter(list,getContext());
        recyclerView.setAdapter(sanPhamAdapter);
        sanPhamAdapter.setAddCard(new AddCard() {
            @Override
            public void onItemClicked(Laptop sp) {


                MainActivity.spCart.add(sp);
                sp.setSoLuong(1);
                MainActivity.numInCart++;
                MainActivity.updateBadge();

                sanPhamAdapter.notifyDataSetChanged();

            }
        });


    }


}