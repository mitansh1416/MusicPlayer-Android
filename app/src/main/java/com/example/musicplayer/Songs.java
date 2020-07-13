package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;


public class Songs extends AppCompatActivity {
   ImageButton back;
   RecyclerView song_list;
   ArrayList<SongsList> mSongs;
   songsAdapter adapter;
   Button search;
   FloatingActionButton shuffle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        back = findViewById(R.id.back);
        song_list = findViewById(R.id.songslist);
        shuffle = findViewById(R.id.shuffle);
        search = findViewById(R.id.search);
        song_list.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase.getInstance().getReference().child("songs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSongs = new ArrayList<>();
                for(DataSnapshot data:dataSnapshot.getChildren())
                {

                    SongsList songsList = data.getValue(SongsList.class);
                    mSongs.add(songsList);

                }
                 adapter = new songsAdapter(Songs.this,mSongs);
                  song_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Songs.this,MainActivity.class));
            }
        });
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Random rand = new Random();
                    int position = rand.nextInt(mSongs.size());
                    SongsList songsList = mSongs.get(position);
                    startActivity(new Intent(getApplicationContext(), MusicControl.class)
                            .putExtra("list",songsList)
                            .putExtra("pos",position)
                            );



            }






        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),search.class));
            }
        });






    }




    public void playSong(ArrayList<SongsList> arrayListSongs, int adapterPosition) {



            startActivity(new Intent(getApplicationContext(), MusicControl.class)
                    .putExtra("list",arrayListSongs)
                    .putExtra("pos",adapterPosition));



        }


}



