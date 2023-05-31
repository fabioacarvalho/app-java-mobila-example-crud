package com.example.sqliteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);

        // Populando a tabela:
        CityDatabase database = new CityDatabase(MainActivity.this);
        //database.createCityInDB(new City("Curitiba", 1000000));
        //database.createCityInDB(new City("São Paulo", 20000000));
        //database.createCityInDB(new City("Minas Gerais", 500000));
        //database.createCityInDB(new City("Pernambuco", 300000));
        //database.createCityInDB(new City("Natal", 500000));

        loadListFromDB();
        updateListView();
    }

    // Verificando dados criados:
    void loadListFromDB(){
        CityDatabase database = new CityDatabase(MainActivity.this);
        ArrayList<City> cities = database.getCitiesFromDB();
        for (City c:cities){
            items.add(c.id + "- " + c.name);
            c.print();
        }
    }
    void updateListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                items
        );
        listView.setAdapter(adapter);
    }

    // Inserindo o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    void goToDetailActivity(int index){
        // Ir outra activity
        Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
        // Se quiser adicionar um extra passe o valores como par (chave, valor):
        // intent.putExtra("index", index);
        startActivity(intent);
    }

    public void goToAddNewCity(){
        Intent intent = new Intent();
        Toast.makeText(MainActivity.this, "CLICOU", Toast.LENGTH_LONG).show();
    }

    // Ouvindo click do menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_add_city) {
            // Ir outra activity
            goToDetailActivity(-1);
//            goToAddCityActivity(-1);

        }
        return super.onOptionsItemSelected(item);
    }
}