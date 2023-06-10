package com.example.thulai.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.thulai.Name;
import com.example.thulai.NameAdapter;
import com.example.thulai.R;
import com.example.thulai.UpdateNameActivity;

import java.util.ArrayList;
import java.util.List;

import database.NameDatabase;

public class GhiChuFragment extends Fragment {
    private static final int MY_REQUEST_CODE = 10;
    private EditText edtName;
    private EditText edtTienNo;
    private Button btADD;
    private RecyclerView rcvDSno;

    private NameAdapter nameAdapter;

    private TextView tvDeleteAll;

    private EditText editTextSearch;
    private TextView tvGhiChucout;

    private List<Name> mListName;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_ghichu, container, false);
        edtName = view.findViewById(R.id.edt_nameNo);
        edtTienNo = view.findViewById(R.id.edt_SoTienNo);
        btADD = view.findViewById(R.id.bt_themNo);
        rcvDSno = view.findViewById(R.id.rcv_No);
        tvDeleteAll = view.findViewById(R.id.tv_deleteAll);
        tvGhiChucout = view.findViewById(R.id.tv_Ghichucout);
        editTextSearch = view.findViewById(R.id.edt_search);


        nameAdapter = new NameAdapter(new NameAdapter.IClickItemName() {
            @Override
            public void updateName(Name name) {
                clickUpdateName(name);
            }

            @Override
            public void deleteName(Name name) {
                clickDeleteName(name);
            }
        });
        mListName = new ArrayList<>();
         nameAdapter.setData(mListName);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rcvDSno.setLayoutManager(linearLayoutManager);

        rcvDSno.setAdapter(nameAdapter);

        btADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addName();
            }
        });
        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDeleteAll();
            }
        });
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    private void addName() {
        String strName = edtName.getText().toString().trim();
        String strSoTienNo = edtTienNo.getText().toString().trim();

        if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strSoTienNo)){
            return;
        }

        Name name = new Name(strName, strSoTienNo);

        if (isCheckName(name)){
            Toast.makeText(requireContext(), "Tên đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        NameDatabase.getInstance(requireContext()).nameDAO().inserName(name);

        Toast.makeText(requireContext(), "Bạn đã thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();

        edtName.setText("");
        edtTienNo.setText("");
        AnBanPhim();

        loadData();

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
        mListName =  NameDatabase.getInstance(requireContext()).nameDAO().getListName();
        nameAdapter.setData(mListName);

        int itemCount = mListName.size();
        tvGhiChucout.setText("Số lượng: " + itemCount);
    }

    public boolean isCheckName(Name name){
        List<Name> list = NameDatabase.getInstance(requireContext()).nameDAO().checkName(name.getName());
        return list != null && !list.isEmpty();
    }

    private void clickUpdateName(Name name){
        Intent intent = new Intent(GhiChuFragment.this.getContext(), UpdateNameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("oject_name", name);
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

    private void clickDeleteName(final Name name){
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xóa thông tin")
                .setMessage("Bạn có chắc chắn muốn xóa tên người nợ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Xóa tên nợ
                        NameDatabase.getInstance(requireContext()).nameDAO().deleteName(name);
                        Toast.makeText(requireContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();

                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void clickDeleteAll(){
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xóa tất cả dánh sách nợ")
                .setMessage("Bạn có chắc chắn muốn xóa tất cả tên người nợ không?")
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
        String strKeyword = editTextSearch.getText().toString().trim();
        mListName = new ArrayList<>();
        mListName = NameDatabase.getInstance(requireContext()).nameDAO().searchName(strKeyword);
        nameAdapter.setData(mListName);
        AnBanPhim();


    }
}
