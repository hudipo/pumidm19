package com.hudipo.pum_indomaret.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.departement.DepartmentItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDeptAdapter extends RecyclerView.Adapter<SearchDeptAdapter.ViewHolder> implements Filterable {

    private List<DepartmentItem> listDepartment;
    private List<DepartmentItem> filterDepartment;
    private ItemClickListener itemClickListener;

    public void setListDepartment(ArrayList<DepartmentItem> listDepartment) {
        this.listDepartment = listDepartment;
        this.filterDepartment = listDepartment;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onClick(DepartmentItem departmentItem);
    }

    public SearchDeptAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_dept, parent, false));
    }

    @Override
    public int getItemCount() {
        if (filterDepartment == null){
            return 0;
        }
        return filterDepartment.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(filterDepartment.get(position));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSearch = constraint.toString().toLowerCase().trim();
                if (charSearch.isEmpty()){
                    filterDepartment = listDepartment;
                }else {
                    List<DepartmentItem> filteredList = new ArrayList<>();
                    if (listDepartment != null){
                        for (DepartmentItem row : listDepartment){
                            if (row.getDescription().toLowerCase().contains(charSearch)){
                                filteredList.add(row);
                            }
                        }
                        filterDepartment.clear();
                        filterDepartment.addAll(filteredList);
                        Log.d("coba", "size filter: "+filteredList.size());
                    }
                }
                Log.d("coba", "size: "+filterDepartment.size());
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterDepartment;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterDepartment = (ArrayList<DepartmentItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitleSearchDept)
        TextView tvTitleSearchDept;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindItem(DepartmentItem item){
            tvTitleSearchDept.setText(item.getDescription());

            itemView.setOnClickListener(v -> itemClickListener.onClick(item));
        }
    }
}
