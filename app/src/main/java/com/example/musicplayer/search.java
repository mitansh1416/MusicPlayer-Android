package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class search extends AppCompatActivity {

    EditText searchField;
    ImageButton search_btn,back;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("songs");
        searchField = findViewById(R.id.search_field);
        search_btn = findViewById(R.id.search_btn);
        recyclerView = findViewById(R.id.search_list);
       back = findViewById(R.id.back_lib);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchField.getText().toString();
                if(TextUtils.isEmpty(text))
                {
                    searchField.setError("Empty Search Field");
                    return;
                }
                firebaseSongSearch(text);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

    }
    private  void firebaseSongSearch(String text)
    {
        Query query = databaseReference.orderByChild("title").startAt(text).endAt(text+"\uf8ff");
        FirebaseRecyclerAdapter<SongsList,searchViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SongsList, searchViewHolder>(
                SongsList.class,
                R.layout.search_item,
                searchViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(searchViewHolder searchViewHolder, SongsList searchList, int i) {

                 searchViewHolder.setDetails(search.this,searchList.getTitle(),searchList.getArtist(),searchList.getSongcover(),searchList);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }
    public static class  searchViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        Context ctx;
        SongsList songs;

        public void setDetails(Context context,String title,String artist,String songCover,SongsList songsList)
        {
            TextView song_title = view.findViewById(R.id.search_song_name);
            TextView song_Artist = view.findViewById(R.id.search_artist_name);
            ImageView cover = view.findViewById(R.id.img_song);
            song_title.setText(title);
            song_Artist.setText(artist);
            Picasso.with(context).load(Uri.parse(songCover)).into(cover);
            ctx = context.getApplicationContext();
            songs = songsList;


        }
        public searchViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx,MusicControl.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("list",songs);
                    i.putExtra("pos",getAdapterPosition());
                    ctx.startActivity(i);
                }
            });
        }




    }


}
