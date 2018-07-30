package com.example.aldop.uas_webtoon;

/**
 * Created by aldop on 12/7/2017.
 */

public class Episode {
    private int id;
    private int comic_id;
    private int episode_no;
    private String episode_name;
    private int episode_pagecount;
    private String episode_link;

    public Episode (int id, int comic_id, int episode_no, String episode_name, int episode_pagecount, String episode_link){
        this.setId(id);
        this.setComic_id(comic_id);
        this.setEpisode_no(episode_no);
        this.setEpisode_name(episode_name);
        this.setEpisode_pagecount(episode_pagecount);
        this.setEpisode_link(episode_link);
    }

    public String getImage(){
        return episode_link + getEpisode_no() + "/";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }

    public String getEpisode_name() {
        return episode_name;
    }

    public void setEpisode_name(String episode_name) {
        this.episode_name = episode_name;
    }

    public int getEpisode_pagecount() {
        return episode_pagecount;
    }

    public void setEpisode_pagecount(int episode_pagecount) {
        this.episode_pagecount = episode_pagecount;
    }

    public String getEpisode_link() {
        return episode_link;
    }

    public void setEpisode_link(String episode_link) {
        this.episode_link = episode_link;
    }

    public int getEpisode_no() {
        return episode_no;
    }

    public void setEpisode_no(int episode_no) {
        this.episode_no = episode_no;
    }
}
