package com.mynimef.swiracle.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

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
            public void onResponse(Call<List<PostServer>> call, Response<List<PostServer>> response) {
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

    public void putPost(PostServer post) {
        postApi.putPost(post).enqueue(new Callback<PostServer>() {
            @Override
            public void onResponse(Call<PostServer> call, Response<PostServer> response) {
                //getPosts(handler);
            }

            @Override
            public void onFailure(Call<PostServer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void deletePost(PostServer employee, Handler handler) {
        postApi.deletePost(employee.getId()).enqueue(new Callback<PostServer>() {
            @Override
            public void onResponse(Call<PostServer> call, Response<PostServer> response) {
                getPosts(handler);
            }

            @Override
            public void onFailure(Call<PostServer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updatePost(PostServer employee, Handler handler){
        postApi.updatePost(employee.getId(), employee).enqueue(new Callback<PostServer>() {
            @Override
            public void onResponse(Call<PostServer> call, Response<PostServer> response) {
                getPosts(handler);
            }

            @Override
            public void onFailure(Call<PostServer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void putClothesElement(ClothesElementServer element) {
        clothesApi.putClothesElement(element).enqueue(new Callback<ClothesElementServer>() {
            @Override
            public void onResponse(Call<ClothesElementServer> call, Response<ClothesElementServer> response) {
                //getPosts(handler);
            }

            @Override
            public void onFailure(Call<ClothesElementServer> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getClothesParsing(String url) {
        Call<ClothesParsingInfo> call = parsingApi.getClothesElementParsing(url.replaceAll("/", "SWIRACLE"));
        call.enqueue(new Callback<ClothesParsingInfo>() {
            @Override
            public void onResponse(Call<ClothesParsingInfo> call, Response<ClothesParsingInfo> response) {
                Log.d("parsing", response.body().getBrand());
                Log.d("parsing", response.body().getDescription());
                Log.d("parsing", response.body().getPrice());
            }

            @Override
            public void onFailure(Call<ClothesParsingInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}