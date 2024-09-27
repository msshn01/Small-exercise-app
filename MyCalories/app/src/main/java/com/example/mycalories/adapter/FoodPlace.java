package com.example.mycalories.adapter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FoodPlace implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "protein")
    public String protein;
    @ColumnInfo(name = "carb")
    public String carb;
    @ColumnInfo(name = "oil")
    public String oil;
    @ColumnInfo(name = "date")
    public String date;


    public FoodPlace(String name, String protein, String carb, String oil,String date) {
        this.name = name;
        this.protein = protein;
        this.carb = carb;
        this.oil = oil;
        this.date = date;
    }
}
