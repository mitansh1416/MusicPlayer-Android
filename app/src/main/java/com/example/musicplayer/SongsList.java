package com.example.musicplayer;


import android.os.Parcel;
import android.os.Parcelable;

public class SongsList implements Parcelable
{

    private String Title,Duration,Link,songcover,lyrics,artist;

    public SongsList() {
    }

    public SongsList(String Title, String Duration, String Link,String songcover,String lyrics,String artist) {
        this.Title = Title;
        this.Duration = Duration;
        this.Link = Link;
        this.songcover = songcover;
        this.lyrics = lyrics;
        this.artist = artist;

    }

    public SongsList(Parcel in) {
        Title = in.readString();
        Duration = in.readString();
        Link = in.readString();
        songcover = in.readString();
        lyrics = in.readString();
        artist = in.readString();
    }

    public static final Creator<SongsList> CREATOR = new Creator<SongsList>() {
        @Override
        public SongsList createFromParcel(Parcel in) {
            return new SongsList(in);
        }

        @Override
        public SongsList[] newArray(int size) {
            return new SongsList[size];
        }
    };

    public String getTitle() {
        return Title;
    }

    public void setTitle(String songTitle) {
        this.Title = songTitle;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String songDuration) {
        this.Duration = songDuration;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getSongcover() {
        return songcover;
    }

    public void setSongcover(String songcover) {
        this.songcover = songcover;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(Title);
        dest.writeString(Duration);
        dest.writeString(Link);
        dest.writeString(songcover);
        dest.writeString(lyrics);
        dest.writeString(artist);

    }
}
