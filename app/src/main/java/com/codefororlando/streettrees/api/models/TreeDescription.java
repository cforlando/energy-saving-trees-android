package com.codefororlando.streettrees.api.models;

import com.google.gson.annotations.SerializedName;

public class TreeDescription {

    private String name;

    private String description;

    @SerializedName("min_height_cm")
    private String minHeight;

    @SerializedName("max_height_cm")
    private String maxHeight;

    @SerializedName("min_width_cm")
    private String minWidth;

    @SerializedName("max_width_cm")
    private String maxWidth;

    private String shape;

    private String leaf;

    private String soil;

    private String moisture;

    @SerializedName("full_sun")
    private String sunlight;

    @SerializedName("partial_sun")
    private String partialSunlight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(String minHeight) {
        this.minHeight = minHeight;
    }

    public String getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(String minWidth) {
        this.minWidth = minWidth;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getSunlight() {
        return sunlight;
    }

    public void setSunlight(String sunlight) {
        this.sunlight = sunlight;
    }

    public String getMaxHeight() {
        return maxHeight;
    }

    public String getMaxWidth() {
        return maxWidth;
    }
}
