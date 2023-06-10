package com.example.thulai.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thulai.R;
import com.example.thulai.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChagePasswordFragment extends Fragment {

    private View mView;
    private EditText edtNewPassWord;
    Button btnChangePasssWord;

    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView =  inflater.inflate(R.layout.fragment_changepassword, container, false);

        initUi();
        btnChangePasssWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangePassword();
            }
        });
        return mView;

    }
    private void initUi(){
        progressDialog = new ProgressDialog(getActivity());
        edtNewPassWord = mView.findViewById(R.id.edt_newPass);
        btnChangePasssWord = mView.findViewById(R.id.btn_Change_password);
    }
    private void onClickChangePassword() {
        String strNewPassword = edtNewPassWord.getText().toString().trim();
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = strNewPassword;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }


}
