package com.example.thulai.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thulai.AddMatHang;
import com.example.thulai.MatHang;
import com.example.thulai.MatHangAdapter;
import com.example.thulai.R;
import com.example.thulai.UpdateMatHang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import database.MatHangDatabase;
import database.NameDatabase;

public class MatHangFragment extends Fragment {

    private static final int MY_REQUEST_CODE = 10;
    private EditText edtNameMH;
    private EditText edtGiaMH;
    private EditText edtSoLuongMH;
    private Button btADDMH;
    private RecyclerView rcvDSMH;

    private MatHangAdapter mathangAdapter;

    private TextView tvDeleteAllMH;

    private EditText editTextSearchMH;
    private TextView tvTongSL;

    private List<MatHang> mListNameMH;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mathang, container, false);
        edtNameMH = view.findViewById(R.id.edt_nameMatHang);
        edtGiaMH = view.findViewById(R.id.edt_GiaTienMH);
        edtSoLuongMH = view.findViewById(R.id.edt_SoLuongmh);
        btADDMH = view.findViewById(R.id.bt_themMathang);
        rcvDSMH = view.findViewById(R.id.rcv_MH);
        tvDeleteAllMH = view.findViewById(R.id.tv_deleteAllMH);
        editTextSearchMH = view.findViewById(R.id.edt_searchMH);
        tvTongSL = view.findViewById(R.id.tv_TongSLMH);


        mathangAdapter = new MatHangAdapter(new MatHangAdapter.IClickItemName() {
            @Override
            public void updateName(MatHang mathang) {
                clickUpdateName(mathang);
            }

            @Override
            public void deleteName(MatHang mathang) {
                clickDeleteName(mathang);
            }
        });

        mListNameMH = new ArrayList<>();
        mathangAdapter.setData(mListNameMH);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rcvDSMH.setLayoutManager(linearLayoutManager);

        rcvDSMH.setAdapter(mathangAdapter);

        btADDMH.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

//                addName();
                Intent intent = new Intent(getActivity(), AddMatHang.class);
                startActivityForResult(intent, MY_REQUEST_CODE);
//                startActivity(intent);

            }
        });
        tvDeleteAllMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDeleteAll();
            }
        });
        editTextSearchMH.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    //Logic search

                    handleSearchName();
                }
                return false;
            }
        });


        loadData();

        return view;
    }

    public void AnBanPhim() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    public void loadData(){
        mListNameMH = MatHangDatabase.getInstance(requireContext()).mhDAO().getListName();
        mathangAdapter.setData(mListNameMH);

        int itemCount = mListNameMH.size();
        tvTongSL.setText("Số lượng: " + itemCount);
    }

    public boolean isCheckName(MatHang mathang){
        List<MatHang> list = MatHangDatabase.getInstance(requireContext()).mhDAO().checkName(mathang.getNameMH());
        return list != null && !list.isEmpty();
    }

    private void clickUpdateName(MatHang mathang){
        Intent intent = new Intent(MatHangFragment.this.getContext(), UpdateMatHang.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("oject_nameMH", (Serializable) mathang);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            loadData();
        }
    }

    private void clickDeleteName(final MatHang mathang){
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xóa mặt hàng")
                .setMessage("Bạn có chắc chắn muốn xóa mặt hàng này không?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Xóa tên nợ
                        MatHangDatabase.getInstance(requireContext()).mhDAO().deleteNameMH(mathang);
                        Toast.makeText(requireContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();

                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void clickDeleteAll(){
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xóa tất cả mặt hàng đang có")
                .setMessage("Bạn có chắc chắn muốn xóa tất cả mặt hàng của bạn không?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Xóa tên nợ
                        NameDatabase.getInstance(requireContext()).nameDAO().deleteAll();
                        Toast.makeText(requireContext(), "Xoá danh sách thành công", Toast.LENGTH_SHORT).show();

                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void handleSearchName(){
        String strKeyword = editTextSearchMH.getText().toString().trim();
        mListNameMH = new ArrayList<>();
        mListNameMH = MatHangDatabase.getInstance(requireContext()).mhDAO().searchName(strKeyword);
        mathangAdapter.setData(mListNameMH);
        AnBanPhim();


    }

}
