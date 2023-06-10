package com.example.thulai;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import database.MatHangDatabase;

public class AddMatHang extends AppCompatActivity {
    private EditText edtNameMH;
    private android.widget.EditText edtGiaMH;
    private EditText edtSoLuongMH;
    private Button btnAddMH;
    private MatHang matHang;
    private List<MatHang> mListNameMH;
    private MatHangAdapter mathangAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mat_hang);

        edtNameMH = findViewById(R.id.edt_nameMatHang);
        edtGiaMH = findViewById(R.id.edt_GiaTienMH);
        edtSoLuongMH = findViewById(R.id.edt_SoLuongmh);
        btnAddMH = findViewById(R.id.bt_themMathang);



        btnAddMH.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                addName();

            }
        });


    }
    private void addName() {
        String strNameMH = edtNameMH.getText().toString().trim();
        String strGiaTienMH = edtGiaMH.getText().toString().trim();
        String strSoLuongMH = edtSoLuongMH.getText().toString().trim();

        if (TextUtils.isEmpty(strNameMH) || TextUtils.isEmpty(strGiaTienMH) || TextUtils.isEmpty(strSoLuongMH) ){
            return;
        }

        MatHang mathang = new MatHang(strNameMH, strGiaTienMH, strSoLuongMH);

        if (isCheckName(mathang)){
            Toast.makeText(this, "Tên mặt hàng đã tồn tại", Toast.LENGTH_SHORT).show();
//            return;

        }
        else {
            MatHangDatabase.getInstance(this).mhDAO().inserNameMH(mathang);

            Toast.makeText(this, "Bạn đã thêm mặt hàng thành công", Toast.LENGTH_SHORT).show();

            edtNameMH.setText("");
            edtGiaMH.setText("");
            edtSoLuongMH.setText("");



            loadData();
            Intent intentResult = new Intent();
            setResult(Activity.RESULT_OK, intentResult);

            finish();

        }

    }


    public boolean isCheckName(MatHang mathang){
        List<MatHang> list = MatHangDatabase.getInstance(this).mhDAO().checkName(mathang.getNameMH());
        return list != null && !list.isEmpty();
    }

    public void loadData(){
        mListNameMH = MatHangDatabase.getInstance(this).mhDAO().getListName();
        mathangAdapter.setData(mListNameMH);

    }



}