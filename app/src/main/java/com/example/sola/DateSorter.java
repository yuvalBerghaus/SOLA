package com.example.sola;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateSorter {
    private static DateSorter Instance = null;
    private DateSorter() {
        Log.d("SingleTon created!", "SingleTon created!");
    }
    public static DateSorter getInstance() {
        if(Instance == null) {
            Instance = new DateSorter();
        }
        return Instance;
    }
    public void sort_ascending_date(List<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getDateType().compareTo(o2.getDateType());
            }
        });
    }
    public void sort_descending_date(List<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.getDateType().compareTo(o1.getDateType());
            }
        });
    }
}
