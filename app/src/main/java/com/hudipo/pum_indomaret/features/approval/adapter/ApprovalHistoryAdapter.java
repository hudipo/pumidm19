package com.hudipo.pum_indomaret.features.approval.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalDetailHistoryActivity;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_APPROVAL_HISTORY_TYPE;

public class ApprovalHistoryAdapter extends RecyclerView.Adapter<ApprovalHistoryAdapter.ApprovalViewHolder> {

    private List<ApprovalListModel> listApproval;

    public ApprovalHistoryAdapter(List<ApprovalListModel> listApproval) {
        this.listApproval = listApproval;
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
        @BindView(R.id.view)
        View rightView;

        public ApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ApprovalListModel approvalModel){
//            tvPumNumber.setText(approvalModel.getPumNumber());
//            tvPumRequester.setText(approvalModel.getPumRequester());
//            tvAmount.setText(String.valueOf(approvalModel.getAmount()));
//
//            itemView.setOnClickListener(view -> {
//                Intent intent = new Intent(itemView.getContext(), ApprovalDetailHistoryActivity.class);
//                intent.putExtra(EXTRA_APPROVAL_HISTORY_TYPE, approvalModel.getType());
//                itemView.getContext().startActivity(intent);
//            });
//
//            if(approvalModel.getType()==1)//1 for approve 2 for rejected sementara
//            {
//                rightView.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_approve));
//            }else {
//                rightView.setBackground(itemView.getContext().getDrawable(R.drawable.gradient_approval_right_reject));
//            }
        }
    }
}
