package com.example.sola;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Movie> dataList;
    private Context context;

    public CustomAdapter(Context context, List<Movie> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle,txtBody;
        ImageView img;
        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.titlee);
            txtBody = mView.findViewById(R.id.overview);
            img = mView.findViewById(R.id.img_movie);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new CustomViewHolder(view);
    }
    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Movie> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        dataList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.txtBody.setText(dataList.get(position).getOverview());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+dataList.get(position).getPoster_path()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Movie_Details.class);
                intent.putExtra("title", dataList.get(position).getTitle());
                intent.putExtra("overview", dataList.get(position).getOverview());
                intent.putExtra("poster_path", dataList.get(position).getPoster_path());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}