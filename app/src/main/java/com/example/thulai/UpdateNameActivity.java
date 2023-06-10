package com.example.thulai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.NameDatabase;

public class UpdateNameActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtTienNo;
    private Button btnUpdateName;

    private Name mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_name);

        edtName = findViewById(R.id.edt_nameNo);
        edtTienNo = findViewById(R.id.edt_SoTienNo);
        btnUpdateName = findViewById(R.id.btn_UpdateTT);

        mName = (Name) getIntent().getExtras().get("oject_name");

        if (mName != null){
            edtName.setText(mName.getName());
            edtTienNo.setText(mName.getSotienno());
        }

        btnUpdateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateName();
            }
        });
    }

    private void updateName() {
        String strName = edtName.getText().toString().trim();
        String strSoTienNo = edtTienNo.getText().toString().trim();

        if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strSoTienNo)){
            return;
        }

        //Update name
        mName.setName(strName);
        mName.setSotienno(strSoTienNo);

        NameDatabase.getInstance(this).nameDAO().updateName(mName);

        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();

        setResult(Activity.RESULT_OK, intentResult);

        finish();
    }
}