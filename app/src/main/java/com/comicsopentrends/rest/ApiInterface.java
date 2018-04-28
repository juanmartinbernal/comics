package com.comicsopentrends.rest;

import com.comicsopentrends.model.CharacterResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

public interface ApiInterface {

    @GET("characters")
    Call<CharacterResponse> getComics(@Query("offset") int offset);

    @GET("characters/{id}")
    Call<CharacterResponse> getComicDetails(@Path("id") int id);

    @GET("characters")
    Call<CharacterResponse> searchComic(@Query("nameStartsWith") String query);
}

