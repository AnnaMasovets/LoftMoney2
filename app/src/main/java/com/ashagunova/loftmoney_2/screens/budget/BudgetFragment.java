package com.ashagunova.loftmoney_2.screens.budget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashagunova.loftmoney_2.AddMoneyActivity;
import com.ashagunova.loftmoney_2.LoftApp;
import com.ashagunova.loftmoney_2.MainViewModel;
import com.ashagunova.loftmoney_2.R;
import com.ashagunova.loftmoney_2.cell.Item;
import com.ashagunova.loftmoney_2.cell.ItemsAdapter;
import com.ashagunova.loftmoney_2.cell.MoneyCellAdapterClick;
import com.ashagunova.loftmoney_2.remote.MoneyApi;
import com.ashagunova.loftmoney_2.screens.dashboard.EditModeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BudgetFragment extends Fragment implements BudgetEditListener {

    private ItemsAdapter itemsAdapter = new ItemsAdapter();
    private BudgetViewModel budgetViewModel;
    private FloatingActionButton btnAdd;

    public SwipeRefreshLayout swipeRefreshLayout;

//    public static final int REQUEST_CODE = 0;
//    public static final String ARGS_CURRENT_POSITION = "args_current_position";
//
//    private RecyclerView recyclerView;
//
//    private List<Item> items = new ArrayList<>();
//    private int currentPosition;
//
//    private SharedPreferences pref;
//
//    public MutableLiveData<String> messageString = new MutableLiveData<>("");
//    public MutableLiveData<Integer> messageIntent = new MutableLiveData<>(-1);
//
//    private MoneyApi moneyApi;
//
//    private static final String TYPE = "fragmentType";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemsAdapter.setMoneyCellAdapterClick(new MoneyCellAdapterClick() {
            @Override
            public void onCellClick(Item moneyItem) {
                if (budgetViewModel.isEditMode.getValue()) {
                    moneyItem.setSelected(!moneyItem.isSelected());
                    itemsAdapter.updateItem(moneyItem);
                    checkSelectedCount();
                }
            }

            @Override
            public void onLongCellClick(Item moneyItem) {
                if (!budgetViewModel.isEditMode.getValue())
                    moneyItem.setSelected(true);
                itemsAdapter.updateItem(moneyItem);
                budgetViewModel.isEditMode.postValue(true);
                checkSelectedCount();
            }
        });

//        if (getArguments() != null) {
//            currentPosition = getArguments().getInt(ARGS_CURRENT_POSITION);
//        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        return inflater.inflate(R.layout.budget_fragment, container, false);

//        View view = inflater.inflate(R.layout.budget_fragment, null);
//        recyclerView = view.findViewById(R.id.recycler);
//
//
//        //swipeRefreshLayout.setOnRefreshListener(() -> loadItems(moneyApi));
//
//        recyclerView.setAdapter(itemsAdapter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
//
//        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdd = view.findViewById(R.id.add_new_expense);

        configureViews(view);
        configureViewModel();

//        viewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
//        pref = getActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.apply();
//
//        viewModel.loadItems(((LoftApp) getActivity().getApplication()).moneyApi, pref);
//
//        viewModel.itemsList.observe(requireActivity(), items -> {
//            itemsAdapter.setData(items);
//        });
    }

    @Override
    public void onResume() {
        super.onResume();

        budgetViewModel.loadItems(
                ((LoftApp) getActivity().getApplication()).moneyApi,
                getActivity().getSharedPreferences(getString(R.string.app_name), 0)
        );
    }

    @Override
    public void onClearEdit() {
        budgetViewModel.isEditMode.postValue(false);
        budgetViewModel.selectedCounter.postValue(-1);

        for (Item moneyItem : itemsAdapter.getItemList()) {
            if (moneyItem.isSelected()) {
                moneyItem.setSelected(false);
                itemsAdapter.updateItem(moneyItem);
            }
        }
    }

    @Override
    public void onClearSelectedClick() {
        budgetViewModel.isEditMode.postValue(false);
        budgetViewModel.selectedCounter.postValue(-1);
        itemsAdapter.deleteSelectedItems();
    }

    private void configureViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false));

        FloatingActionButton addNewIncomeView = view.findViewById(R.id.add_new_expense);
        addNewIncomeView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddMoneyActivity.class);
            startActivity(intent);
        });
    }

    private void checkSelectedCount() {
        int selectedItemsCount = 0;
        for (Item moneyItem : itemsAdapter.getItemList()) {
            if (moneyItem.isSelected()) {
                selectedItemsCount++;
            }
        }

        budgetViewModel.selectedCounter.postValue(selectedItemsCount);
    }

    private void configureViewModel() {
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.itemsList.observe(getViewLifecycleOwner(), moneyItems -> {
            itemsAdapter.setData(moneyItems);
        });

        budgetViewModel.isEditMode.observe(getViewLifecycleOwner(), isEditMode -> {
            btnAdd.setVisibility(isEditMode ? View.GONE : View.VISIBLE);

            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onEditModeChanged(isEditMode);
            }
        });

        budgetViewModel.selectedCounter.observe(getViewLifecycleOwner(), newCount -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onCounterChanged(newCount);
            }
        });

        budgetViewModel.messageString.observe(getViewLifecycleOwner(), message -> {
            if (!message.equals("")) {
                showToast(message);
            }
        });

        budgetViewModel.messageInt.observe(getViewLifecycleOwner(), message -> {
            if (message > 0) {
                showToast(getString(message));
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }



//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String nameAdd = data.getStringExtra("name");
//        String priceAdd = data.getStringExtra("price");
//
//        items.add(new Item(nameAdd, priceAdd));
//        itemsAdapter.setData(items);
//
//    }

//    public static BudgetFragment newInstance(int currentPosition) {
//        BudgetFragment fragment = new BudgetFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARGS_CURRENT_POSITION, currentPosition);
//        fragment.setArguments(args);
//        return fragment;
//
//
//    }













 /*   private void configureViewModel() {
        budgetFragment = new ViewModelProvider(this).get(BudgetFragment.class);
        budgetFragment.itemsList.observe(this, items -> {
            itemsAdapter.setData(items);
        });

        budgetFragment.messageString.observe(this, message -> {
            if (!message.equals("")) {
                showToast(message);
            }
        });
        budgetFragment.messageIntent.observe(this, message ->{
            if (message > 0) {
                showToast(getString(message));
            }
        });
    }
 */

}
