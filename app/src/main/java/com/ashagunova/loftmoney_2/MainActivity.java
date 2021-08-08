package com.ashagunova.loftmoney_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashagunova.loftmoney_2.screens.dashboard.DashboardFragment;

public class MainActivity extends AppCompatActivity {

    private RecyclerView itemsView;
//
//    private ItemsAdapter itemsAdapter = new ItemsAdapter();
//
//    private BudgetFragment budgetFragment;
//
//    public static final String TOKEN = "token";
//
    private FrameLayout containerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerView = findViewById(R.id.containerView);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, new DashboardFragment())
                .commitNow();

//        TabLayout tabLayout = findViewById(R.id.tabs);
//        final ViewPager viewPager = findViewById(R.id.viewPager);
//
//        viewPager.setAdapter(new BudgetFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
//
//        FloatingActionButton floatingActionButton = findViewById(R.id.add_new_expense);
//        floatingActionButton.setOnClickListener(v -> {
//            final int activeFragmentIndex = viewPager.getCurrentItem();
//            Fragment activeFragment = getSupportFragmentManager().getFragments().get(activeFragmentIndex);
//            activeFragment.startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class),
//            BudgetFragment.REQUEST_CODE);
//        });
//
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).setText(R.string.Expenses);
//        tabLayout.getTabAt(1).setText(R.string.Income);

        //configureViewModel();
    }



//    static class BudgetFragmentAdapter extends FragmentPagerAdapter{
//
//
//        public BudgetFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
//            super(fm, behavior);
//        }
//
//        @NonNull
//        @Override
//        public Fragment getItem(int position) {
//            if (position < 2) {
//                return BudgetFragment.newInstance(position);
//            } else
//                return null;
//        }
//
//        @Override
//        public int getCount() {
//            return 2;
//        }
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//    }

}