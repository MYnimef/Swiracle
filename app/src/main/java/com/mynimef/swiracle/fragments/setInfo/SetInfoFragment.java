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

import com.mynimef.swiracle.Interfaces.ISetInfo;
import com.mynimef.swiracle.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetInfoFragment extends Fragment implements ISetInfo {
    private EditText title;
    private EditText description;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_set_info, container, false);

        title = root.findViewById(R.id.addTitle);
        description = root.findViewById(R.id.addDescription);

        Button addElement = root.findViewById(R.id.addElement);
        addElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //ElementAdapter adapter = new ElementAdapter(root.getContext(), "hhh");
        ListView lv = (ListView) root.findViewById(R.id.setInfoElementsList);
        //lv.setAdapter(adapter);

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
}