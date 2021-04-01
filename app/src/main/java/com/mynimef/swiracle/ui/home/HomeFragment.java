package com.mynimef.swiracle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.PostList;
import com.mynimef.swiracle.R;

public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        PostList list = new PostList();
        list.setSampleList();
        PostAdapter adapter = new PostAdapter(root.getContext(), list);
        ListView lv = (ListView) root.findViewById(R.id.list_view);
        lv.setAdapter(adapter);

        return root;
    }
}