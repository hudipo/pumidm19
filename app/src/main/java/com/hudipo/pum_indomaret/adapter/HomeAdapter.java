package com.hudipo.pum_indomaret.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.home.HomeModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private ItemClickListener itemClickListener;
    private ArrayList<HomeModel> listDataHome;
    static public int request=0;
    static public int approval=0;
    static public int status=1;
    static public int response=1;
    static public int settings=2;
    static public int reports=2;

    public HomeAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setListDataHome(ArrayList<HomeModel> listDataHome) {
        this.listDataHome = listDataHome;
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.listDataHome.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.listDataHome.size());
    }

    public interface ItemClickListener{
        void onItemClick(HomeModel homeItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listDataHome.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listDataHome.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivHome)
        ImageView ivHome;
        @BindView(R.id.tvTitleHome)
        TextView tvTitleHome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(HomeModel item, ItemClickListener itemClickListener){
            Glide.with(itemView).load(item.getImage()).into(ivHome);
            tvTitleHome.setText(item.getTitle());

            itemView.setOnClickListener(view -> itemClickListener.onItemClick(item));
        }
    }
}
