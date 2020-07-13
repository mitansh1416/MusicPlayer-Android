package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Random;


public class MusicControl extends AppCompatActivity {
    static MediaPlayer mp;
    ImageButton playpause,song_list,back,forward,back_lib,shuffle;
    SeekBar volume,progress;
    int position;
    String link,songcover,lyrics;
    ImageView song_cover;
    AudioManager audioManager;
    TextView song_name,start_time,stop_time,artist_name;
    double startTime = 0;
    double finalTime = 0;
    Handler myHandler = new Handler();
    int oneTimeOnly=0;
    Uri uri,cover_uri;
    Button lyrics_btn;
    ArrayList<SongsList> arrayList;
    SongsList songsList;
    Random rand;
    boolean click = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_music_control);
        playpause = findViewById(R.id.playpause);
        song_list = findViewById(R.id.song_list);
        volume = findViewById(R.id.Volume);
        song_name = findViewById(R.id.song_name);
        back = findViewById(R.id.back);
        forward = findViewById(R.id.forward);
        back_lib = findViewById(R.id.back_lib);
        progress = findViewById(R.id.songtime);
        song_cover = findViewById(R.id.Song_Cover);
        start_time = findViewById(R.id.start_time);
        stop_time = findViewById(R.id.stop_time);
        artist_name = findViewById(R.id.artist_name);
        shuffle = findViewById(R.id.search);
        lyrics_btn = findViewById(R.id.Lyrics);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        initControls();
        if(mp != null){
            mp.stop();
            mp.release();
        }
        Intent i = getIntent();
        Bundle b = i.getExtras();
        position = i.getIntExtra("pos",0);

        if (b != null) {

            arrayList = b.getParcelableArrayList("list");
        }
        if (arrayList != null) {
            songsList = arrayList.get(position);
            artist_name.setText(songsList.getArtist());
            song_name.setText(songsList.getTitle());
            lyrics = songsList.getLyrics();
            songcover = songsList.getSongcover();
            link = songsList.getLink();

        }
        cover_uri = Uri.parse(songcover);
        Picasso.with(getApplicationContext()).load(cover_uri).into(song_cover);
        uri = Uri.parse(link);
        mp = MediaPlayer.create(getApplicationContext(),uri);
        mp.start();
        finalTime = mp.getDuration();
        startTime = mp.getCurrentPosition();

            progress.setMax((int) finalTime);


        stop_time.setText(msToString((int) finalTime));
        start_time.setText(msToString((int)startTime));
        progress.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,50);
        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                  mp.seekTo(seekBar.getProgress());
            }
        });

        playpause.setBackgroundResource(R.drawable.musiccontrol_grey_pause);
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    playpause.setBackgroundResource(R.drawable.musiccontrol_grey_play);
                    mp.pause();


                }
                else {
                    playpause.setBackgroundResource(R.drawable.musiccontrol_grey_pause);
                    mp.start();
                }
            }
        });
        song_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MusicControl.this,Songs.class));
            }
        });
      back_lib.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),MainActivity.class));
          }
      });
      lyrics_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),Lyrics.class)
                      .putExtra("lyrics",lyrics));
          }
      });
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mp.stop();
              mp.release();

              position=((position-1)<0)?(arrayList.size()-1):(position-1);
              SongsList songsList = arrayList.get(position);
              artist_name.setText(songsList.getArtist());
              song_name.setText(songsList.getTitle());
              lyrics = songsList.getLyrics();
              songcover = songsList.getSongcover();
              link = songsList.getLink();
              cover_uri = Uri.parse(songcover);
              Picasso.with(getApplicationContext()).load(cover_uri).into(song_cover);
              uri = Uri.parse(link);
              mp = MediaPlayer.create(getApplicationContext(),uri);
              mp.start();

          }
      });
      forward.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mp.stop();
              mp.release();
              position=((position+1)%arrayList.size());
              songsList = arrayList.get(position);
              artist_name.setText(songsList.getArtist());
              song_name.setText(songsList.getTitle());
              lyrics = songsList.getLyrics();
              songcover = songsList.getSongcover();
              link = songsList.getLink();
              cover_uri = Uri.parse(songcover);
              Picasso.with(getApplicationContext()).load(cover_uri).into(song_cover);
              uri = Uri.parse(link);
              mp = MediaPlayer.create(getApplicationContext(),uri);
              mp.start();
          }
      });
      shuffle.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mp.stop();
              mp.release();
              rand = new Random();
              position = rand.nextInt(arrayList.size());
              songsList = arrayList.get(position);
              artist_name.setText(songsList.getArtist());
              song_name.setText(songsList.getTitle());
              lyrics = songsList.getLyrics();
              songcover = songsList.getSongcover();
              link = songsList.getLink();
              cover_uri = Uri.parse(songcover);
              Picasso.with(getApplicationContext()).load(cover_uri).into(song_cover);
              uri = Uri.parse(link);
              mp = MediaPlayer.create(getApplicationContext(),uri);
              mp.start();
          }
      });

      mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
          @Override
          public void onCompletion(MediaPlayer mp1) {
              mp.stop();
              mp.release();
              position=((position+1)%arrayList.size());
              songsList = arrayList.get(position);
              artist_name.setText(songsList.getArtist());
              song_name.setText(songsList.getTitle());
              lyrics = songsList.getLyrics();
              songcover = songsList.getSongcover();
              link = songsList.getLink();
              cover_uri = Uri.parse(songcover);
              Picasso.with(getApplicationContext()).load(cover_uri).into(song_cover);
              uri = Uri.parse(link);
              mp = MediaPlayer.create(getApplicationContext(),uri);
              mp.start();
              finalTime = mp.getDuration();
              startTime = mp.getCurrentPosition();
              progress.setMax((int) finalTime);
              stop_time.setText(msToString((int) finalTime));
              start_time.setText(msToString((int)startTime));
              progress.setProgress((int)startTime);
          }
      });

    }
    private  Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {

                 startTime = mp.getCurrentPosition();
                 progress.setProgress((int)startTime);
                 start_time.setText(msToString((int) startTime));
                 myHandler.postDelayed(this,50);
        }
    };
    String msToString(int ms){

        int hh = ms / 3600000;
        ms -= hh * 3600000;
        int mm = ms / 60000;
        ms -= mm * 60000;
        int ss = ms / 1000;

        return String.format(getString(R.string.time), mm, ss);
    }
    private  void  initControls()
    {
        try {


             volume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
             volume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        int action = event.getAction();
        int keycode=event.getKeyCode();
        switch (keycode)
        {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(action==KeyEvent.ACTION_DOWN)
                {
                       if(event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout())
                       {
                                int index  = volume.getProgress();
                                volume.setProgress(index+5);
                                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume.getProgress(),0);
                       }
                       else
                       {
                           int index  = volume.getProgress();
                           volume.setProgress(index+2);
                           audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume.getProgress(),0);
                       }
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(action==KeyEvent.ACTION_DOWN)
                {
                    if(event.getEventTime() - event.getDownTime() > ViewConfiguration.getLongPressTimeout())
                    {
                        int index  = volume.getProgress();
                        volume.setProgress(index-5);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume.getProgress(),0);
                    }
                    else
                    {
                        int index  = volume.getProgress();
                        volume.setProgress(index-2);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume.getProgress(),0);
                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);

        }



    }

}
