package com.example.nguoi_dung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.nguoi_dung.molder.TaiKhoan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SigupActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPass, edtRepass;
    AppCompatButton btnSigup;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigup);
        edtName = findViewById(R.id.edtName1);
        edtEmail = findViewById(R.id.edtEmail1);
        edtPass = findViewById(R.id.edtPass1);
        edtRepass = findViewById(R.id.edtRepass1);
        btnSigup = findViewById(R.id.btnSigup);

        btnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                String repass = edtRepass.getText().toString();
                if (pass == repass){
                    AddTaiKhoan(name,email,pass);
                }

            }
        });

    }

    private void AddTaiKhoan(String name,String email, String pass){
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("TaiKhoan");
        String id = mDatabase.push().getKey();
        TaiKhoan taiKhoan = new TaiKhoan(id ,name, email, pass);
        mDatabase.child(id).setValue(taiKhoan);
    }
}