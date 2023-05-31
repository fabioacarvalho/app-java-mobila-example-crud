package com.example.sqliteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddCityActivity extends AppCompatActivity {

    EditText city;
    EditText population;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
    }

    public void goBackCancel(View v) {
        finish();
    }

    public void createNewCityOnClick(View v){

        EditText city = findViewById(R.id.city);
        EditText population = findViewById(R.id.population);

        try {
            String name_aux = city.getText().toString();
            int population_aux = Integer.parseInt(population.getText().toString());

            if(name_aux.length() > 1 && population.getText().toString().length() > 1){

                CityDatabase database = new CityDatabase(AddCityActivity.this);
                database.createCityInDB(new City(name_aux, population_aux));

                Toast.makeText(AddCityActivity.this, "City added with success.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                // Se quiser adicionar um extra passe o valores como par (chave, valor):
                intent.putExtra("load_data", true);
                startActivity(intent);
            } else {
                Toast.makeText(AddCityActivity.this, "Please entry with city and population", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(AddCityActivity.this, "Error add new City, please try again", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}