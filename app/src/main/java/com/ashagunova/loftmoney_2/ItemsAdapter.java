package com.ashagunova.loftmoney_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter <ItemsAdapter.ItemViewHolder> {

    private List<Item> itemLis = new ArrayList<>();

    public void setData(List<Item>items){

        itemLis.clear();
        itemLis.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.cell_money, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemViewHolder holder, int position) {
        holder.bind(itemLis.get(position));

    }

    @Override
    public int getItemCount() {
        return itemLis.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView valueTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.moneyCellTitleView);
            valueTextView = itemView.findViewById(R.id.moneyCellValueView);
                    }

        public void bind(Item item) {
            titleTextView.setText(item.getTitle());
            valueTextView.setText(item.getValue());

        }
    }

}
