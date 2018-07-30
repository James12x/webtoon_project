package com.example.aldop.uas_webtoon;

/**
 * Created by aldop on 12/7/2017.
 */

public class Comic {
    private int id;
    private String genre;
    private String name;
    private String description;
    private String comic_link;
    private String comic_preview;
    private String comic_background;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    float rating;


    public Comic (int id, String genre, String name, String description, String comic_link, String comic_preview, String comic_background, String date, float rating){
        this.setId(id);
        this.setGenre(genre);
        this.setName(name);
        this.setDescription(description);
        this.comic_link = comic_link;
        this.comic_preview = comic_preview;
        this.comic_background = comic_background;
        this.date = date;
        this.rating = rating;
    }

    public String getImagePreview(){
        return getComic_link() + comic_preview;
    }

    public String getImageBackground(){
        return getComic_link() + comic_background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComic_preview() {
        return comic_preview;
    }

    public void setComic_preview(String comic_preview) {
        this.comic_preview = comic_preview;
    }

    public String getComic_link() {
        return comic_link;
    }

    public void setComic_link(String comic_link) {
        this.comic_link = comic_link;
    }
}
