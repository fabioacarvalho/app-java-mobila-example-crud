package com.example.sqliteapplication;

import android.util.Log;

public class City {

    public long id;
    public String name;
    public int population;

    public City(long id, String name, int population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public City(String name, int population) {
        id = -1;
        this.name = name;
        this.population = population;
    }

    public void print(){
        Log.v("SQLDatabase", "City["+id+"]:"+name);
    }

}
