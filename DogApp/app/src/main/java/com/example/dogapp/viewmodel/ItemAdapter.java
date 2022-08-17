package com.example.dogapp.viewmodel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<DogBreed> dogBreeds;

    public ItemAdapter(ArrayList<DogBreed> dataSet) {
        dogBreeds = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DogBreed dogBreed = dogBreeds.get(position);
        Picasso.get().load(dogBreed.getUrl()).into(holder.imgMain);
        holder.tvName.setText(dogBreed.getName());
        holder.tvBredFor.setText(dogBreed.getBredFor());
        holder.tvDetailName.setText(dogBreed.getName());
        holder.tvDetailOrigin.setText(dogBreed.getOrigin());
        holder.tvDetailLifeSpan.setText(dogBreed.getLifeSpan());
        holder.tvDetailBredFor.setText(dogBreed.getBredFor());
    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgMain;
        public TextView tvName;
        public TextView tvBredFor;
        public LinearLayout lnMain;
        public LinearLayout lnDetail;
        public TextView tvDetailName;
        public TextView tvDetailOrigin;
        public TextView tvDetailLifeSpan;
        public TextView tvDetailBredFor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMain = itemView.findViewById(R.id.img_main);
            tvName = itemView.findViewById(R.id.tv_name);
            tvBredFor = itemView.findViewById(R.id.tv_bred_for);
            lnMain = itemView.findViewById(R.id.ln_main);
            lnDetail = itemView.findViewById(R.id.ln_detail);
            tvDetailName = itemView.findViewById(R.id.tv_detail_name);
            tvDetailLifeSpan = itemView.findViewById(R.id.tv_detail_life_span);
            tvDetailOrigin = itemView.findViewById(R.id.tv_detail_origin);
            tvDetailBredFor = itemView.findViewById(R.id.tv_detail_bred_for);

            itemView.setOnTouchListener(new OnSwipeTouchListener(itemView.getContext()) {
                public void viewDetail() {
                    if (lnMain.getVisibility() == View.VISIBLE) {
                        lnMain.setVisibility(View.INVISIBLE);
                        lnDetail.setVisibility(View.VISIBLE);
                    } else {
                        lnMain.setVisibility(View.VISIBLE);
                        lnDetail.setVisibility(View.INVISIBLE);
                    }
                }
                public void onClick() {
                    DogBreed dogBreed = dogBreeds.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dogBreed", dogBreed);
                    Navigation.findNavController(itemView).navigate(R.id.detailsFragment, bundle);
                }
                public void onSwipeRight() {
                    viewDetail();
                    Log.d("DEBUG0", "onSwipeRight");
                }
                public void onSwipeLeft() {
                    viewDetail();
                    Log.d("DEBUG0", "onSwipeLeft");
                }
            });
        }
    }

    public void setDogBreeds(List<DogBreed> listDogs) {
        this.dogBreeds.clear();
        this.dogBreeds.addAll(listDogs);
        notifyDataSetChanged();
    }

    public void findByName(List<DogBreed> listDogs, String name) {
        this.dogBreeds.clear();
        if (name.isEmpty()) {
            this.dogBreeds.addAll(listDogs);
        } else {
            name = name.toLowerCase();
            for (DogBreed dogBreed : listDogs) {
                if (dogBreed.getName().toLowerCase().contains(name)) {
                    this.dogBreeds.add(dogBreed);
                }
            }
        }
        notifyDataSetChanged();
    }
}
