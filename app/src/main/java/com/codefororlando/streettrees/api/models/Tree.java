package com.codefororlando.streettrees.api.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Tree {
    private int order;

    private Date date;

    private LatLng location;

    @SerializedName("tree_name")
    private String treeName;

    private String savings;

    public Tree() {

    }

    public Tree(Date _date, double _lat, double _long, String _treeName) {
        setDate(_date);
        setLocation(_lat, _long);
        setTreeName(_treeName);
    }


    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setLocation(double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
