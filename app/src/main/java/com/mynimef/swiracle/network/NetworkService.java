package com.mynimef.swiracle.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.network.api.ClothesApi;
import com.mynimef.swiracle.network.api.ParsingApi;
import com.mynimef.swiracle.network.api.PostApi;
import com.mynimef.swiracle.models.ClothesParsingInfo;
import com.mynimef.swiracle.models.PostServer;

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
    private final PostApi postApi;
    private final ClothesApi clothesApi;
    private final ParsingApi parsingApi;

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    private NetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.postApi = retrofit.create(PostApi.class);
        this.clothesApi = retrofit.create(ClothesApi.class);
        this.parsingApi = retrofit.create(ParsingApi.class);
    }

    public void getPosts() {
        postApi.getAll().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NotNull Call<List<Post>> call,
                                   @NotNull Response<List<Post>> response) {
                Repository.getInstance().insertAll(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<List<Post>> call,
                                  @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void putPost(PostServer post, List<String> uriList, Handler handler) {
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
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", "positive");
                msg.setData(bundle);
                handler.sendMessage(msg);
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