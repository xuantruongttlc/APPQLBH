package com.example.thulai.fragment;

import static com.example.thulai.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.thulai.MainActivity;
import com.example.thulai.R;
import com.example.thulai.databinding.FragmentHomeBinding;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MyProfileFragment extends Fragment {


    private View mview;

    private ImageView imgAvatar;

    private EditText edtFullName, edtEmail;

    private Button btnUpdateProfile,btnUpdateEmail;
    private MainActivity mMainActivity;
    private Uri mUri;

    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mview =  inflater.inflate(R.layout.fragment_myprofile, container, false);

        initUi();
        mMainActivity = (MainActivity) getActivity();
        progressDialog = new ProgressDialog(getActivity());
        setUserInformation();
        initListner();



        return mview;

    }

    private void initListner() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }
        });
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateEmail();
            }
        });
    }

    private void onClickRequestPermission() {
        if(mMainActivity == null){
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mMainActivity.openGallery();
            return;
        }

        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            mMainActivity.openGallery();
        }
        else {
            String [] permisstion = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permisstion, MY_REQUEST_CODE);
        }
    }



    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }

        edtFullName.setText(user.getDisplayName());
        edtEmail.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.avatar).into(imgAvatar);
    }




    private void initUi(){
        imgAvatar = mview.findViewById(R.id.img_avatar);
        edtFullName = mview.findViewById(R.id.edt_full_name);
        edtEmail = mview.findViewById(R.id.edt_Email);
        btnUpdateProfile = mview.findViewById(R.id.btn_UpdateProfile);
        btnUpdateEmail = mview.findViewById(R.id.btn_UpdateEmail);
    }

    public void setBitmapImageView(Bitmap bitmapImageView){
        imgAvatar.setImageBitmap(bitmapImageView);
    }

    public void setmUri(Uri mUri) {
        this.mUri = mUri;
    }

    private void onClickUpdateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        progressDialog.show();
        String strFullName = edtFullName.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            mMainActivity.showUserInformation();
                        }
                    }
                });

    }
    private void onClickUpdateEmail(){
        String strNewEmail = edtEmail.getText().toString().trim();
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateEmail(strNewEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"Cập nhật Email thành công", Toast.LENGTH_SHORT).show();
                            mMainActivity.showUserInformation();
                        }
                    }
                });
    }

}
