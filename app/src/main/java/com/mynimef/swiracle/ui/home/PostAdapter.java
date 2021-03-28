package com.mynimef.swiracle.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.mynimef.swiracle.R;

public class PostAdapter extends ArrayAdapter<Post> {
    public PostAdapter(Context context, Post[] post) {
        super(context, R.layout.adapter_post, post);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Post post = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_post, null);
        }

        Button title = (Button) convertView.findViewById(R.id.button);
        title.setText(post.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ImageView pic = convertView.findViewById(R.id.imageView);
        pic.setImageResource(post.imageResource);

        Button description = (Button) convertView.findViewById(R.id.description);
        description.setText(post.description);

        return convertView;
    }
}