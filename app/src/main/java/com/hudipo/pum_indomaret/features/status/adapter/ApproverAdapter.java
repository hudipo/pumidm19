package com.hudipo.pum_indomaret.features.status.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.status.model.Approver;
import com.hudipo.pum_indomaret.features.status.model.StatusDetailResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApproverAdapter extends RecyclerView.Adapter<ApproverAdapter.ViewHolder> {

    private List<Approver> list;

    public ApproverAdapter(List<Approver> approver){
        this.list = approver;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approver,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(Approver approver) {
            tvName.setText(approver.name);
        }
    }
}
