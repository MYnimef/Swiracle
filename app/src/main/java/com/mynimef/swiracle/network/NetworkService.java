package com.mynimef.swiracle.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.PostDetails;
import com.mynimef.swiracle.models.Login;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.models.UserDetails;
import com.mynimef.swiracle.network.api.AuthApi;
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
    private final AuthApi authApi;
    private final PostApi postApi;
    private final ClothesApi clothesApi;
    private final ParsingApi parsingApi;

    private String token = "";

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

        this.authApi = retrofit.create(AuthApi.class);
        this.postApi = retrofit.create(PostApi.class);
        this.clothesApi = retrofit.create(ClothesApi.class);
        this.parsingApi = retrofit.create(ParsingApi.class);
    }

    public void signUp() {
        authApi.signUp(new UserDetails("MYnimef", "Ivan2000",
                "ivan.markov.2013@gmail.com", "Ivan", "Markov"))
                .enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(@NotNull Call<Response<String>> call,
                                           @NotNull Response<Response<String>> response) {

                    }

                    @Override
                    public void onFailure(@NotNull Call<Response<String>> call,
                                          @NotNull Throwable t) {

                    }
                });
    }

    public void signIn() {
        authApi.signIn(new Login("MYnimef", "Ivan2000"))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NotNull Call<User> call,
                                           @NotNull Response<User> response) {
                        if (response.isSuccessful()) {
                            token = "Bearer " + response.body().getToken();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<User> call,
                                          @NotNull Throwable t) {

                    }
                });
    }

    public void getPosts() {
        postApi.getAll(token).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NotNull Call<List<Post>> call,
                                   @NotNull Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    Repository.getInstance().insertAll(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Post>> call,
                                  @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getPostDetails(String id, Handler handler) {
        postApi.getPostDetails(token, id).enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(@NotNull Call<PostDetails> call,
                                   @NotNull Response<PostDetails> response) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("details", response.body());
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(@NotNull Call<PostDetails> call, @NotNull Throwable t) {
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

        postApi.putPost(token, post, partList).enqueue(new Callback<PostServer>() {
            @Override
            public void onResponse(@NotNull Call<PostServer> call,
                                   @NotNull Response<PostServer> response) {
                if (response.isSuccessful()) {
                    getPosts();
                }
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