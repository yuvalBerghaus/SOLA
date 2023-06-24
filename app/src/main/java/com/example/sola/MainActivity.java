package com.example.sola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    DateSorter dateSorter;
    CustomAdapter recyclerAdapter;
    String[] options = {"descending", "ascending"};
    android.widget.SearchView searchView;
    List<Movie> movieList;

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Movie> filteredlist = new ArrayList<Movie>();

        // running a for loop to compare elements.
        for (Movie item : movieList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            recyclerAdapter.filterList(filteredlist);
        }
    }

    // calling on create option menu
    // layout to inflate our menu file.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateSorter = DateSorter.getInstance();
        Spinner spin = (Spinner) findViewById(R.id.static_spinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new CustomAdapter(getApplicationContext(), movieList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API service = retrofit.create(API.class);

        //3c891b1006d9bdf7725711a9834ce4e2
        Call<ModelData> call = service.getReponse("3c891b1006d9bdf7725711a9834ce4e2");
        call.enqueue(new Callback<ModelData>() {
            @Override
            public void onResponse(Call<ModelData> call, Response<ModelData> response) {
                Log.d("hey!", "hello");
                Log.d("hey!", "hello");
                if(response.code() != 200) {
                    Log.d("Connection", "connection error!");

                }
                assert response.body() != null;
                movieList = response.body().getResults();
                Log.d("movies", movieList.get(0).getTitle());
                //Sorting by date
                DateSorter.getInstance().sort_descending_date(movieList);
                generateDataList(movieList);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filter(newText);
                        return false;
                    }
                });
//                Log.d("response", "d");
            }

            @Override
            public void onFailure(Call<ModelData> call, Throwable t) {
                Log.d("NO", "NOOOO");
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Movie> movies) {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new CustomAdapter(this,movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), options[position], Toast.LENGTH_LONG).show();
        if(movieList!= null) {
            switch (options[position]) {
                case "ascending":
                    Log.d("sort", "ascending");
                    DateSorter.getInstance().sort_ascending_date(movieList);
                    recyclerAdapter.notifyDataSetChanged();
                    break;
                case "descending":
                    Log.d("sort", "descending");
                    DateSorter.getInstance().sort_descending_date(movieList);
                    recyclerAdapter.notifyDataSetChanged();
                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}