package com.example.thulai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatHangAdapter extends RecyclerView.Adapter<MatHangAdapter.NameMHViewHolder> {

    public class NameMHViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameMH;
        private TextView tvGiaTienMH;

        private TextView tvSoLuongMH;
        private Button btnUpdateMH;

        private Button btnDeleteMH;
        public NameMHViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameMH = itemView.findViewById(R.id.tv_NameMatHang);
            tvGiaTienMH = itemView.findViewById(R.id.tv_GiaMatHang);
            tvSoLuongMH = itemView.findViewById(R.id.tv_SoLuongMH);
            btnUpdateMH = itemView.findViewById(R.id.btn_UpdateMH);
            btnDeleteMH = itemView.findViewById(R.id.btn_DeleteMH);
        }
    }

    private List<MatHang> mListNameMH;


    private IClickItemName iClickItemName;

//    MatHangAdapter mathangAdapter = new MatHangAdapter(new MatHangAdapter.IClickItemName() {
//        @Override
//        public void updateName(MatHang mathang) {
//            // Xử lý cập nhật tên mặt hàng
//        }
//
//        @Override
//        public void deleteName(MatHang mathang) {
//            // Xử lý xóa tên mặt hàng
//        }
//    });


    public interface IClickItemName {
        void updateName(MatHang mathang);

        void deleteName(MatHang mathang);
    }
    public MatHangAdapter(IClickItemName iClickItemName) {

        this.iClickItemName =  iClickItemName;
    }

    public void setData(List<MatHang> list){
        this.mListNameMH = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NameMHViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mat_hang, parent, false);
        return new NameMHViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameMHViewHolder holder, int position) {
        final MatHang mathang = mListNameMH.get(position);
        if (mathang == null){
            return;
        }
        holder.tvNameMH.setText(mathang.getNameMH());
        holder.tvGiaTienMH.setText(mathang.getGiaTienMH());
        holder.tvSoLuongMH.setText(mathang.getSoLuongMh());

        holder.btnUpdateMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    iClickItemName.updateName(mathang);
            }
        });

        holder.btnDeleteMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    iClickItemName.deleteName(mathang);
            }
        });

    }


    @Override
    public int getItemCount() {
        if(mListNameMH != null){
            return mListNameMH.size();
        }
        return 0;
    }
}
