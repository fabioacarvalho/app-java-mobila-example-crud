#  Example of CRUD in App with Java and SQLite

## Start here

_You need have [Android Studio]() instaled in your developer envirioment._ <br>


## First step

In the first step, you need create a file Class where you'll references your instance, for example:

_In this case I'll use a example of add cities. But you are free to create waht you want._
<br>

```
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

```
> Name file: City.java
<br>

## Second step
<br>

Now we'll create a Class of Database. In this file we'll create the CRUD:
<br>

- __Create our Table:__

```

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

}


```
> Name file: CityDatabase.java
<br>

- __CREATE Mehtod__:

```
    public long createCityInDB(City c){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME,c.name);
        values.put(COL_POPULATION,""+c.population);
        long id = database.insert(DB_TABLE, null, values);
        database.close();
        return  id;
    }

```

<br>

- __GET Mehtod__:

```
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

```
<br>

- __UPDATE Mehtod__:

```
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

```
<br>

- __DELETE Mehtod__:

```
public int removeCityInDB(City c){
    SQLiteDatabase database = getWritableDatabase();
    String id = String.valueOf(c.id);
    int count = database.delete(DB_TABLE, COL_ID + "=?", new String[]{id});
    database.close();
    return count;
}
```
<br>

## Third step

Now we'll load ours data in the listView.

Into MainActivity.class in the method onCreate we can create the following code:

```
public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        loadListFromDB();

    }

    void loadListFromDB(){
        CityDatabase database = new CityDatabase(MainActivity.this);
        ArrayList<City> cities = database.getCitiesFromDB();
        for (City c:cities){
            items.add(c.id + "- " + c.name);
            c.print();
        }
    }
}
```
<br>

The method `loadListFromDB` will load ours data and add in the listView, but before you need create a listView in your `file.xml`.