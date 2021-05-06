package com.mynimef.swiracle.network;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostApi {
    @GET("/posts")
    Call<List<PostServer>> getAll();

    @Multipart
    @POST("/posts")
    Call<PostServer> putPost(@Part("info") PostServer postInfo,
                             @Part List<MultipartBody.Part> images);

    @DELETE("/posts/{id}")
    Call<PostServer> deletePost(@Path("id") String id);

    @PUT("/posts/{id}")
    Call<PostServer> updatePost(@Path("id") String id, @Body PostServer postInfo);
}