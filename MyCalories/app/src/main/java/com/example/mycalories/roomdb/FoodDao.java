package com.example.mycalories.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycalories.adapter.FoodPlace;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM FoodPlace")
    Flowable<List<FoodPlace>> getAll();

    @Insert
    Completable insert(FoodPlace foodPlace);
    //void insert(Place place);


    @Delete
    Completable delete(FoodPlace foodPlace);
    //void delete(Place place);
}
