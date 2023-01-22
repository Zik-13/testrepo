package com.example.examapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AdapterTable adapterTable;
    ArrayList<TableGame> tableGames = new ArrayList<>();
    Intent item_edit;
    Intent item_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new postData().execute();
        ListView listView = findViewById(R.id.listview);
        adapterTable = new AdapterTable(MainActivity.this, (ArrayList<TableGame>) tableGames);
        listView.setAdapter(adapterTable);
        item_edit = new Intent(this,EditPage.class);

    }

    public void Perehod(View v){
        startActivity(item_edit);
    }

    public class postData extends AsyncTask<Void,Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/зеленцовдр/Api/GamesModels");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    TableGame tableGame = new TableGame(
                            jsonObject.getInt("ID"),
                            jsonObject.getString("Title"),
                            jsonObject.getInt("Cost"),
                            jsonObject.getInt("StockAvailability"),
                            jsonObject.getInt("AvailabilityInTheStore"),
                            jsonObject.getString("Description"),
                            jsonObject.getString("Rewiews"),
                            jsonObject.getString("Image")
                            );
                    tableGames.add(tableGame);
                    adapterTable.notifyDataSetInvalidated();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

