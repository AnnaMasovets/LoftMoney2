package com.ashagunova.loftmoney_2.remote;


import com.ashagunova.loftmoney_2.remote.MoneyRemoteItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoneyResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<MoneyRemoteItem> moneyItemsList;

    public String getStatus() {
        return status;
    }

    public List<MoneyRemoteItem> getMoneyItemsList() {
        return moneyItemsList;
    }
}
