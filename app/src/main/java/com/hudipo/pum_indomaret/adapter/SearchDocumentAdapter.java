package com.hudipo.pum_indomaret.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.docdetail.DocDataItem;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDocumentAdapter extends RecyclerView.Adapter<SearchDocumentAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private DocDetailResponse docDetailResponse;

    public SearchDocumentAdapter(DocDetailResponse docDetailResponse, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.docDetailResponse = docDetailResponse;
    }


    public interface ItemClickListener{
        void onItemClick(DocDataItem docDataItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_search_document, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(docDetailResponse.getDocData().getData().get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return docDetailResponse.getDocData().getData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDocNum)
        TextView tvDocNum;
        @BindView(R.id.tvDocDate)
        TextView tvDocDate;
        @BindView(R.id.tvAmount)
        TextView tvAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindItem(DocDataItem docDataItem, ItemClickListener itemClickListener) {
            tvDocNum.setText(docDataItem.getDocNum());
            tvDocDate.setText(docDataItem.getDocDate());
            String string = "Rp "+ docDataItem.getDocAmount();
            tvAmount.setText(string);

            itemView.setOnClickListener(view -> itemClickListener.onItemClick(docDataItem));
        }
    }


}
