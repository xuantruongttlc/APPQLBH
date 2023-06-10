package com.example.thulai;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.thulai.fragment.ChagePasswordFragment;
import com.example.thulai.fragment.MatHangFragment;
import com.example.thulai.fragment.GhiChuFragment;
import com.example.thulai.fragment.HomeFragment;
import com.example.thulai.fragment.MyProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOError;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout ndrawerLayout;

        final private ActivityResultLauncher<Intent> mActivytiResultLancher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                if (intent == null){
                    return;
                }
                Uri uri = intent.getData();
                mMyProfileFragment.setmUri(uri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    mMyProfileFragment.setBitmapImageView(bitmap);
                }catch(IOException e){
                    e.printStackTrace();

            }
            }
        }
    });

    public static final int MY_REQUEST_CODE  = 10;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FAVOPRITE = 1;
    private static final int FRAGMENT_HISTORY = 2;

    private static final int FRAGMENT_MY_PROFILE = 3;

    private  static final int FRAGEMNT_CHANGEP_PASSWORD = 4;

    private ImageView imagAvatar;
    private TextView tvName;
    private TextView tvMail;
    private NavigationView mnavigationView;

    private int mCurrenFragment = FRAGMENT_HOME;

    final private MyProfileFragment mMyProfileFragment = new MyProfileFragment();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, ndrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ndrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mnavigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        mnavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        showUserInformation();

    }
    private void  openHomeFragment(){
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.conten_frame, homeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }



    //check nut

    private void initUi(){
        mnavigationView = findViewById(R.id.navigation_view);
        imagAvatar = mnavigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        tvName = mnavigationView.getHeaderView(0).findViewById(R.id.tv_User);
        tvMail  = mnavigationView.getHeaderView(0).findViewById(R.id.tv_Gmail);
        ndrawerLayout = findViewById(R.id.drawer_layout);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            if(mCurrenFragment  != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrenFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_Mathang) {
            if(mCurrenFragment  != FRAGMENT_FAVOPRITE){
                replaceFragment(new MatHangFragment());
                mCurrenFragment = FRAGMENT_FAVOPRITE;
            }
        }
        else if (id == R.id.nav_SoGhiNo) {
            if(mCurrenFragment  != FRAGMENT_HISTORY){
                replaceFragment(new GhiChuFragment());
                mCurrenFragment = FRAGMENT_HISTORY;
            }
        }
        else if (id == R.id.outlogin){
            FirebaseAuth.getInstance().signOut();
            //đăng xuất
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            //Khởi động
            startActivity(intent);
            finishAffinity();
        }
        else if (id == R.id.nav_my_profile) {
            if(mCurrenFragment  != FRAGMENT_MY_PROFILE){
                replaceFragment(mMyProfileFragment);
                mCurrenFragment = FRAGMENT_MY_PROFILE;
            }
        }
        else if (id == R.id.nav_change_password) {
            if(mCurrenFragment  != FRAGEMNT_CHANGEP_PASSWORD){
                replaceFragment(new ChagePasswordFragment());
                mCurrenFragment = FRAGEMNT_CHANGEP_PASSWORD;
            }
        }
        ndrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (ndrawerLayout.isDrawerOpen(GravityCompat.START)) {
            ndrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.conten_frame, fragment);
        transaction.commit();
    }

    public void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null){
            tvName.setVisibility(View.GONE);
        }
        else {
            tvName.setVisibility(View.VISIBLE);
        }
        tvName.setText(name);
        tvMail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.avatar).into(imagAvatar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivytiResultLancher.launch(Intent.createChooser(intent, "Select Picture"));
    }
}