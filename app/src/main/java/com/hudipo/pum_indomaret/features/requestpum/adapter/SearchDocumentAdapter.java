package com.hudipo.pum_indomaret.features.requestpum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.model.DocumentDetailRequestModel;
import com.hudipo.pum_indomaret.model.docdetail.DataItem;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDocumentAdapter extends RecyclerView.Adapter<SearchDocumentAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private ArrayList<DocumentDetailRequestModel> documentDetailRequestModels;
    private DocDetailResponse docDetailResponse;

    public SearchDocumentAdapter(DocDetailResponse docDetailResponse, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.docDetailResponse = docDetailResponse;
    }


    public interface ItemClickListener{
        void onItemClick(DataItem dataItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_search_document, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(docDetailResponse.getData().getData().get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return docDetailResponse.getData().getData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDocNum)
        TextView tvDocNume;
        @BindView(R.id.tvDocDate)
        TextView tvDocDate;
        @BindView(R.id.tvAmount)
        TextView tvAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindItem(DataItem dataItem, ItemClickListener itemClickListener) {
            tvDocNume.setText(dataItem.getDocNum());
            tvDocDate.setText(dataItem.getDocDate());
            String string = "Rp "+ dataItem.getDocAmount();
            tvAmount.setText(string);

            itemView.setOnClickListener(view -> itemClickListener.onItemClick(dataItem));
        }
    }


}
