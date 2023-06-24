package com.example.sola;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    public String getDate() {
        return release_date;
    }
    public Date getDateType() {
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(release_date);
            return date1;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
