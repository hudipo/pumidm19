package com.hudipo.pum_indomaret.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.historyresponse.HistoryResponseItem;
import com.hudipo.pum_indomaret.utils.Global;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResponseHistoryAdapter extends RecyclerView.Adapter<ResponseHistoryAdapter.ViewHolder>{

    private List<HistoryResponseItem> items;

    public void setItems(List<HistoryResponseItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvResponsibilityNumber)
        TextView tvResponsibilityNumber;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvAmount)
        TextView tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(HistoryResponseItem historyResponseItem) {
            tvResponsibilityNumber.setText(historyResponseItem.getPUMRESPTRXNUM());
            tvDate.setText(historyResponseItem.getRESPDATE());
            tvAmount.setText(String.format("Rp. %s", Global.priceFormatter(String.valueOf(historyResponseItem.getAMOUNT()))));
        }
    }
}
