package com.mynimef.swiracle.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.DateModel;
import com.mynimef.swiracle.models.PostDetails;
import com.mynimef.swiracle.models.Login;
import com.mynimef.swiracle.models.SignUpServer;
import com.mynimef.swiracle.models.User;
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

    public void signUp(String username, String password, String email,
                       String firstName, String lastName,
                       int gender, DateModel birthday,
                       Handler signUpHandler) {
        authApi.signUp(new SignUpServer(username, password, email,
                firstName, lastName, gender, birthday))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NotNull Call<String> call,
                                           @NotNull Response<String> response) {
                        Message msg = new Message();
                        Bundle bundle = new Bundle();

                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().equals("User registered successfully!")) {
                                bundle.putByte("signup", (byte) 0);
                            } else {
                                bundle.putByte("signup", (byte) 1);
                            }
                        } else {
                            bundle.putByte("signup", (byte) 1);
                        }
                        msg.setData(bundle);
                        signUpHandler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(@NotNull Call<String> call,
                                          @NotNull Throwable t) {
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putByte("signup", (byte) -1);
                        msg.setData(bundle);
                        signUpHandler.sendMessage(msg);
                    }
                });
    }

    public void signIn(String username, String password, Handler handler) {
        authApi.signIn(new Login(username, password))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NotNull Call<User> call,
                                           @NotNull Response<User> response) {
                        Message msg = new Message();
                        Bundle bundle = new Bundle();

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Repository.getInstance().setToken(response.body().getToken());
                                Repository.getInstance().setSignedIn(1);
                                Repository.getInstance().insertUser(response.
                                        body().getUserDetails());
                                bundle.putByte("login", (byte) 0);
                            } else {
                                bundle.putByte("login", (byte) 1);
                            }
                        } else {
                            bundle.putByte("login", (byte) 1);
                        }
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(@NotNull Call<User> call,
                                          @NotNull Throwable t) {
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putByte("login", (byte) -1);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                });
    }

    public void getPosts() {
        postApi.getAll().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NotNull Call<List<Post>> call,
                                   @NotNull Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    Repository.getInstance().insertAllPosts(response.body());
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
        postApi.getPostDetails(id).enqueue(new Callback<PostDetails>() {
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

        postApi.putPost(Repository.getInstance().getToken(),
                post, partList).enqueue(new Callback<PostServer>() {
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