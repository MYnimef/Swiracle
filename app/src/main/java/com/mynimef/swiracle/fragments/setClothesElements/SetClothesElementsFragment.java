package com.mynimef.swiracle.fragments.setClothesElements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mynimef.swiracle.AppLogic.ClothesElement;
import com.mynimef.swiracle.AppLogic.ParseClothes;
import com.mynimef.swiracle.Interfaces.ISetClothesElements;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.ClothesElementAdapter;

import java.util.ArrayList;

public class SetClothesElementsFragment extends Fragment implements ISetClothesElements {
    private ArrayList<ClothesElement> clothes;
    private Fragment fragment;
    private ClothesElementAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set_clothes_elements, container, false);
        this.fragment = this;
        this.clothes = new ArrayList<>();

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
    public void showError() {
        Toast.makeText(getContext(),
                "Data retrieval error",
                Toast.LENGTH_SHORT).show();
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
