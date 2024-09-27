package com.example.mycalories.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mycalories.activity.MainActivity;
import com.example.mycalories.activity.StockActivity;
import com.example.mycalories.databinding.RecycerRowBinding;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;



public class FoodAdepter extends RecyclerView.Adapter<FoodAdepter.FoodHolder> {


    List<FoodPlace> placeList;


    public FoodAdepter(List<FoodPlace> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override

    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecycerRowBinding recycerRowBinding = RecycerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FoodHolder(recycerRowBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, @SuppressLint("RecyclerView") int position) {
        float topCalories = 0;
        holder.recycerRowBinding.foodName.setText(placeList.get(position).name);
        holder.recycerRowBinding.carb.setText("Carb: " + placeList.get(position).carb);
        holder.recycerRowBinding.protein.setText("Protein: " + placeList.get(position).protein);
        holder.recycerRowBinding.oil.setText("Oil: " + placeList.get(position).oil);
        holder.recycerRowBinding.date.setText("Date: " + placeList.get(position).date);


        float carbInt = Float.parseFloat(placeList.get(position).carb) * 4;
        float proteinInt = Float.parseFloat(placeList.get(position).protein) * 4;
        float oilInt = Float.parseFloat(placeList.get(position).oil) * 9;

        topCalories += carbInt + oilInt + proteinInt ;
        holder.recycerRowBinding.calories.setText("Cal:" + topCalories);






        holder.itemView.setOnClickListener(view -> Snackbar.make(view,"Delete selected item?",Snackbar.LENGTH_LONG)
        .setAction("Yes", view1 -> {

               Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
               intent.putExtra("deleteFood",placeList.get(position));
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               holder.itemView.getContext().startActivity(intent);

        }).show());


    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class FoodHolder extends RecyclerView.ViewHolder {
        RecycerRowBinding recycerRowBinding;

        public FoodHolder(RecycerRowBinding recycerRowBinding) {
            super(recycerRowBinding.getRoot());
            this.recycerRowBinding = recycerRowBinding;
        }
    }



}
