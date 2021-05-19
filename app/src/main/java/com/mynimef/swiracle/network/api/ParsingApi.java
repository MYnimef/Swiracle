package com.mynimef.swiracle.network.api;

import com.mynimef.swiracle.models.ClothesParsingInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ParsingApi {
    @GET("/parsing/{url}")
    Call<ClothesParsingInfo> getClothesElementParsing(@Header("Authorization") String token,
                                                      @Path("url") String url);
}