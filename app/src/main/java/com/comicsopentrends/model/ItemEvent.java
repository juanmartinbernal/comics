package com.comicsopentrends.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Asus on 21/10/2017.
 */

public class ItemEvent {

    @SerializedName("resourceURI")
    @Expose
    public String resourceURI;

    @SerializedName("name")
    @Expose
    public String name;

}
