package com.example.thulai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameViewHolder> {
    private List<Name> mListName;

    private IClickItemName iClickItemName;

    public interface IClickItemName {
        void updateName(Name name);

        void deleteName(Name name);
    }

    public NameAdapter(IClickItemName iClickItemName) {
        this.iClickItemName = iClickItemName;
    }

    public void setData(List<Name> list){
        this.mListName = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
            final Name name = mListName.get(position);
            if (name == null){
                return;
            }
            holder.tvName.setText(name.getName());
            holder.tvSoTienNo.setText(name.getSotienno());

            holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemName.updateName(name);
                }
            });

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemName.deleteName(name);
                }
            });

    }

    @Override
    public int getItemCount() {
        if(mListName != null){
            return mListName.size();
        }
        return 0;
    }

    public class NameViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvSoTienNo;
        private Button btnUpdate;

        private Button btnDelete;
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_Name);
            tvSoTienNo = itemView.findViewById(R.id.tv_SoTienNo);
            btnUpdate = itemView.findViewById(R.id.btn_Update);
            btnDelete = itemView.findViewById(R.id.btn_Delete);
        }
    }
}
