package com.example.sola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter recyclerAdapter;
    List<Movie> movieList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                generateDataList(movieList);
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
}