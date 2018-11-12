package com.comicsopentrends.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 Created by Juan Martin Bernal on 20/10/2017.
 */
public class Character {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("thumbnail")
    @Expose
    public Thumbnail thumbnail;

    @SerializedName("comics")
    @Expose
    public Comic comic;

    @SerializedName("events")
    @Expose
    public Event event;

    @SerializedName("urls")
    @Expose
    public ArrayList<UrlCharacter> urls;
}
