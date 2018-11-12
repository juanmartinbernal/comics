package com.comicsopentrends.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asus on 20/10/2017.
 */

public class Data {

    @SerializedName("offset")
    @Expose
    public int offset;
    @SerializedName("limit")
    @Expose
    public int limit;
    @SerializedName("total")
    @Expose
    public int total;
    @SerializedName("count")
    @Expose
    public int count;

    @SerializedName("results")
    @Expose
    public List<Character> results;
}
