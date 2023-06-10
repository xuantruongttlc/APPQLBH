package com.example.thulai;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import database.MatHangDatabase;
import database.NameDatabase;

public class UpdateMatHang extends AppCompatActivity {
    private EditText edtNameMH;
    private EditText edtGiaMH;
    private EditText edtSoLuong;
    private Button btnUpdateMH;
    private MatHang matHang;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mathang);

        edtNameMH = findViewById(R.id.edt_UDNameMH);
        edtGiaMH = findViewById(R.id.edt_UDGiaTienMH);
        edtSoLuong = findViewById(R.id.edt_UDSoLuongMH);
        btnUpdateMH = findViewById(R.id.btn_UpdateMH);

        matHang = (MatHang) getIntent().getExtras().get("oject_nameMH");

        if (matHang != null){
            edtNameMH.setText(matHang.getNameMH());
            edtGiaMH.setText(matHang.getGiaTienMH());
            edtSoLuong.setText(matHang.getSoLuongMh());
        }

        btnUpdateMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNameMH();
            }
        });
    }
    private void updateNameMH() {
        String strNameMH = edtNameMH.getText().toString().trim();
        String strGiaMH = edtGiaMH.getText().toString().trim();
        String strSoLuong = edtSoLuong.getText().toString().trim();

        if (TextUtils.isEmpty(strNameMH) || TextUtils.isEmpty(strGiaMH) || TextUtils.isEmpty(strSoLuong)){
            return;
        }

        //Update name
        matHang.setNameMH(strNameMH);
        matHang.setGiaTienMH(strGiaMH);
        matHang.setSoLuongMh(strSoLuong);

        MatHangDatabase.getInstance(this).mhDAO().updateNameMH(matHang);

        Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();

        setResult(Activity.RESULT_OK, intentResult);

        finish();
    }
}