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
import com.hudipo.pum_indomaret.model.response.TrxTypeItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTrxResponseAdapter extends RecyclerView.Adapter<SearchTrxResponseAdapter.ViewHolder> implements Filterable {

    public interface ItemClickListener{
        void onClick(TrxTypeItem trxItem);
    }

    private ArrayList<TrxTypeItem> trxTypeItems;
    private ArrayList<TrxTypeItem> filterTrxTypeItems;
    private ItemClickListener itemClickListener;

    public SearchTrxResponseAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTrxTypeItems(ArrayList<TrxTypeItem> trxTypeItems) {
        this.trxTypeItems = trxTypeItems;
        this.filterTrxTypeItems = trxTypeItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(filterTrxTypeItems.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return filterTrxTypeItems.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSearch = constraint.toString().toLowerCase().trim();
                if (charSearch.isEmpty()){
                    filterTrxTypeItems = trxTypeItems;
                }else {
                    ArrayList<TrxTypeItem> resultList = new ArrayList<>();
                    if (trxTypeItems != null){
                        for (TrxTypeItem row : trxTypeItems){
                            if (row.getDESCRIPTION() != null){
                                if (row.getDESCRIPTION().toLowerCase().trim().contains(charSearch)){
                                    resultList.add(row);
                                }
                            }
                        }
                    }
                    filterTrxTypeItems = resultList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterTrxTypeItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null){
                    filterTrxTypeItems = (ArrayList<TrxTypeItem>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitleSearchDept)
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(TrxTypeItem trxTypeItem, ItemClickListener itemClickListener) {
            tvTitle.setText(trxTypeItem.getNAME());

            itemView.setOnClickListener(v -> itemClickListener.onClick(trxTypeItem));
        }
    }
}
