package com.hudipo.pum_indomaret.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.submitresponse.SubmitResponseItem;
import com.hudipo.pum_indomaret.utils.Global;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseCartPartialAdapter extends RecyclerView.Adapter<ResponseCartPartialAdapter.ViewHolder>{

    private ArrayList<SubmitResponseItem> submitResponseItems;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener{
        void onClick(int position, int idSubmitResponse);
    }

    public void removeItemFromAdapter(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setSubmitResponseItems(ArrayList<SubmitResponseItem> submitResponseItems) {
        this.submitResponseItems = submitResponseItems;
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        submitResponseItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, submitResponseItems.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_cart_partial, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(submitResponseItems.get(position), position, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return submitResponseItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTransactionType)
        TextView tvTransactionType;
        @BindView(R.id.tvDescription)
        TextView tvDescription;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tvStoreCode)
        TextView tvStoreCode;

        private int position;
        private ItemClickListener itemClickListener;
        private int idSubmitResponse;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btnRemove)
        void btnRemove(){
            itemClickListener.onClick(position, idSubmitResponse);
        }

        public void bindItem(SubmitResponseItem submitResponseItem, int position, ItemClickListener itemClickListener) {
            tvTransactionType.setText(submitResponseItem.getTextTransactionType());
            tvDescription.setText(submitResponseItem.getDescription());
            tvAmount.setText(String.format("Rp. %s", Global.priceFormatter(submitResponseItem.getAmount())));
            tvStoreCode.setText(submitResponseItem.getStoreCode());

            this.position = position;
            this.idSubmitResponse = submitResponseItem.getId();
            this.itemClickListener = itemClickListener;
        }
    }
}
