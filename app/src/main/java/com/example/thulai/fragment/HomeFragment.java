package com.example.thulai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thulai.LoginActivity;
import com.example.thulai.R;
import com.example.thulai.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding homeBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);

    }
}
