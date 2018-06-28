package com.example.android.moviemanager.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("poster_path")
    @Expose
    private String posterImage;

    @SerializedName("release_date")
    @Expose
    private String date;

    @SerializedName("vote_average")
    @Expose
    private String voteAverage;

    @SerializedName("vote_count")
    @Expose
    private String voteCount;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("backdrop_path")
    @Expose
    private String backPoster;

    public Movie(String title, String posterImage, String date, String voteAverage, String voteCount, String overview, String backPoster) {
        this.title = title;
        this.posterImage = posterImage;
        this.date = date;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.overview = overview;
        this.backPoster = backPoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImage() {
        return String.format("http://image.tmdb.org/t/p/w400%s",posterImage);
    }

    public String getBackPoster() {
        return String.format("http://image.tmdb.org/t/p/w400%s",backPoster);
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public void setBackPoster(String backPoster) {
        this.backPoster = backPoster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
