package com.hudipo.pum_indomaret.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.docdetail.DocData;
import com.hudipo.pum_indomaret.model.docdetail.DocDataItem;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.utils.Global;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDocumentAdapter extends RecyclerView.Adapter<SearchDocumentAdapter.ViewHolder> implements Filterable {

    private ItemClickListener itemClickListener;
    private DocDetailResponse docDetailResponse;
    private List<DocDataItem> docDataFilter;

    public SearchDocumentAdapter(DocDetailResponse docDetailResponse, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.docDetailResponse = docDetailResponse;
        this.docDataFilter = docDetailResponse.getDocData().getData();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSearch = constraint.toString().toLowerCase().trim();
                if (charSearch.isEmpty()){
                    docDataFilter = docDetailResponse.getDocData().getData();
                }else {
                    List<DocDataItem> resultFilter = new ArrayList<>();
                    if (docDetailResponse.getDocData().getData() != null){
                        for (DocDataItem row: docDetailResponse.getDocData().getData()){
                            if (row != null){
                                if (row.getDocNum().contains(charSearch) ||
                                    row.getDocDate().contains(charSearch) ||
                                    String.valueOf(row.getDocAmount()).contains(charSearch)){
                                    resultFilter.add(row);
                                }
                            }
                        }
                        docDataFilter = resultFilter;
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = docDataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null){
                    docDataFilter = (ArrayList<DocDataItem>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
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
        holder.bindItem(docDataFilter.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        if (docDataFilter != null){
            return docDataFilter.size();
        }
        return 0;
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
            String string = "Rp "+ Global.priceFormatter(docDataItem.getDocAmount());
            tvAmount.setText(string);

            itemView.setOnClickListener(view -> itemClickListener.onItemClick(docDataItem));
        }
    }


}
