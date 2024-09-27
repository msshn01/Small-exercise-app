package com.example.mycalories.activity;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mycalories.R;
import com.example.mycalories.adapter.FoodPlace;
import com.example.mycalories.adapter.StockAdepter;
import com.example.mycalories.databinding.ActivityStockBinding;
import com.example.mycalories.roomdb.FoodDao;
import com.example.mycalories.roomdb.FoodDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StockActivity extends AppCompatActivity {
    private ActivityStockBinding binding;
    FoodDao foodDao;
    FoodDatabase db;
    FoodDao daoEnd;
    FoodDatabase dbEnd;
    FoodPlace deleteFood;
    FoodPlace foodPlaceMain;
    String foodName;
    String foodCarb;
    String foodProtein;
    String foodOil;
    String formatDate;
    List<FoodPlace> hazirMenu;
    FoodPlace selectedFoodGram;
    int gram;
    final private CompositeDisposable mDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        db = Room.databaseBuilder(getApplicationContext(), FoodDatabase.class, "Food").build();
        foodDao = db.placeDao();

        dbEnd = Room.databaseBuilder(getApplicationContext(), FoodDatabase.class, "FoodEnd").addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries().build();

        daoEnd = dbEnd.placeDao();


        Intent intent = getIntent();

        deleteFood = (FoodPlace) intent.getSerializableExtra("delete");
        gram = intent.getIntExtra("gram",0);
        selectedFoodGram = (FoodPlace) intent.getSerializableExtra("selected");









        if (deleteFood != null) {
            mDisposable.add(daoEnd.delete(deleteFood).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe());

        }



            getData();







    }

    public void saveEnd(View view){

        foodCarb=binding.carb.getText().toString();
        foodProtein=binding.protein.getText().toString();
        foodOil=binding.oil.getText().toString();
        foodName=binding.foodName.getText().toString();
        Date date = Calendar.getInstance().getTime();
        formatDate = DateFormat.getDateInstance().format(date);

        if ((!Objects.equals(foodCarb, "")) & (!foodName.isEmpty()) & (!Objects.equals(foodProtein, ""))&(!Objects.equals(foodOil, ""))){


            foodPlaceMain = new FoodPlace(foodName,foodProtein,foodCarb,foodOil,formatDate);





            mDisposable.add(foodDao.insert(foodPlaceMain)
                    .subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread())
                    .subscribe());



            mDisposable.add(daoEnd.insert(foodPlaceMain)
                    .subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread())
                    .subscribe(StockActivity.this::handleResponseEnd));
        }else{

            Toast.makeText(this, "Enter information", Toast.LENGTH_SHORT).show();

        }


    }
    private void handleResponseEnd() {
        Intent intent = new Intent(this, StockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void save(View view){


        foodCarb=binding.carb.getText().toString();
        foodProtein=binding.protein.getText().toString();
        foodOil=binding.oil.getText().toString();
        foodName=binding.foodName.getText().toString();


        Date date = Calendar.getInstance().getTime();
        formatDate = DateFormat.getDateInstance().format(date);

        if ((!Objects.equals(foodCarb, "")) & (!foodName.isEmpty()) & (!Objects.equals(foodProtein, ""))&(!Objects.equals(foodOil, ""))){


            foodPlaceMain = new FoodPlace(foodName,foodProtein,foodCarb,foodOil,formatDate);


            mDisposable.add(foodDao.insert(foodPlaceMain)
                    .subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread())
                    .subscribe(StockActivity.this::handleResponse));



        }else{
            Toast.makeText(this, "Enter information", Toast.LENGTH_SHORT).show();
        }




    }


    private void handleResponse() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }






    static final Migration MIGRATION_1_2 = new Migration(3,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users "
                    +"ADD COLUMN address String");

        }
    };

    public void getData(){
        mDisposable.add(daoEnd.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(StockActivity.this::handleResponse,throwable -> System.out.println("Eror subscribe")));

    }

    private void handleResponse(List<FoodPlace> foodPlaces) {

        binding.recyclerViewStock.setLayoutManager( new LinearLayoutManager(this));
        StockAdepter stockAdepter = new StockAdepter(foodPlaces,"0");
        binding.recyclerViewStock.setAdapter(stockAdepter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.get_to_Main){
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if (item.getItemId() == R.id.stock_meal){
            Intent intent = new Intent(this,StockActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (item.getItemId() == R.id.excercies_activity) {
            Intent intent = new Intent(this,ExcerciesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);



        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }
}