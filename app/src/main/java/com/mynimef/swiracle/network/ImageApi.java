package com.mynimef.swiracle.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageApi {
    @Multipart
    @POST("/images")
    Call<ResponseBody> putImages(@Part List<MultipartBody.Part> images);
}