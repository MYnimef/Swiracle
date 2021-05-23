package com.mynimef.swiracle.network;

import android.os.Handler;
import android.os.Message;

import com.mynimef.swiracle.database.Post;
import com.mynimef.swiracle.logic.Repository;
import com.mynimef.swiracle.models.DateModel;
import com.mynimef.swiracle.models.PostDetails;
import com.mynimef.swiracle.models.Login;
import com.mynimef.swiracle.models.ProfileView;
import com.mynimef.swiracle.models.SignUpServer;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.network.api.AuthApi;
import com.mynimef.swiracle.network.api.ParsingApi;
import com.mynimef.swiracle.network.api.PostApi;
import com.mynimef.swiracle.models.ClothesParsingInfo;
import com.mynimef.swiracle.models.PostServer;
import com.mynimef.swiracle.network.api.UserApi;

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
    private final ParsingApi parsingApi;
    private final UserApi userApi;
    private final Repository repository;

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
        this.parsingApi = retrofit.create(ParsingApi.class);
        this.userApi = retrofit.create(UserApi.class);
        this.repository = Repository.getInstance();
    }

    public void signUp(String username, String password, String email,
                       String firstName, String lastName,
                       int gender, DateModel birthday,
                       Handler signUpHandler) {

        SignUpServer signUpServer = new SignUpServer(username, password, email,
                firstName, lastName, gender, birthday);

        Message msg = new Message();
        authApi.signUp(signUpServer).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NotNull Call<Boolean> call,
                                   @NotNull Response<Boolean> response) {
                if (response.body() != null && response.body()) {
                    msg.arg1 = 0; // success
                } else {
                    msg.arg1 = 1; // failure
                }
                signUpHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(@NotNull Call<Boolean> call, @NotNull Throwable t) {
                msg.arg1 = -1; // no connection
                signUpHandler.sendMessage(msg);
            }
        });
    }

    public void signIn(String username, String password, Handler handler) {
        Message msg = new Message();
        authApi.signIn(new Login(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.body() != null) {
                    repository.setSignedIn(1);
                    repository.insertUser(response.body());
                    msg.arg1 = 0;
                } else {
                    msg.arg1 = 1;
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                msg.arg1 = -1;
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
                    repository.insertAllPosts(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Post>> call,
                                  @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getPostsAuth(String token) {
        postApi.getAllAuth(token).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NotNull Call<List<Post>> call,
                                   @NotNull Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    repository.insertAllPosts(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Post>> call,
                                  @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void likePost(String id, String token) {
        postApi.likePost(token, id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NotNull Call<Boolean> call,
                                   @NotNull Response<Boolean> response) {

            }

            @Override
            public void onFailure(@NotNull Call<Boolean> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getPostDetails(String id, Handler handler) {
        Message message = new Message();
        postApi.getPostDetails(id).enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(@NotNull Call<PostDetails> call,
                                   @NotNull Response<PostDetails> response) {
                if (response.body() != null) {
                    message.arg1 = 0; // success;
                    message.obj = response.body();
                } else {
                    message.arg1 = 1; // failure
                }
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(@NotNull Call<PostDetails> call, @NotNull Throwable t) {
                message.arg1 = -1;
                handler.sendMessage(message); // no connection
            }
        });
    }

    public void putPost(PostServer post, List<String> uriList, String token) {
        List<MultipartBody.Part> partList = new ArrayList<>();
        for (String uri : uriList) {
            File file = new File(uri);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            partList.add(MultipartBody.Part.createFormData("images",
                    file.getName(), requestFile));
        }

        postApi.putPost(token,
                post, partList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NotNull Call<Boolean> call,
                                   @NotNull Response<Boolean> response) {
                if (response.isSuccessful()) {
                    getPosts();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Boolean> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getClothesParsing(String url, Handler handler, String token) {
        parsingApi.getClothesElementParsing(token, url.replaceAll("/", "SWIRACLE"))
                .enqueue(new Callback<ClothesParsingInfo>() {
            @Override
            public void onResponse(@NotNull Call<ClothesParsingInfo> call,
                                   @NotNull Response<ClothesParsingInfo> response) {
                Message msg = new Message();
                if (response.body() != null) {
                    msg.arg1 = 0; // success
                    msg.obj = response.body();
                } else {
                    msg.arg1 = 1; // failure
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(@NotNull Call<ClothesParsingInfo> call, @NotNull Throwable t) {
                Message msg = new Message();
                msg.arg1 = -1; // no connection
                handler.sendMessage(msg);
            }
        });
    }

    public void followUser(String token, String id) {
        userApi.followUser(token, id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NotNull Call<Boolean> call,
                                   @NotNull Response<Boolean> response) {

            }

            @Override
            public void onFailure(@NotNull Call<Boolean> call, @NotNull Throwable t) {

            }
        });
    }

    public void getProfileView(String id, Handler handler) {
        Message message = new Message();
        userApi.getProfileView(id).enqueue(new Callback<ProfileView>() {
            @Override
            public void onResponse(@NotNull Call<ProfileView> call,
                                   @NotNull Response<ProfileView> response) {
                if (response.body() != null) {
                    message.arg1 = 0; // success;
                    message.obj = response.body();
                } else {
                    message.arg1 = 1; // failure
                }
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(@NotNull Call<ProfileView> call, @NotNull Throwable t) {
                message.arg1 = -1;
                handler.sendMessage(message); // no connection
            }
        });
    }
}