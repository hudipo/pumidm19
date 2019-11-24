package com.hudipo.pum_indomaret.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.trxtype.TrxItem;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTrxAdapter extends RecyclerView.Adapter<SearchTrxAdapter.ViewHolder>{

    private TrxTypeResponse trxTypeResponse;
    private ItemClickListener itemClickListener;

    public SearchTrxAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTrxTypeResponse(TrxTypeResponse trxTypeResponse) {
        this.trxTypeResponse = trxTypeResponse;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_dept, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(trxTypeResponse.getTrx().get(position));
    }

    @Override
    public int getItemCount() {
        return trxTypeResponse.getTrx().size();
    }

    public interface ItemClickListener{
        void onClick(TrxItem trxItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitleSearchDept)
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(TrxItem trxItem) {
            tvTitle.setText(trxItem.getDescription());

            itemView.setOnClickListener(v-> itemClickListener.onClick(trxItem));
        }
    }
}
