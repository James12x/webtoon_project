package com.example.aldop.uas_webtoon;

import java.util.Date;

/**
 * Created by aldop on 12/10/2017.
 */

public class Comment {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(int episode_id) {
        this.episode_id = episode_id;
    }

    int episode_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    int user_id;
    private String username;
    private String text;
    private String date;
    private int like;

    public boolean isHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    boolean hasLiked;

    public Comment(int id, int episode_id, int user_id, String username, String text, String date, int like, boolean hasLiked){
        this.id = id;
        this.episode_id = episode_id;
        this.user_id = user_id;
        this.setUsername(username);
        this.setText(text);
        this.setDate(date);
        this.setLike(like);
        this.hasLiked = hasLiked;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
