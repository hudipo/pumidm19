package com.hudipo.pum_indomaret.features.status.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private List<StatusResponse.StatusModel> statusModelList;

    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onItemClick(StatusResponse.StatusModel currentStatus);
    }

    public StatusAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setStatusModelList(List<StatusResponse.StatusModel> statusModelList) {
        this.statusModelList = statusModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_status, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(statusModelList.get(position));
    }

    @Override
    public int getItemCount() {
        if (statusModelList.isEmpty()) {
            return 0;
        } else {
            return statusModelList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvPumNumber)
        TextView tvPumNumber;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.bgStatus)
        View bgStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(StatusResponse.StatusModel statusModel) {
            tvPumNumber.setText(statusModel.getTrx_num());
            if (statusModel.getPum_status().equals("N")){
                tvStatus.setText("Waiting");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_waiting));
            }else if (statusModel.getPum_status().equals("I")){
                tvStatus.setText("Reject");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_reject));
            }else if (statusModel.getPum_status().equals("A")){
                tvStatus.setText("Approved");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_approve));
            }else{
                tvStatus.setText("Process");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_approve));
            }
            itemClickListener.onItemClick(statusModel);
        }
    }
}
