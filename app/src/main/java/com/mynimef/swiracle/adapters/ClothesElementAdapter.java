package com.mynimef.swiracle.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mynimef.swiracle.R;
import com.mynimef.swiracle.fragments.setClothesElements.SetClothesElementsFragment;
import com.mynimef.swiracle.network.models.ClothesParsingInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClothesElementAdapter extends RecyclerView.Adapter<ClothesElementAdapter.ClothesView> {
    private final List<ClothesParsingInfo> clothesList;
    private final SetClothesElementsFragment fragment;

    public ClothesElementAdapter(List<ClothesParsingInfo> clothesList,
                                 SetClothesElementsFragment fragment) {
        this.clothesList = clothesList;
        this.fragment = fragment;
    }

    @NotNull
    @Override
    public ClothesView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_clothes_element, viewGroup, false);
        return new ClothesView(view);
    }

    @Override
    public void onBindViewHolder(ClothesView clothesView, final int position) {
        ClothesParsingInfo element = clothesList.get(position);
        clothesView.getBrand().setText(element.getBrand());
        clothesView.getDescription().setText(element.getDescription());
        clothesView.getPrice().setText(element.getPrice());
    }

    @Override
    public void onViewRecycled(@NonNull ClothesView holder) { super.onViewRecycled(holder); }

    @Override
    public int getItemCount() { return clothesList.size(); }

    class ClothesView extends RecyclerView.ViewHolder {
        private final TextView brand;
        private final TextView description;
        private final TextView price;

        public ClothesView(View view) {
            super(view);
            this.brand = view.findViewById(R.id.elementBrand);
            this.description = view.findViewById(R.id.elementDescription);
            this.price = view.findViewById(R.id.elementPrice);
            ImageButton deleteButton = view.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(v -> fragment.removeClothes(getBindingAdapterPosition()));
        }

        public TextView getBrand() { return brand; }
        public TextView getDescription() { return description; }
        public TextView getPrice() { return price; }
    }
}