package com.example.sola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Movie_Details extends AppCompatActivity {
    ImageView image;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        image = findViewById(R.id.image_detail);
        title = findViewById(R.id.title_detail);
        Intent data = getIntent();
        title.setText(data.getStringExtra("title"));
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+data.getStringExtra("poster_path")).into(image);

    }
}