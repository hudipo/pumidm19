package com.hudipo.pum_indomaret.features.response.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.createresponse.CreateResponseActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsehistory.ResponseHistoryActivity;
import com.hudipo.pum_indomaret.model.ResponseModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder>{
    private ArrayList<ResponseModel> listResponse;
    private Context context;

    public ResponseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.bindItem(listResponse.get(position));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setData(ArrayList<ResponseModel> listResponse){
        this.listResponse = listResponse;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.tvTrxNumber)
        TextView tvTrxNumber;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(ResponseModel itemResponse){
        }

        @OnClick(R.id.btnFlag)
        void btnFlag(){
            context.startActivity(new Intent(context, CreateResponseActivity.class));
            Animatoo.animateSlideLeft(context);
        }



        @OnClick(R.id.btnEye)
        void btnEye(){
            context.startActivity(new Intent(context, ResponseHistoryActivity.class));
            Animatoo.animateSlideLeft(context);
        }
    }
}
