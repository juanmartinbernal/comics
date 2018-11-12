package com.comicsopentrends.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asus on 20/10/2017.
 */

public class CharacterResponse {

    @SerializedName("data")
    @Expose
    public Data data;

}
