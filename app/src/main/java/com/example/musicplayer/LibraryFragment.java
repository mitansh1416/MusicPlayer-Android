package com.example.musicplayer;




import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class LibraryFragment extends Fragment {
    TextView playlist,songs,downloaded_music;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_library,null);
        playlist = v.findViewById(R.id.playlist);
        songs = v.findViewById(R.id.Songs);
        downloaded_music = v.findViewById(R.id.Downloded_songs);
        playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(),Playlist.class));
            }
        });
        songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Songs.class));
            }
        });
        downloaded_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Downloded_Music.class));
            }
        });



     return v;
    }


}
