package com.hudipo.pum_indomaret.features.approval.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.detail.ApprovalDetailActivity;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PUM_TRX_ID;

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ApprovalViewHolder> {
    public interface OnItemClickCallback {
        void onItemChecked(ApprovalListModel approvalModel, boolean checked);
    }

    private List<ApprovalListModel> listApproval;
    private int isAllChecked = 0; //0 default; 1 semua uncheck; 2 semua cheked
    private OnItemClickCallback onItemClickCallback;

    public ApprovalAdapter(List<ApprovalListModel> listApproval, OnItemClickCallback onItemClickCallback) {
        this.listApproval = listApproval;
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approval, parent, false);
        return new ApprovalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalViewHolder holder, int position) {
        holder.bind(listApproval.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listApproval.size();
    }

    class ApprovalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvPumNumber)
        TextView tvPumNumber;
        @BindView(R.id.tvPumRequester)
        TextView tvPumRequester;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.cbApproval)
        CheckBox cbApproval;

        public ApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ApprovalListModel approvalModel, int position){
            tvPumNumber.setText(approvalModel.getTRXNUM());
            tvPumRequester.setText(approvalModel.getNAME());
            tvAmount.setText(String.valueOf(approvalModel.getAMOUNT()));

            if(isAllChecked==2){
                cbApproval.setChecked(true);
            }else if(isAllChecked==1){
                cbApproval.setChecked(false);
            }else {
                cbApproval.setChecked(approvalModel.isChecked());
            }

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), ApprovalDetailActivity.class);
                intent.putExtra(EXTRA_PUM_TRX_ID, approvalModel.getPUMTRXID());
                itemView.getContext().startActivity(intent);
            });

            cbApproval.setOnClickListener(
                    view -> {
                        onItemClickCallback.onItemChecked(approvalModel, cbApproval.isChecked());
                        listApproval.get(position).setChecked(cbApproval.isChecked());
                        isAllChecked = 0;
                    }
            );
        }
    }

    public void setAllChecked(boolean allChecked) {
        if(allChecked) isAllChecked = 2;
        else isAllChecked = 1;
        for(int i=0;i<listApproval.size();i++){
            listApproval.get(i).setChecked(allChecked);
        }
        notifyDataSetChanged();
    }

    public void updateListApproval(List<ApprovalListModel> listApprovalUpdate) {
        listApproval.clear();
        listApproval.addAll(listApprovalUpdate);
        notifyDataSetChanged();
    }
}
