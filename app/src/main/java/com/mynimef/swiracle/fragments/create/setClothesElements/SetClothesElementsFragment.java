package com.mynimef.swiracle.fragments.create.setClothesElements;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import com.mynimef.swiracle.Interfaces.ISetClothesElements;
import com.mynimef.swiracle.R;
import com.mynimef.swiracle.adapters.ClothesElementAdapter;
import com.mynimef.swiracle.models.ClothesParsingInfo;
import com.mynimef.swiracle.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetClothesElementsFragment extends Fragment implements ISetClothesElements {
    private List<ClothesParsingInfo> infoList;
    private RecyclerView rv;
    private ClothesElementAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.infoList = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_set_clothes_elements, container, false);

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                addClothes((ClothesParsingInfo) msg.obj);
            }
        };

        EditText link = root.findViewById(R.id.addLink);
        Button addElement = root.findViewById(R.id.addElement);
        addElement.setOnClickListener(v -> {
            String url = link.getText().toString();
            if (!url.equals("")) {
                NetworkService.getInstance().getClothesParsing(url, handler);
                link.setText("");
            }
        });

        rv = root.findViewById(R.id.setInfoElementsList);
        rv.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        adapter = new ClothesElementAdapter(infoList, this);
        rv.setAdapter(adapter);

        return root;
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(),
                "Data retrieval error",
                Toast.LENGTH_SHORT).show();
    }

    public void addClothes(ClothesParsingInfo info) {
        for (ClothesParsingInfo added : infoList) {
            if (added.getUrl().equals(info.getUrl())) {
                Toast.makeText(getContext(),
                        "You have already added this item to list!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
        infoList.add(0, info);
        adapter.notifyItemInserted(0);
        rv.smoothScrollToPosition(0);
    }

    public void removeClothes(int pos) {
        infoList.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public List<ClothesParsingInfo> getInfoList() { return infoList; }
}