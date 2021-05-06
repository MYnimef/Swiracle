package com.mynimef.swiracle.network;

import android.os.Handler;
import android.os.Message;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService instance;
    Retrofit retrofit;
    PostApi postApi;
    ClothesApi clothesApi;
    ParsingApi parsingApi;

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    private NetworkService() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.postApi = retrofit.create(PostApi.class);
        this.clothesApi = retrofit.create(ClothesApi.class);
        this.parsingApi = retrofit.create(ParsingApi.class);
    }

    public void getPosts(Handler handler) {
        Call<List<PostServer>> call = postApi.getAll();
        call.enqueue(new Callback<List<PostServer>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostServer>> call,
                                   @NotNull Response<List<PostServer>> response) {
                Message msg = new Message();
                msg.obj = response.body();
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<List<PostServer>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void putPost(PostServer post, List<String> uriList) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (String uri : uriList) {
            File file = new File(uri);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            partList.add(MultipartBody.Part.createFormData("images",
                    file.getName(), requestFile));
        }

        postApi.putPost(post, partList).enqueue(new Callback<PostServer>() {
            @Override
            public void onResponse(@NotNull Call<PostServer> call,
                                   @NotNull Response<PostServer> response) {
            }

            @Override
            public void onFailure(@NotNull Call<PostServer> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getClothesParsing(String url, Handler handler) {
        Call<ClothesParsingInfo> call = parsingApi.
                getClothesElementParsing(url.replaceAll("/", "SWIRACLE"));
        call.enqueue(new Callback<ClothesParsingInfo>() {
            @Override
            public void onResponse(@NotNull Call<ClothesParsingInfo> call,
                                   @NotNull Response<ClothesParsingInfo> response) {
                Message msg = new Message();
                msg.obj = response.body();
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(@NotNull Call<ClothesParsingInfo> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}