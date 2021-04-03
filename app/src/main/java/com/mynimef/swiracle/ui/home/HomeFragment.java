package com.mynimef.swiracle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.Singleton;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Singleton list = Singleton.getInstance();
        PostAdapter adapter = new PostAdapter(root.getContext(), list.getRecommendationList());
        ListView lv = (ListView) root.findViewById(R.id.list_view);
        lv.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }
}