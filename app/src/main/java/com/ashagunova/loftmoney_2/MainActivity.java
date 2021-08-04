package com.ashagunova.loftmoney_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.ashagunova.loftmoney_2.cell.ItemsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsView;

    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    private BudgetFragment budgetFragment;

    public static final String TOKEN = "token";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);
        final ViewPager viewPager = findViewById(R.id.viewpager);

        viewPager.setAdapter(new BudgetFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        FloatingActionButton floatingActionButton = findViewById(R.id.add_new_expense);
        floatingActionButton.setOnClickListener(v -> {
            final int activeFragmentIndex = viewPager.getCurrentItem();
            Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
            activeFragment.startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class),
            BudgetFragment.REQUEST_CODE);
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.Expenses);
        tabLayout.getTabAt(1).setText(R.string.Income);

        //configureViewModel();

    }


//    protected void onResume() {
//        super.onResume();
//        budgetFragment.loadItems(((LoftApp) getApplication()).moneyApi);
//    }



    static class BudgetFragmentAdapter extends FragmentPagerAdapter{


        public BudgetFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position < 2) {
                return BudgetFragment.newInstance(position);
            } else
                return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }



//    private void configureViewModel() {
////        budgetFragment = new ViewModelProvider(this).get(BudgetFragment.class);
//        budgetFragment.itemsList.observe(this, items -> {
//            itemsAdapter.setData(items);
//        });
//
//        budgetFragment.messageString.observe(this, message -> {
//            if (!message.equals("")) {
//                showToast(message);
//            }
//        });
//        budgetFragment.messageIntent.observe(this, message ->{
//            if (message > 0) {
//                showToast(getString(message));
//            }
//        });
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//    }

}