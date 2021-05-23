package com.mynimef.swiracle.network.api;

import com.mynimef.swiracle.models.ProfileView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/user/follow/{id}")
    Call<Boolean> followUser(@Header("Authorization") String token,
                             @Path("id") String id);

    @GET("/user/info/{id}")
    Call<ProfileView> getProfileView(@Path("id") String id);
}