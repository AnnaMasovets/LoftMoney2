package com.ashagunova.loftmoney_2;

//public class MainViewModel extends ViewModel {
//    private CompositeDisposable compositeDisposable = new CompositeDisposable();
//    public MutableLiveData<List<Item>> itemsList = new MutableLiveData<>();
//    public MutableLiveData<String> messageString = new MutableLiveData<>("");
//    public MutableLiveData<Integer> messageIntent = new MutableLiveData<>(-1);
//    public SwipeRefreshLayout swipeRefreshLayout;
//    public ItemsAdapter itemsAdapter = new ItemsAdapter();
//
//
//
//    @Override
//    protected void onCleared() {
//        compositeDisposable.dispose();
//        super.onCleared();
//    }
//
//    public void loadItems(MoneyApi moneyApi) {
//        compositeDisposable.add(moneyApi.getMoneyItems("income")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(moneyResponse -> {
//                    if (moneyResponse.getStatus().equals("success")) {
//                        itemsAdapter.clearItems();
//                        swipeRefreshLayout.setRefreshing(false);
//
//                        List<Item> items = new ArrayList<>();
//
//                        for (MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()) {
//                            items.add(Item.getInstance(moneyRemoteItem));
//                        }
//                        itemsList.postValue(items);
//                    }else {
//                        messageIntent.postValue(R.string.connection_lost);
//                    }
//                }, throwable -> {
//                    messageString.postValue(throwable.getLocalizedMessage());
//                    swipeRefreshLayout.setRefreshing(false);
//                }));
//
//    }
//}
