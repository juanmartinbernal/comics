package com.comicsopentrends.rest;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class AuthInterceptor implements Interceptor {

    private final String tokenClans;

    AuthInterceptor(String tokenClans) {
        this.tokenClans = tokenClans;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + tokenClans)
                .build();
        return chain.proceed(newRequest);
    }
}