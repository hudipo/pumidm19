package com.hudipo.pum_indomaret.features.approval.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.detail.ApprovalDetailHistoryActivity;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.model.approval.history.ApprovalHistoryListModel;
import com.hudipo.pum_indomaret.utils.Global;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PUM_STATUS;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PUM_TRX_ID;

public class ApprovalHistoryAdapter extends RecyclerView.Adapter<ApprovalHistoryAdapter.ApprovalViewHolder> implements Filterable {

    private List<ApprovalHistoryListModel> listApproval;
    private List<ApprovalHistoryListModel> listApprovalFiltered;

    public ApprovalHistoryAdapter(List<ApprovalHistoryListModel> listApproval) {
        this.listApproval = listApproval;
        this.listApprovalFiltered = listApproval;
    }

    @NonNull
    @Override
    public ApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approval_history, parent, false);
        return new ApprovalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalViewHolder holder, int position) {
        holder.bind(listApprovalFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return listApprovalFiltered.size();
    }

    class ApprovalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvPumNumber)
        TextView tvPumNumber;
        @BindView(R.id.tvPumRequester)
        TextView tvPumRequester;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.view)
        View rightView;

        public ApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ApprovalHistoryListModel approvalModel){
            tvPumNumber.setText(approvalModel.getTrxNum());
            tvPumRequester.setText(approvalModel.getUsername());
            String amount = String.format("%s %s", itemView.getContext().getString(R.string.rp),
                    Global.priceFormatter(String.valueOf(approvalModel.getAmount())));
            tvAmount.setText(amount);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), ApprovalDetailHistoryActivity.class);
                intent.putExtra(EXTRA_PUM_TRX_ID, approvalModel.getPumTrxId());
                intent.putExtra(EXTRA_PUM_STATUS, approvalModel.getStatus());
                itemView.getContext().startActivity(intent);
            });

            if(approvalModel.getStatus().equals("APP"))
            {
                rightView.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_approve));
            }else {
                rightView.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_reject));
            }
        }
    }

    public void updateListApproval(List<ApprovalHistoryListModel> listApprovalUpdate) {
        listApproval.clear();
        listApproval.addAll(listApprovalUpdate);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listApprovalFiltered = listApproval;
                } else {
                    List<ApprovalHistoryListModel> filteredList = new ArrayList<>();
                    for (ApprovalHistoryListModel row : listApproval) {
                        boolean isMatched = false;

                        if(row.getTrxNum()!=null){
                            if(row.getTrxNum().toLowerCase().contains(charString.toLowerCase())){
                                isMatched=true;
                            }
                        }
                        if(row.getUsername()!=null){
                            if(row.getUsername().toLowerCase().contains(charString.toLowerCase())){
                                isMatched=true;
                            }
                        }
                        if(isMatched) filteredList.add(row);
                    }

                    listApprovalFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listApprovalFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listApprovalFiltered = (ArrayList<ApprovalHistoryListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
