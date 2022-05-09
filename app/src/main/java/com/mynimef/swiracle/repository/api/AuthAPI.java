package com.mynimef.swiracle.repository.api;

import com.mynimef.swiracle.models.SignInRequest;
import com.mynimef.swiracle.models.SignInCallback;
import com.mynimef.swiracle.models.SignUpRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("/auth/signup")
    Call<SignInCallback> signUp(@Body SignUpRequest signUpRequest);

    @POST("/auth/signin")
    Call<SignInCallback> signIn(@Body SignInRequest signInRequest);
}