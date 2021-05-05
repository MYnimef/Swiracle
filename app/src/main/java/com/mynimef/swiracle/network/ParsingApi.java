package com.mynimef.swiracle.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParsingApi {
    @GET("/parsing/{url}")
    Call<ClothesParsingInfo> getClothesElementParsing(@Path("url") String url);
}