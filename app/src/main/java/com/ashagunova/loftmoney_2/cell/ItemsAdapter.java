package com.ashagunova.loftmoney_2.cell;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ashagunova.loftmoney_2.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter <ItemsAdapter.ItemViewHolder> {

    private List<Item> itemList = new ArrayList<>();

    public MoneyCellAdapterClick moneyCellAdapterClick;

    public void setData(List<Item> items) {
        itemList.clear();
        itemList.addAll(items);

        notifyDataSetChanged();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void updateItem(Item item) {
        int itemPosition = itemList.indexOf(item);
        itemList.set(itemPosition, item);
        notifyItemChanged(itemPosition);
    }

    public void deleteSelectedItems() {
        List<Item> selectedItems = new ArrayList<>();
        for (Item moneyItem : itemList) {
            if (moneyItem.isSelected()) {
                selectedItems.add(moneyItem);
            }
        }

        itemList.removeAll(selectedItems);
        notifyDataSetChanged();
    }

    public void setMoneyCellAdapterClick(MoneyCellAdapterClick moneyCellAdapterClick) {
        this.moneyCellAdapterClick = moneyCellAdapterClick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.cell_money, parent, false),
                moneyCellAdapterClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ItemViewHolder holder, int position) {
        holder.bind(itemList.get(position));

    }

//    public void addItem(Item item) {
//        itemList.add(item);
//        notifyDataSetChanged();
//    }
//
//    public void clearItems() {
//        itemList.clear();
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private MoneyCellAdapterClick moneyCellAdapterClick;

        public ItemViewHolder(@NonNull View itemsView, final MoneyCellAdapterClick moneyCellAdapterClick) {
            super(itemsView);

            this.moneyCellAdapterClick = moneyCellAdapterClick;

            name = itemsView.findViewById(R.id.moneyCellTitleView);
            price = itemsView.findViewById(R.id.moneyCellValueView);

        }

        public void bind(Item item) {


            name.setText(item.getName());
            price.setText(new SpannableString(item.getPrice() + "\u2880"));

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),
                    item.isSelected() ? R.color.cellSelectionColor : android.R.color.white));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moneyCellAdapterClick != null) {
                        moneyCellAdapterClick.onCellClick(item);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (moneyCellAdapterClick != null) {
                        moneyCellAdapterClick.onLongCellClick(item);
                    }

                    return true;
                }
            });

 /*           if (item.getCurrentPosition() == 0) {
                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.expenseColor));
            } else if (item.getCurrentPosition() == 1) {
                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.incomeColor));

           } */
        }
    }
}
