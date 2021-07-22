package com.ashagunova.loftmoney_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final String [] titles;
    private final String [] types = {"expense", "income"};

    MainPagerAdapter() {
        super(getSupportFragmentManager());
        titles = getResources().getStringArray(R.array.main_pager_titles);
    }

    @Override

    public Fragment getItem(int position) {
        if (position == getCount() - 1)
            return new BalanceFragment();

        Bundle args = new Bundle();
        args.putString("type", types[position]);

        final ItemsFragment itemsFragment = new ItemsFragment();
        itemsFragment.setArguments(args);
        return itemsFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
    @Override
    public int getCount() {
        return titles.length;
    }
}
