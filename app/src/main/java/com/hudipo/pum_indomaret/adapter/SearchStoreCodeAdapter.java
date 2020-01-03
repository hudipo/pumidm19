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
import com.hudipo.pum_indomaret.model.storecode.StoreCodeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchStoreCodeAdapter extends RecyclerView.Adapter<SearchStoreCodeAdapter.ViewHolder> implements Filterable {

    private ItemClickListener itemClickListener;
    private List<StoreCodeItem> storeCodeItems;
    private List<StoreCodeItem> filterStoreCodeItems;

    public SearchStoreCodeAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setStoreCodeItems(List<StoreCodeItem> storeCodeItems) {
        this.storeCodeItems = storeCodeItems;
        this.filterStoreCodeItems = storeCodeItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(filterStoreCodeItems.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return filterStoreCodeItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSearch = constraint.toString().toLowerCase().trim();
                if (charSearch.isEmpty()){
                    filterStoreCodeItems = storeCodeItems;
                }else {
                    List<StoreCodeItem> resultList = new ArrayList<>();
                    if (storeCodeItems != null && storeCodeItems.size() > 0){
                        for (StoreCodeItem row: storeCodeItems){
                            if (row.getSTORENAME() != null){
                                if (row.getSTORENAME().toLowerCase().trim().contains(charSearch)){
                                    resultList.add(row);
                                }
                            }
                        }
                    }
                    filterStoreCodeItems = resultList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterStoreCodeItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null){
                    filterStoreCodeItems = (List<StoreCodeItem>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    public interface ItemClickListener{
        void onClick(StoreCodeItem storeCodeItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitleSearchDept)
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(StoreCodeItem storeCodeItem, ItemClickListener itemClickListener) {
            tvTitle.setText(storeCodeItem.getSTORENAME());

            itemView.setOnClickListener(v-> itemClickListener.onClick(storeCodeItem));
        }
    }
}
