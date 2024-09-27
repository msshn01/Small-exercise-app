package com.example.mycalories.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mycalories.R;
import com.example.mycalories.adapter.FoodAdepter;
import com.example.mycalories.adapter.FoodPlace;
import com.example.mycalories.databinding.ActivityMainBinding;
import com.example.mycalories.roomdb.FoodDao;
import com.example.mycalories.roomdb.FoodDatabase;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<FoodPlace> arrayList;
    int topCalorie = 0;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        arrayList = new ArrayList<>();


        getData();





    }

    private void getData(){


        Intent intent = getIntent();

        FoodPlace deleteFood = (FoodPlace) intent.getSerializableExtra("deleteFood");
        FoodPlace addFood = (FoodPlace) intent.getSerializableExtra("add");



        FoodDatabase db = Room.databaseBuilder(getApplicationContext(),FoodDatabase.class,"Food")
                .addMigrations(MIGRATION_1_2).allowMainThreadQueries().build();

        FoodDao foodDao = db.placeDao();






        if (addFood != null){

            mDisposable.add(foodDao.insert(addFood).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(MainActivity.this::none,throwable -> System.out.println("Eror subscribe")));

        }

        if (deleteFood != null){
            mDisposable.add(foodDao.delete(deleteFood).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe());

        }


        mDisposable.add(foodDao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity.this::handleResponse,throwable -> System.out.println("Eror subscribe")));




    }

    private void none(){

    }
    @SuppressLint("SetTextI18n")
    private void handleResponse(List<FoodPlace> foodPlaces) {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FoodAdepter foodAdepter = new FoodAdepter(foodPlaces);
        binding.recyclerView.setAdapter(foodAdepter);




    }


    static final Migration MIGRATION_1_2 = new Migration(3,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users "
                    +"ADD COLUMN address String");

        }
    };





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

        } else if (item.getItemId() == R.id.excercies_activity) {
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