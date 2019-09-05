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
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalDetailHistoryActivity;
import com.hudipo.pum_indomaret.model.approval.ApprovalModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovalHistoryAdapter extends RecyclerView.Adapter<ApprovalHistoryAdapter.ApprovalViewHolder> {
    public interface OnItemClickCallback {
        void onItemChecked(ApprovalModel approvalModel, boolean checked);
    }

    private List<ApprovalModel> listApproval;
    private boolean isAllChecked = false;
    private ApprovalAdapter.OnItemClickCallback onItemClickCallback;

    public ApprovalHistoryAdapter(List<ApprovalModel> listApproval, ApprovalAdapter.OnItemClickCallback onItemClickCallback) {
        this.listApproval = listApproval;
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approval_history, parent, false);
        return new ApprovalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalViewHolder holder, int position) {
        holder.bind(listApproval.get(position));
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
        @BindView(R.id.view)
        View rightView;

        public ApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ApprovalModel approvalModel){
            tvPumNumber.setText(approvalModel.getPumNumber());
            tvPumRequester.setText(approvalModel.getPumRequester());
            tvAmount.setText(String.valueOf(approvalModel.getAmount()));
            cbApproval.setChecked(isAllChecked);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), ApprovalDetailHistoryActivity.class);
                itemView.getContext().startActivity(intent);
            });

            cbApproval.setOnClickListener(view -> onItemClickCallback.onItemChecked(approvalModel, cbApproval.isChecked()));

            if(approvalModel.getType()==1)//1 for approve 2 for rejected sementara
            {
                rightView.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_approve));
            }else {
                rightView.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_reject));
            }
        }
    }

    public void setAllChecked(boolean allChecked) {
        isAllChecked = allChecked;
        notifyDataSetChanged();
    }
}
