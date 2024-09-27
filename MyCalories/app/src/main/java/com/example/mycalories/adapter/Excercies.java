package com.example.mycalories.adapter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Excercies  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String excerciesName;
    @ColumnInfo(name = "set")
    public String excerciesSet;






    public Excercies(String excerciesName, String excerciesSet) {
        this.excerciesName = excerciesName;
        this.excerciesSet = excerciesSet;
    }
}
