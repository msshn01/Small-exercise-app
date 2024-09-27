package com.example.mycalories.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.telephony.ims.RcsUceAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycalories.activity.ExcerciesActivity;
import com.example.mycalories.databinding.ExcerciesRowBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ExcerciesAdapter extends RecyclerView.Adapter<ExcerciesAdapter.ExcerciesHolder> {
    List<Excercies> list;

    public ExcerciesAdapter(List<Excercies> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ExcerciesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExcerciesRowBinding excerciesRowBinding = ExcerciesRowBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ExcerciesHolder(excerciesRowBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull ExcerciesHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.excerciesRowBinding.nameRcy.setText(list.get(position).excerciesName);
        holder.excerciesRowBinding.setRcy.setText(list.get(position).excerciesSet);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View view) {

                Snackbar.make(view,"Delete is item ?",Snackbar.LENGTH_LONG).setAction("yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(holder.itemView.getContext(), ExcerciesActivity.class);
                        intent.putExtra("delete", list.get(position));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        view.getContext().startActivity(intent);


                    }
                }).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ExcerciesHolder extends RecyclerView.ViewHolder{
        ExcerciesRowBinding excerciesRowBinding;
        public ExcerciesHolder(ExcerciesRowBinding excerciesRowBinding) {
            super(excerciesRowBinding.getRoot());
            this.excerciesRowBinding = excerciesRowBinding;
        }
    }
}
