package com.example.mycalories.activity;

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


import com.example.mycalories.R;
import com.example.mycalories.adapter.Excercies;
import com.example.mycalories.adapter.ExcerciesAdapter;
import com.example.mycalories.databinding.ActivityExcerciesBinding;
import com.example.mycalories.databinding.RecyclerRowStockBinding;
import com.example.mycalories.roomdb.ExcerciesDao;
import com.example.mycalories.roomdb.ExcerciesDatabase;

import java.security.PublicKey;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExcerciesActivity extends AppCompatActivity {
    ActivityExcerciesBinding binding;
    ExcerciesDao excerciesDao;
    ExcerciesDatabase db;
    Excercies mainExcercies;
    final private CompositeDisposable mDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExcerciesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        db = Room.databaseBuilder(getApplicationContext(),ExcerciesDatabase.class,"Excercies").build();
        excerciesDao = db.excerciesDao();


        Intent intent = getIntent();
        Excercies excerciesDelete = (Excercies) intent.getSerializableExtra("delete");

        if (excerciesDelete != null){

            mDisposable.add(excerciesDao.delete(excerciesDelete).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe());

        }







        getData();

    }


    public void getData(){

        mDisposable.add(excerciesDao.getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(ExcerciesActivity.this::handleResponse,throwable -> System.out.println("Eror subscribe")));



    }

    private void handleResponse(List<Excercies> excercies) {

        binding.recyclerView.setLayoutManager( new LinearLayoutManager(this));
        ExcerciesAdapter excerciesAdapter = new ExcerciesAdapter(excercies);
        binding.recyclerView.setAdapter(excerciesAdapter);


    }


    public void addExcercies(View view){

        String excerciesName = binding.nameRcy.getText().toString();
        String excerciesSet = binding.setTekrar.getText().toString();

        if (!excerciesName.isEmpty() & !excerciesSet.isEmpty()){

            mainExcercies = new Excercies(excerciesName,excerciesSet);

            mDisposable.add(excerciesDao.insert(mainExcercies).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe());




            Intent intent = new Intent(this,ExcerciesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);



        }else {
            Toast.makeText(this, "Enter name end set", Toast.LENGTH_SHORT).show();
        }




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