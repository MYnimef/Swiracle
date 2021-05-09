package com.mynimef.swiracle.network.api;

import com.mynimef.swiracle.models.Login;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.models.UserDetails;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/auth/signup")
    Call<Response<String>> signUp(@Body UserDetails userDetails);

    @POST("/auth/signin")
    Call<User> signIn(@Body Login login);
}