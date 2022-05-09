package com.mynimef.swiracle.repository.api;

import com.mynimef.swiracle.models.ProfileView;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserAPI {
    @GET("/user/follow/{id}")
    Call<Boolean> followUser(@Header("Authorization") String token,
                             @Path("id") String id);

    @GET("/user/info/{id}")
    Call<ProfileView> getProfileViewAuth(@Header("Authorization") String token,
                                         @Path("id") String id);

    @GET("/user/info/unauth/{id}")
    Call<ProfileView> getProfileView(@Path("id") String id);
}