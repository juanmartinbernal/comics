package com.comicsopentrends.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
public class ItemComic {

    @SerializedName("resourceURI")
    @Expose
    public String resourceURI;

    @SerializedName("name")
    @Expose
    public String name;
}
