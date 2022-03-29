package com.mynimef.swiracle.repository.api;

import com.mynimef.swiracle.models.Post;
import com.mynimef.swiracle.models.PostDetails;
import com.mynimef.swiracle.models.PostServer;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostApi {
    @GET("/posts")
    Call<List<Post>> getAll();

    @GET("/posts/auth")
    Call<List<Post>> getAllAuth(@Header("Authorization") String token);

    @GET("/posts/details/{id}")
    Call<PostDetails> getPostDetails(@Path("id") String id);

    @Multipart
    @POST("/posts/add")
    Call<Boolean> putPost(@Header("Authorization") String token,
                             @Part("info") PostServer postInfo,
                             @Part List<MultipartBody.Part> images);

    @GET("/posts/like/{id}")
    Call<Boolean> likePost(@Header("Authorization") String token,
                               @Path("id") String id);

    @DELETE("/posts/{id}")
    Call<PostServer> deletePost(@Header("Authorization") String token, @Path("id") String id);
}