package com.example.thulai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.PrimaryKey;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    private EditText edtAddMail;
    private Button btnGuiMail;

    private Button btnBack;

    private ProgressDialog progressDialog;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        edtAddMail = findViewById(R.id.edt_NhapMail);
        btnGuiMail = findViewById(R.id.btn_GuiMail);
        btnBack = findViewById(R.id.btn_Back);
        progressDialog = new ProgressDialog(this);

        btnGuiMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForgotPasssword();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void onClickForgotPasssword() {
        String strEmail = edtAddMail.getText().toString().trim();
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = strEmail;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotActivity.this, "Đã gửi Password về Email", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}