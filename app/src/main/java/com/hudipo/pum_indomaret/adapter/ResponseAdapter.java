package com.hudipo.pum_indomaret.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.activity.CreateResponseActivity;
import com.hudipo.pum_indomaret.features.response.activity.ResponseHistoryActivity;
import com.hudipo.pum_indomaret.model.response.DataResponseItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder>{
    private List<DataResponseItem> listResponse;
    private Context context;

    public ResponseAdapter(Context context) {
        this.context = context;
    }

    public void setListResponse(List<DataResponseItem> listResponse) {
        this.listResponse = listResponse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listResponse.get(position));
    }

    @Override
    public int getItemCount() {
        return listResponse.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvTrxNumber)
        TextView tvTrxNumber;

        @BindView(R.id.btnFlag)
        ImageButton btnFlag;

        @BindView(R.id.btnEye)
        ImageButton btnEye;

        private DataResponseItem itemResponse;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(DataResponseItem itemResponse){
            tvStatus.setText(itemResponse.getRESPSTATUS());
            tvTrxNumber.setText(itemResponse.getTRXNUM());
            if (tvStatus.getText().toString().matches("N")){
                tvStatus.setText(R.string.new_text);
                btnEye.setClickable(false);
                btnFlag.setClickable(true);
                btnFlag.getBackground().setAlpha(255);
                btnEye.getBackground().setAlpha(99);

            }else if (tvStatus.getText().toString().matches("F")){
                tvStatus.setText(R.string.full_text);
                btnFlag.setClickable(false);
                btnFlag.getBackground().setAlpha(99);

            }else if (tvStatus.getText().toString().matches("P")){
                tvStatus.setText(R.string.partial_text);

            }else if (tvStatus.getText().toString().matches("A")){
                tvStatus.setText(R.string.approved_text);
                btnFlag.setClickable(false);
                btnFlag.getBackground().setAlpha(99);

            }else if (tvStatus.getText().toString().matches("I")){
                tvStatus.setText(R.string.invoice_text);
                btnFlag.setClickable(false);
                btnFlag.getBackground().setAlpha(99);

            }

            this.itemResponse = itemResponse;
        }

        @OnClick(R.id.btnFlag)
        void btnFlag(){
            Intent intent = new Intent(context, CreateResponseActivity.class);
            intent.putExtra(CreateResponseActivity.EXTRA_DATA_RESPONSE, itemResponse);
            context.startActivity(intent);
        }

        @OnClick(R.id.btnEye)
        void btnEye(){
            Intent intent = new Intent(context, ResponseHistoryActivity.class);
            intent.putExtra(ResponseHistoryActivity.EXTRA_DATA_RESPONSE, itemResponse);
            context.startActivity(intent);
        }
    }
}
