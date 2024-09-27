package com.example.mycalories.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mycalories.adapter.FoodPlace;

@Database(
        entities = {FoodPlace.class},
        version = 3)
public abstract class FoodDatabase extends RoomDatabase {
    public abstract FoodDao placeDao();
}
