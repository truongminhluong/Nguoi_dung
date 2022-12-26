package com.example.nguoi_dung.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nguoi_dung.R;


public class DangNhapFragment extends Fragment {
    private AppCompatButton btnLogin;
    private EditText edtEmail, edtPass;
    private TextView txtTaotk;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        edtEmail = view.findViewById(R.id.edtEmail);
        edtPass = view.findViewById(R.id.edtPass);
        txtTaotk = view.findViewById(R.id.taotaikhoan);
        btnLogin = view.findViewById(R.id.login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();


            }
        });

        txtTaotk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(UserFragment.this, SigupActivity.class);
//                startActivity(intent);
            }
        });



        return view;
    }
}
