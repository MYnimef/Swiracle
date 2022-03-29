package com.mynimef.swiracle.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mynimef.swiracle.models.Post;
import com.mynimef.swiracle.models.PostImage;
import com.mynimef.swiracle.models.PostInfo;
import com.mynimef.swiracle.models.User;
import com.mynimef.swiracle.repository.dao.ImagesDao;
import com.mynimef.swiracle.repository.dao.PostDao;
import com.mynimef.swiracle.repository.dao.UserDao;

import java.util.List;

@androidx.room.Database(
        entities = {User.class, PostInfo.class, PostImage.class},
        version = 1,
        exportSchema = false
)
abstract class LocalDatabase extends RoomDatabase {
    protected abstract UserDao userDao();
    protected abstract PostDao postDao();
    protected abstract ImagesDao imagesDao();

    static LocalDatabase init(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                LocalDatabase.class,
                "swiracle_database"
        )
                .fallbackToDestructiveMigration()
                .build();
    }

    final LiveData<User> getUser(String username) {
        return userDao().getUser(username);
    }

    final String getToken(String username) {
        return userDao().getToken(username);
    }

    final LiveData<List<Post>> getRecommendationList() {
        return postDao().getAllPosts();
    }

    final void insertUser(User user) {
        new Thread(() -> userDao().insertUser(user))
                .start();
    }

    final LiveData<Post> getPost(String username) {
        return postDao().getPost(username);
    }

    final void updatePostInfo(PostInfo postInfo) {
        new Thread(() -> postDao().updatePostInfo(postInfo))
                .start();
    }

    final void insertAllPosts(List<Post> postList) {
        new Thread(() -> {
            postDao().deleteAllPosts();
            imagesDao().deleteAllImages();

            for (Post post : postList) {
                postDao().insertPostInfo(post.getPostInfo());
                for (PostImage image : post.getImages()) {
                    imagesDao().insertPostImage(image);
                }
            }
        })
                .start();
    }
}