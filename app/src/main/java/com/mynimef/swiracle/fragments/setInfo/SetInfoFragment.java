package com.mynimef.swiracle.fragments.setInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.AppLogic.ClothesElement;
import com.mynimef.swiracle.AppLogic.ParseClothes;
import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.ClothesElementAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetInfoFragment extends Fragment implements ISetInfo {
    private EditText title;
    private EditText description;
    private ArrayList<ClothesElement> clothes;
    private Fragment fragment;
    private ClothesElementAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.fragment = this;
        View root = inflater.inflate(R.layout.fragment_set_info, container, false);

        title = root.findViewById(R.id.addTitle);
        description = root.findViewById(R.id.addDescription);
        clothes = new ArrayList<>();

        EditText link = root.findViewById(R.id.addLink);
        Button addElement = root.findViewById(R.id.addElement);
        addElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = link.getText().toString();
                if (!url.equals("")) {
                    new ParseClothes(url, fragment);
                    link.setText("");
                }
            }
        });

        adapter = new ClothesElementAdapter(root.getContext(), clothes);
        ListView lv = (ListView) root.findViewById(R.id.setInfoElementsList);
        lv.setAdapter(adapter);

        return root;
    }

    @Override
    public String getTitle() {
        return title.getText().toString();
    }

    @Override
    public String getDescription() {
        return description.getText().toString();
    }

    @Override
    public void addClothes(String name, String description, String price, String url) {
        clothes.add(new ClothesElement(name, description, price, url));
        adapter.notifyDataSetChanged();
    }

    @Override
    public ArrayList<ClothesElement> getClothes() {
        return clothes;
    }
}