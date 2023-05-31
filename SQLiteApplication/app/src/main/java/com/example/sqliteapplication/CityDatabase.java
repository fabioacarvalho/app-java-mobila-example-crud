package com.example.sqliteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CityDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "cities.sqlite";
    private static final int DB_VERSION = 1;

    // Database Table
    private static String DB_TABLE = "City";
    private static String COL_ID = "id";
    private static String COL_NAME = "name";
    private static String COL_POPULATION = "population";

    private Context context;
    public CityDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create Table if not exists " + DB_TABLE + "( "+
                COL_ID + " Integer primary key autoincrement, " +
                COL_NAME + " TEXT, " +
                COL_POPULATION + " INTEGER" + ")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long createCityInDB(City c){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME,c.name);
        // o ""+c.population as "" seria para modificar para string.
        values.put(COL_POPULATION,""+c.population);
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return  id;
    }

    // GET

    public ArrayList<City> getCitiesFromDB(){
        ArrayList<City> cities = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        // Criar um cursor de localizacao da tabela:
        // database.query.(DB_TABLE, column, selection, selectionArgs, groupBy, having, orderBy):
        Cursor cursor = database.query(DB_TABLE, null, null, null, null,null, null);

        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                int population = cursor.getInt(cursor.getColumnIndexOrThrow(COL_POPULATION));
                cities.add(
                        new City(id, name, population)
                );
            } while (cursor.moveToNext());
            database.close();
        }

        return cities;
    }

    // UPDATE
    public int updateCityInDB(City c){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, c.name);
        values.put(COL_POPULATION, c.population);
        String id = String.valueOf(c.id);
        int count = database.update(DB_TABLE, values, COL_ID + "=?", new String[]{id});
        database.close();

        return count;
    }

    // DELETE
    public int removeCityInDB(City c){
        SQLiteDatabase database = getWritableDatabase();
        String id = String.valueOf(c.id);
        int count = database.delete(DB_TABLE, COL_ID + "=?", new String[]{id});
        database.close();
        return count;
    }
}
