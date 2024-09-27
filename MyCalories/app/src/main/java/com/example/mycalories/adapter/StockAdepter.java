package com.example.mycalories.adapter;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mycalories.activity.MainActivity;
import com.example.mycalories.activity.StockActivity;
import com.example.mycalories.databinding.RecyclerRowStockBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class StockAdepter extends RecyclerView.Adapter<StockAdepter.StockHolder> {
    List<FoodPlace> placeList;
    String specimen;


    public StockAdepter(List<FoodPlace> placeList,String specimen) {
        this.placeList = placeList;
        this.specimen = specimen;

    }

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowStockBinding recyclerRowStockBinding = RecyclerRowStockBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new StockHolder(recyclerRowStockBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, @SuppressLint("RecyclerView") int position) {



        if (specimen.equals("1")){

            //Gram tarzında ekleme ekranı

            holder.recyclerRowStockBinding.foodName.setText(placeList.get(position).name);
            holder.recyclerRowStockBinding.foodCal.setText("Protein: " + placeList.get(position).protein);


            holder.itemView.setOnClickListener(new View.OnClickListener() {

                FoodPlace selectedGramFood = placeList.get(position);




                @Override
                public void onClick(View view) {


                    Snackbar.make(view,"Öğün Seçilsin mi ?",Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(view.getContext(),StockActivity.class);
                            intent.putExtra("selected",selectedGramFood);
                            intent.putExtra("gram",1);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            view.getContext().startActivity(intent);


                        }
                    }).show();

                }
            });




        }else{

            //NORMAL STOCK ARAYUZ
            holder.recyclerRowStockBinding.foodName.setText(placeList.get(position).name);
            float carbInt = Float.parseFloat(placeList.get(position).carb) * 4;
            float proteinInt = Float.parseFloat(placeList.get(position).protein) * 4;
            float oilInt = Float.parseFloat(placeList.get(position).oil) * 9;
            float topCalories = 0;
            topCalories += carbInt + oilInt + proteinInt ;
            holder.recyclerRowStockBinding.foodCal.setText("Cal: " + String.valueOf(topCalories));

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Snackbar.make(view,"Add food?",Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.putExtra("add",placeList.get(position));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            view.getContext().startActivity(intent);
                        }
                    }).show();


                    return true;

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                FoodPlace deleteFood = placeList.get(position);

                @Override
                public void onClick(View view) {

                    Snackbar.make(view,"Delete item?",Snackbar.LENGTH_LONG).setAction("yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(holder.itemView.getContext(), StockActivity.class);
                            intent.putExtra("delete",deleteFood);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            view.getContext().startActivity(intent);




                        }
                    }).show();


                    Intent intent = new Intent(holder.itemView.getContext(),StockAdepter.class);
                    intent.putExtra("delete",placeList.get(position));
                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class StockHolder extends RecyclerView.ViewHolder{
        RecyclerRowStockBinding recyclerRowStockBinding;
        public StockHolder(RecyclerRowStockBinding recyclerRowStockBinding) {
            super(recyclerRowStockBinding.getRoot());
            this.recyclerRowStockBinding = recyclerRowStockBinding;

        }
    }
}
