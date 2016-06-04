package com.codefororlando.streettrees.api.models;

import com.google.gson.annotations.SerializedName;

public class TreeJsonModel {
    @SerializedName("Order #")
    public String orderId;

    @SerializedName("Date")
    public String date;

    @SerializedName("Location")
    public String location;

    @SerializedName("Tree name")
    public String treeName;
}
