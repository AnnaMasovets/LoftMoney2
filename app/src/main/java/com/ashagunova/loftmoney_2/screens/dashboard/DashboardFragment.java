package com.ashagunova.loftmoney_2.screens.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashagunova.loftmoney_2.screens.budget.BudgetEditListener;
import com.ashagunova.loftmoney_2.screens.budget.BudgetFragment;
import com.ashagunova.loftmoney_2.R;
import com.ashagunova.loftmoney_2.screens.dashboard.adapter.FragmentAdapter;
import com.ashagunova.loftmoney_2.screens.balance.BalanceFragment;
import com.ashagunova.loftmoney_2.screens.dashboard.adapter.FragmentItem;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements EditModeListener{

    private Toolbar toolbarView;
    private ImageView backButtonView;
    private ImageView dashboardActionView;
    private TabLayout tabContainerView;
    private TextView dashboardTitleView;
    private ViewPager containerView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<FragmentItem> fragments = new ArrayList<>();
        fragments.add(new FragmentItem(new BudgetFragment(), getString(R.string.title_expense)));
        fragments.add(new FragmentItem(new BudgetFragment(), getString(R.string.title_incomes)));
        fragments.add(new FragmentItem(new BalanceFragment(), getString(R.string.title_balance)));

//        ViewPager containerView = view.findViewById(R.id.containerView);
//        TabLayout tabs = view.findViewById(R.id.tabs);
//
//        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragments, getChildFragmentManager(), 0);
//        containerView.setAdapter(fragmentAdapter);
//        containerView.setOffscreenPageLimit(3);
//        tabs.setupWithViewPager(containerView);

        toolbarView = view.findViewById(R.id.toolbarView);
        backButtonView = view.findViewById(R.id.backButtonView);
        dashboardActionView = view.findViewById(R.id.dashboardActionView);
        dashboardTitleView = view.findViewById(R.id.dashboardTitleView);

        backButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEditStatus();
            }
        });

        dashboardActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.delete_items_title))
                        .setMessage(getString(R.string.delete_items_message))
                        .setNegativeButton(R.string.action_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                clearSelectedItems();
                            }
                        })
                        .show();
            }
        });

        containerView = view.findViewById(R.id.containerView);
        containerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearEditStatus();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabContainerView = view.findViewById(R.id.tabs);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragments, getChildFragmentManager(), 0);
        containerView.setAdapter(fragmentAdapter);
        containerView.setOffscreenPageLimit(3);
        tabContainerView.setupWithViewPager(containerView);
    }



    @Override
    public void onEditModeChanged(boolean status) {
        toolbarView.setBackgroundColor(ContextCompat.getColor(getContext(),
                status ? R.color.selectionColorPrimary : R.color.colorPrimary));
        dashboardActionView.setVisibility(status ? View.VISIBLE : View.GONE);
        backButtonView.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        tabContainerView.setBackgroundColor(ContextCompat.getColor(getContext(),
                status ? R.color.selectionColorPrimary : R.color.colorPrimary));

        Window window = getActivity().getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),
                status ? R.color.selectionColorPrimary : R.color.colorPrimary));
    }

    @Override
    public void onCounterChanged(int newCount) {
        if (newCount >= 0) {
            dashboardTitleView.setText(getString(R.string.selected) + " " + newCount);
        } else {
            dashboardTitleView.setText(getString(R.string.activity_main_toolbar_title));
        }
    }

    private void clearEditStatus() {
        Fragment fragment = getChildFragmentManager().getFragments().get(containerView.getCurrentItem());
        if (fragment instanceof BudgetEditListener) {
            ((BudgetEditListener) fragment).onClearEdit();
        }
    }

    private void clearSelectedItems() {
        Fragment fragment = getChildFragmentManager().getFragments().get(containerView.getCurrentItem());
        if (fragment instanceof BudgetEditListener) {
            ((BudgetEditListener) fragment).onClearSelectedClick();
        }
    }
}
