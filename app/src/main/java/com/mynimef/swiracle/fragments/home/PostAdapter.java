package com.mynimef.swiracle.fragments.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.mynimef.swiracle.Interfaces.IFragmentConnector;
import com.mynimef.swiracle.AppLogic.Post;
import com.mynimef.swiracle.fragments.post.PostFragment;
import com.mynimef.swiracle.AppLogic.PostList;
import com.mynimef.swiracle.R;

public class PostAdapter extends ArrayAdapter<Post> {
    private int i = 0;

    public PostAdapter(Context context, PostList list) {
        super(context, R.layout.adapter_post, list.getList());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Post post = getItem(position);
        //IFragmentConnector fc = (IFragmentConnector) getContext();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_post, null);
        }

        Button title = (Button) convertView.findViewById(R.id.button);
        title.setText(post.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        ImageView pic = convertView.findViewById(R.id.imageView);
        pic.setImageBitmap(post.getImages().get(0));
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fc.replaceFragment(new PostFragment());
            }
        });

        Button description = (Button) convertView.findViewById(R.id.description);
        description.setText(post.getDescription());
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button likes = (Button) convertView.findViewById(R.id.likes);
        likes.setText(post.getLikes() + "");
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.increaseLikes();
                likes.setText(post.getLikes() + "");
            }
        });

        Button comments = (Button) convertView.findViewById(R.id.comments);
        comments.setText(post.getComments() + "");
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.increaseComments();
                comments.setText(post.getComments() + "");
            }
        });

        return convertView;
    }
}