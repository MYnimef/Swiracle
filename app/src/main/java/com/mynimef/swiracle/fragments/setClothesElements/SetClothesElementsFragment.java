package com.mynimef.swiracle.fragments.setClothesElements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.AppLogic.ClothesElement;
import com.mynimef.swiracle.AppLogic.ParseClothes;
import com.mynimef.swiracle.Interfaces.ISetClothesElements;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.ClothesElementAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetClothesElementsFragment extends Fragment implements ISetClothesElements {
    private ArrayList<ClothesElement> clothes;
    private Fragment fragment;
    private RecyclerView rv;
    private ClothesElementAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragment = this;
        this.clothes = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set_clothes_elements, container, false);

        EditText link = root.findViewById(R.id.addLink);
        Button addElement = root.findViewById(R.id.addElement);
        addElement.setOnClickListener(v -> {
            String url = link.getText().toString();
            if (!url.equals("")) {
                new ParseClothes(url, fragment);
                link.setText("");
            }
        });

        rv = root.findViewById(R.id.setInfoElementsList);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        adapter = new ClothesElementAdapter(clothes, this);
        rv.setAdapter(adapter);

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
        clothes.add(0, new ClothesElement(name, description, price, url));
        adapter.notifyItemInserted(0);
        rv.smoothScrollToPosition(0);
    }

    public void removeClothes(int pos) {
        clothes.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public ArrayList<ClothesElement> getClothes() {
        return clothes;
    }
}