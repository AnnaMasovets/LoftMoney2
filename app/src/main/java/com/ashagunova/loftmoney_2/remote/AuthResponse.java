package com.ashagunova.loftmoney_2.remote;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("id")
    private String userId;

    @SerializedName("auth_token")
    private String authToken;

    @SerializedName("status")
    private String stratus;

    public String getUserId() {
        return userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getStratus() {
        return stratus;
    }
}
