package com.comicsopentrends.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Juan Martin Bernal on 21/10/2017.
 */

public class Event {

    @SerializedName("items")
    @Expose
    public ArrayList<ItemEvent> items;
}
