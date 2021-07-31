package com.ashagunova.loftmoney_2;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ashagunova.loftmoney_2.MoneyApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Item>> itemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageIntent = new MutableLiveData<>(-1);


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void  loadItems(MoneyApi moneyApi) {
        compositeDisposable.add(moneyApi.getMoneyItems("income")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    if (moneyResponse.getStatus().equals("success")) {
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
                }));

    }
}
