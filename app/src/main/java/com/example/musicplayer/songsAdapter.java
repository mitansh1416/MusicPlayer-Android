package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;




public class songsAdapter extends RecyclerView.Adapter<songsAdapter.songsAdapterViewHolder> {

      Context context;
      ArrayList<SongsList> ArrayListSongs;


    public  songsAdapter(Context context, ArrayList<SongsList> ArrayListSongs) {
        this.context = context;
        this.ArrayListSongs = ArrayListSongs;
    }

    @NonNull
    @Override
    public songsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_item,parent,false);
        return new songsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull songsAdapterViewHolder holder, int position) {


        holder.titleTxt.setText(ArrayListSongs.get(position).getTitle());
        holder.durationTxt.setText(ArrayListSongs.get(position).getDuration());

    }

    @Override
    public int getItemCount() {
        return ArrayListSongs.size();
    }

    public class songsAdapterViewHolder extends RecyclerView.ViewHolder  {
        TextView titleTxt,durationTxt;
        songsAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.song_title);
            durationTxt = itemView.findViewById(R.id.duration);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((Songs)context).playSong(ArrayListSongs,getAdapterPosition());


                }
            });

        }

    }
}
