package com.ashagunova.loftmoney_2;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

public class ItemsAdapter extends RecyclerView.Adapter <ItemsAdapter.ItemViewHolder> {

    private List<Item> itemLis = new ArrayList<>();

    public void setData(List<Item> items) {

        itemLis.clear();
        itemLis.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cell_money;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemViewHolder holder, int position) {
        holder.bind(itemLis.get(position));

    }

    public void addItem(Item item) {
        itemLis.add(item);
        notifyDataSetChanged();
    }

    public void clearItems() {
        itemLis.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemLis.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.moneyCellTitleView);
            price = itemView.findViewById(R.id.moneyCellValueView);

        }

        public void bind(Item item) {


//            name.setText(item.getName());
//            price.setText(new SpannableString(item.getPrice() + "\u2880"));
//
//            if (item.getCurrentPosition() == 0) {
//                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.expenseColor));
//            } else if (item.getCurrentPosition() == 1) {
//                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.incomeColor));
//
//            }
        }
    }
}
