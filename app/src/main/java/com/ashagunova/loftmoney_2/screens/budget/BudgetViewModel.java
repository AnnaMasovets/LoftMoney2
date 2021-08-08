package com.ashagunova.loftmoney_2.screens.budget;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ashagunova.loftmoney_2.LoftApp;
import com.ashagunova.loftmoney_2.cell.Item;
import com.ashagunova.loftmoney_2.remote.MoneyApi;
import com.ashagunova.loftmoney_2.remote.MoneyRemoteItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BudgetViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Item>> itemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isEditMode = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectedCounter = new MutableLiveData<>(-1);



    public void loadItems(MoneyApi moneyApi, SharedPreferences pref) {
        String authToken =  pref.getString(LoftApp.AUTH_KEY, "");

        compositeDisposable.add(moneyApi.getMoneyItems("income", authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyRemoteItems -> {
                    //itemsAdapter.clearItems();
                    //swipeRefreshLayout.setRefreshing(false);

                    List<Item> items = new ArrayList<>();

                    for (MoneyRemoteItem moneyRemoteItem : moneyRemoteItems) {
                        items.add(Item.getInstance(moneyRemoteItem));
                    }
                    itemsList.postValue(items);

                }, throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                    //swipeRefreshLayout.setRefreshing(false);
                }));

    }

}
