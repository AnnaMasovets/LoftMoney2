package com.ashagunova.loftmoney_2;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 0;
    public static final String ARGS_CURRENT_POSITION = "args_current_position";

    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter = new ItemsAdapter();
    private List<Item> items = new ArrayList<>();
    private int currentPosition;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MoneyApi moneyApi;

    private static final String TYPE = "fragmentType";

    public BudgetFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARGS_CURRENT_POSITION);

        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.budget_fragment, null);
        recyclerView = view.findViewById(R.id.recycler);
        btnAdd = view.findViewById(R.id.add_new_expense);

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddItemActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadItems();
//            }
//        });

        recyclerView.setAdapter(itemsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String nameAdd = data.getStringExtra("name");
        String priceAdd = data.getStringExtra("price");

        items.add(new Item(nameAdd, priceAdd));
        itemsAdapter.setData(items);

    }

    public static BudgetFragment newInstance(int currentPosition) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_CURRENT_POSITION, currentPosition);
        fragment.setArguments(args);
        return fragment;


    }

//    public void loadItems() {
//        final String token = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(MainActivity.TOKEN, "");
//
//        Call<List<Item>> items = moneyApi.getMoneyItems(getArguments().getString(TYPE), token);
//        items.enqueue(new Callback<List<Item>>() {
//
//            @Override
//            public void onResponse(
//                    final Call<List<Item>> call, final Response<List<Item>> response
//            ) {
//                itemsAdapter.clearItems();
//                swipeRefreshLayout.setRefreshing(false);
//                List<Item> items = response.body();
//                for (Item item : items) {
//                    itemsAdapter.addItem(item);
//                }
//            }
//
//            @Override
//            public void onFailure(final Call<List<Item>> call, final Throwable t) {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }


}
