package com.ashagunova.loftmoney_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import com.ashagunova.loftmoney_2.cell.Item;
import com.ashagunova.loftmoney_2.cell.ItemsAdapter;
import com.ashagunova.loftmoney_2.remote.MoneyApi;
import com.ashagunova.loftmoney_2.remote.MoneyRemoteItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    public static final int REQUEST_CODE = 0;
    public static final String ARGS_CURRENT_POSITION = "args_current_position";

    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    public ItemsAdapter itemsAdapter = new ItemsAdapter();
    private List<Item> items = new ArrayList<>();
    private int currentPosition;
    public SwipeRefreshLayout swipeRefreshLayout;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Item>> itemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageIntent = new MutableLiveData<>(-1);



    private MoneyApi moneyApi;

    private static final String TYPE = "fragmentType";

    public BudgetFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        compositeDisposable.dispose();
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


//        swipeRefreshLayout.setOnRefreshListener(() -> loadItems(moneyApi));

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

    public void loadItems(MoneyApi moneyApi) {
        compositeDisposable.add(moneyApi.getMoneyItems("income")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    if (moneyResponse.getStatus().equals("success")) {
                        itemsAdapter.clearItems();
                        swipeRefreshLayout.setRefreshing(false);

                        List<Item> items = new ArrayList<>();

                        for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()) {
                            items.add(Item.getInstance(moneyRemoteItem));
                        }
                        itemsList.postValue(items);
                    }else {
                        messageIntent.postValue(R.string.connection_lost);
                    }
                }, throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                    swipeRefreshLayout.setRefreshing(false);
                }));

    }


}
