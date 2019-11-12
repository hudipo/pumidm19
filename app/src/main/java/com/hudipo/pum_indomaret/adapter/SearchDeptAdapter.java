package com.hudipo.pum_indomaret.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.departement.DepartmentItem;
import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDeptAdapter extends RecyclerView.Adapter<SearchDeptAdapter.ViewHolder>{

    private DepartmentResponse departmentResponse;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener{
        void onClick(DepartmentItem departmentItem);
    }

    public SearchDeptAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setDepartmentResponse(DepartmentResponse departmentResponse) {
        this.departmentResponse = departmentResponse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_dept, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(departmentResponse.getDepartment().get(position));
    }

    @Override
    public int getItemCount() {
        return departmentResponse.getDepartment().size();
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
