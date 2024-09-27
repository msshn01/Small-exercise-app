package com.example.mycalories.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycalories.adapter.Excercies;
import com.example.mycalories.adapter.FoodPlace;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
@Dao
public interface ExcerciesDao {

    @Query("SELECT * FROM Excercies")
    Flowable<List<Excercies>> getAll();

    @Insert
    Completable insert(Excercies excercies);


    @Delete
    Completable delete(Excercies excercies);


}
