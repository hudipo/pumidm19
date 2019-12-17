package com.hudipo.pum_indomaret.features.status.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> implements Filterable {

    private List<StatusResponse.StatusModel> statusModelList ;
    private List<StatusResponse.StatusModel> filteredStatusList;

    private ItemClickListener itemClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredStatusList = statusModelList;
                } else {
                    List<StatusResponse.StatusModel> filteredList = new ArrayList<>();
                    for (StatusResponse.StatusModel row : statusModelList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTRX_NUM().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredStatusList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredStatusList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredStatusList = (ArrayList<StatusResponse.StatusModel>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    }

    public interface ItemClickListener {
        void onItemClick(StatusResponse.StatusModel currentStatus);
    }

    public StatusAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setStatusModelList(List<StatusResponse.StatusModel> statusModelList) {
        this.statusModelList = statusModelList;
        this.filteredStatusList = statusModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_status, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(filteredStatusList.get(position));
    }

    @Override
    public int getItemCount() {
        if (filteredStatusList==null) {
            return 0;
        } else {
            return filteredStatusList.size();
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
            itemView.setOnClickListener(view -> {
                itemClickListener.onItemClick(statusModel);
            });
            tvPumNumber.setText(statusModel.getTRX_NUM());
            if (statusModel.getPUM_STATUS().equals("N")){
                tvStatus.setText("New");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_waiting));
            }else if (statusModel.getPUM_STATUS().equals("R")){
                tvStatus.setText("Reject");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_reject));
            }else if (statusModel.getPUM_STATUS().equals("I")){
                tvStatus.setText("Invoice");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_approve));
            }else if (statusModel.getPUM_STATUS().equals("A")){
                tvStatus.setText("Waiting Finance");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.bg_status_process));
            }else if (statusModel.getPUM_STATUS().equals("APP1")){
                tvStatus.setText("Approval 1");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.bg_status_process));
            }else if (statusModel.getPUM_STATUS().equals("APP2")){
                tvStatus.setText("Approval 2");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.bg_status_process));
            }else if (statusModel.getPUM_STATUS().equals("APP3")){
                tvStatus.setText("Approval 3");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.bg_status_process));
            }else if (statusModel.getPUM_STATUS().equals("APP4")){
                tvStatus.setText("Approval 4");
                bgStatus.setBackground(itemView.getContext().getDrawable(R.drawable.bg_status_process));
            }
        }
    }
}
