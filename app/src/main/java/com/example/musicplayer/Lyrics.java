package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Lyrics extends AppCompatActivity {

    String Lyrics;
    TextView lyrics_text;
    ImageButton back;
    final long ONE_MEGABYTE = 1024 * 1024;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        lyrics_text = findViewById(R.id.lyrics);
        back = findViewById(R.id.back_lib);
        Intent i = getIntent();
        Lyrics = i.getStringExtra("lyrics");
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Lyrics);
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                String s = new String(bytes);
                lyrics_text.setText(s);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

    }
}
