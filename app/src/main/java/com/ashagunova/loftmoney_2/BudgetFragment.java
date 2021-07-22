package com.ashagunova.loftmoney_2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.ashagunova.loftmoney_2.R.id.pages_view;
import static com.ashagunova.loftmoney_2.R.id.tabs_layout;

public class BudgetFragment extends AppCompatActivity {

    private RecyclerView itemsView;

    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    private TabLayout tabLayout;
    private ViewPager ViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_fragment);

        configureRecyclerView();

        generateMoney();

        tabLayout = findViewById(tabs_layout);
        ViewPager = findViewById(pages_view);

        ViewPager.setAdapter(new MainPagerAdapter());
        tabLayout.setupWithViewPager(ViewPager);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(pages_view, new ExpensesFragment())
                .commit();
    }


    private void generateMoney(){

        List<Item> items = new ArrayList<>();
        items.add(new Item("Зуюная щетка", "70р"));

        itemsAdapter.setData(items);
    }

    private void configureRecyclerView() {

        itemsView = findViewById(R.id.itemsView);
        itemsView.setAdapter(itemsAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);

        itemsView.setLayoutManager(layoutManager);
    }
}
