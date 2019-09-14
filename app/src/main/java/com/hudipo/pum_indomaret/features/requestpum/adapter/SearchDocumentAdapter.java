package com.hudipo.pum_indomaret.features.requestpum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.model.DocumentDetailRequestModel;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDocumentAdapter extends RecyclerView.Adapter<SearchDocumentAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private ArrayList<DocumentDetailRequestModel> documentDetailRequestModels;

    public SearchDocumentAdapter(ArrayList<DocumentDetailRequestModel> documentDetailRequestModel,ItemClickListener itemClickListener){
        this.documentDetailRequestModels = documentDetailRequestModel;
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(String docNum);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request_search_document, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(documentDetailRequestModels.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return documentDetailRequestModels.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDocNum)
        TextView tvDocNume;
        @BindView(R.id.tvDocDate)
        TextView tvDocDate;
        @BindView(R.id.tvAmount)
        TextView tvAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindItem(DocumentDetailRequestModel documentDetailRequestModel, ItemClickListener itemClickListener) {
            tvDocNume.setText(documentDetailRequestModel.getStringDocNumber());
            tvDocDate.setText(documentDetailRequestModel.getStringDocDate());
            String string = "Rp "+ documentDetailRequestModel.getLongAmount();
            tvAmount.setText(string);

            itemView.setOnClickListener(view -> itemClickListener.onItemClick(documentDetailRequestModel.getStringDocNumber()));
        }
    }


}
