package com.example.mycalories.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mycalories.adapter.Excercies;


@Database(
        entities = {Excercies.class},
        version = 3)
public abstract class ExcerciesDatabase extends RoomDatabase {
    public abstract ExcerciesDao excerciesDao();
}
