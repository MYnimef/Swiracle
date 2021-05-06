package com.mynimef.swiracle.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClothesApi {
    @GET("/clothes")
    Call<List<ClothesElementServer>> getAll();

    @POST("/clothes")
    Call<ClothesElementServer> putClothesElement(@Body ClothesElementServer element);

    @DELETE("/clothes/{id}")
    Call<ClothesElementServer> deleteClothesElement(@Path("id") String id);

    @PUT("/clothes/{id}")
    Call<ClothesElementServer> updateClothesElement(@Path("id") String id, @Body ClothesElementServer element);
}
