package com.ashagunova.loftmoney_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsView;

    private ItemsAdapter itemsAdapter = new ItemsAdapter();

    private MainViewModel mainViewModel;

    public static final String TOKEN = "token";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);
        final ViewPager viewPager = findViewById(R.id.viewpager);

        viewPager.setAdapter(new BudgetFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

//        FloatingActionButton floatingActionButton = findViewById(R.id.add_new_expense);
//        floatingActionButton.setOnClickListener(v -> {
//            final int activeFragmentIndex = viewPager.getCurrentItem();
//            Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
//            activeFragment.startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class),
//            BudgetFragment.REQUEST_CODE);
//        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.Expenses);
        tabLayout.getTabAt(1).setText(R.string.Income);

        configureViewModel();

    }


    protected void onResume() {
        super.onResume();
        mainViewModel.loadItems(((LoftApp) getApplication()).moneyApi);
    }



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



    private void configureViewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.itemsList.observe(this, items -> {
            itemsAdapter.setData(items);
        });

        mainViewModel.messageString.observe(this, message -> {
            if (!message.equals("")) {
                showToast(message);
            }
        });
        mainViewModel.messageIntent.observe(this, message ->{
            if (message > 0) {
                showToast(getString(message));
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}