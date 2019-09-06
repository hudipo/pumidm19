package com.hudipo.pum_indomaret.features.response.subfeatures.responsecartfull.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hudipo.pum_indomaret.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseCartFullAdapter extends RecyclerView.Adapter<ResponseCartFullAdapter.ViewHolder>{
    private Context context;

    public ResponseCartFullAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_cart_partial, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btnRemove)
        void btnRemove(){
            Toast.makeText(context, "Remove", Toast.LENGTH_SHORT).show();
        }
    }
}
