package com.mynimef.swiracle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mynimef.swiracle.R;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        PostAdapter adapter = new PostAdapter(root.getContext(), makePost());
        ListView lv = (ListView) root.findViewById(R.id.list_view);
        lv.setAdapter(adapter);

        return root;
    }

    Post[] makePost() {
        Post[] post = new Post[4];
        int[] link = {R.drawable.picture1, R.drawable.picture2, R.drawable.picture3, R.drawable.picture4};
        for (int i = 0; i < 4; i++) {
            post[i] = new Post();
            post[i].title = "Fashion";
            post[i].description = "";
            post[i].likes = 0;
            post[i].comments = 0;
            post[i].imageResource = link[i];
        }
        return post;
    }
}