package com.mynimef.swiracle.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostApi {
    @GET("/posts")
    Call<List<PostServer>> getAll();

    @POST("/posts")
    Call<PostServer> putPost(@Body PostServer postInfo);

    @DELETE("/posts/{id}")
    Call<PostServer> deletePost(@Path("id") String id);

    @PUT("/posts/{id}")
    Call<PostServer> updatePost(@Path("id") String id, @Body PostServer postInfo);
}