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
import com.hudipo.pum_indomaret.model.trxtype.TrxItem;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchTrxAdapter extends RecyclerView.Adapter<SearchTrxAdapter.ViewHolder> implements Filterable {

    private TrxTypeResponse trxTypeResponse;
    private ItemClickListener itemClickListener;
    private List<TrxItem> filterTrxItem;

    public SearchTrxAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTrxTypeResponse(TrxTypeResponse trxTypeResponse) {
        this.trxTypeResponse = trxTypeResponse;
        this.filterTrxItem = trxTypeResponse.getTrx();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_dept, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(filterTrxItem.get(position));
    }

    @Override
    public int getItemCount() {
        if (filterTrxItem == null){
            return 0;
        }
        return filterTrxItem.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSearch = constraint.toString().toLowerCase().trim();
                if (charSearch.isEmpty()){
                    filterTrxItem = trxTypeResponse.getTrx();
                }else {
                    List<TrxItem> resultList = new ArrayList<>();
                    if (trxTypeResponse.getTrx() != null){
                        for (TrxItem row: trxTypeResponse.getTrx()){
                            if (row.getDescription() != null){
                                if (row.getDescription().toLowerCase().trim().contains(charSearch)){
                                    resultList.add(row);
                                }
                            }
                        }
                    }
                    filterTrxItem = resultList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterTrxItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null){
                    filterTrxItem = (List<TrxItem>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
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
