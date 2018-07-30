package com.example.aldop.uas_webtoon;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Laptop-SI on 13/10/2017.
 */

public class SendPostRequest extends AsyncTask<String, Void, String> {
    GenreActivity ga;
    ComicActivity ca;
    ReadingActivity ra;
    CommentActivity coa;
    PopularActivity pa;
    NewestActivity na;
    FavoriteActivity fa;

    public enum DataType {
        NONE,
        COMIC_GENRE,
        COMIC_POPULAR,
        COMIC_NEWEST,
        COMIC_FAVORITE,
        SEND_FAVORITE,
        SEND_UNFAVORITE,
        EPISODE,
        SEND_RATING,
        GET_FAVORITE_RATINGS,
        Comment,
        Top_Comment,
        Send_Comment,
        Like_Comment_Comment,
        Like_Comment_Reading,
        Unlike_Comment_Comment,
        Unlike_Comment_Reading
    }

    DataType data;
    
    public SendPostRequest() {
        data = DataType.NONE;
    }

    protected void onPreExecute(){}

    public void getFavoriteAndRating(int comic_id, ComicActivity ca){
        this.ca = ca;
        String link = Connection.Ip + "getFavoriteAndRatings.php";
        execute("comic_id", comic_id+"", link);
        data = DataType.GET_FAVORITE_RATINGS;
    }

    public void sendRating(int comic_id, float rating_value, ComicActivity ca){
        this.ca = ca;
        String link = Connection.Ip + "sendRating.php";
        execute("comic_id", comic_id+"", "rating_value", rating_value+"", link);
        data = DataType.SEND_RATING;
    }

    public void getFavoriteComic(FavoriteActivity fa){
        this.fa = fa;
        String link = Connection.Ip + "loadFavoriteComic.php";
        execute(link);
        data = DataType.COMIC_FAVORITE;
    }

    public void sendFavorite(int comic_id, ComicActivity ca){
        this.ca = ca;
        String link = Connection.Ip + "sendFavorite.php";
        execute("comic_id", comic_id+"", link);
        data = DataType.SEND_FAVORITE;
    }

    public void sendUnFavorite(int comic_id, ComicActivity ca){
        this.ca = ca;
        String link = Connection.Ip + "sendUnFavorite.php";
        execute("comic_id", comic_id+"", link);
        data = DataType.SEND_UNFAVORITE;
    }

    public void getRecentComic(NewestActivity na){
        this.na = na;
        String link = Connection.Ip + "loadRecentComic.php";
        execute(link);
        data = DataType.COMIC_NEWEST;
    }

    public void getPopularComic(PopularActivity pa){
        this.pa = pa;
        String link = Connection.Ip + "loadPopularComic.php";
        execute(link);
        data = DataType.COMIC_POPULAR;
    }

    public void getComicBasedOnGenre(String genre_name, GenreActivity ga){
        this.ga = ga;
        String link = Connection.Ip + "loadComicByGenre.php";
        execute("genre_name", genre_name, link);
        data = DataType.COMIC_GENRE;
        //Toast.makeText(ga, "MASUK GENRE", Toast.LENGTH_SHORT).show();
    }

    public void getTopComments(int episode_id, ReadingActivity ra){
        this.ra = ra;
        String link = Connection.Ip + "loadTopComment.php";
        execute("episode_id", episode_id+"", link);
        data = DataType.Top_Comment;
    }

    public void getComments(int episode_id, CommentActivity coa){
        this.coa = coa;
        String link = Connection.Ip + "loadComment.php";
        execute("episode_id", episode_id+"", link);
        data = DataType.Comment;
    }


    public void GetEpisodes(int comic_id, ComicActivity ca){
        this.ca = ca;
        String link = Connection.Ip + "getEpisode.php";
        execute("comic_id", comic_id+"", link);
        data = DataType.EPISODE;
    }

    public void SendComment(int eps_id, int user_id, String comment, CommentActivity coa){
        this.coa = coa;
        String link = Connection.Ip + "sendComment.php";
        execute("eps_id", eps_id+"", "user_id", user_id+"", "comment", comment, link);
        data = DataType.Send_Comment;
    }

    public void LikeComment(int comment_id, int user_id, CommentActivity coa){
        this.coa = coa;
        String link = Connection.Ip + "sendLike.php";
        execute("comment_id", comment_id+"", "user_id", user_id+"", link);
        data = DataType.Like_Comment_Comment;
    }

    public void LikeComment(int comment_id, int user_id, ReadingActivity ra){
        this.ra = ra;
        String link = Connection.Ip + "sendLike.php";
        execute("comment_id", comment_id+"", "user_id", user_id+"", link);
        data = DataType.Like_Comment_Reading;
    }

    public void UnlikeComment(int comment_id, int user_id, CommentActivity coa){
        this.coa = coa;
        String link = Connection.Ip + "sendUnLike.php";
        execute("comment_id", comment_id+"", "user_id", user_id+"", link);
        data = DataType.Unlike_Comment_Comment;
    }

    public void UnlikeComment(int comment_id, int user_id, ReadingActivity ra){
        this.ra = ra;
        String link = Connection.Ip + "sendUnLike.php";
        execute("comment_id", comment_id+"", "user_id", user_id+"", link);
        data = DataType.Unlike_Comment_Reading;
    }

    protected String doInBackground(String... arg0) {
        try {

            URL url = new URL(arg0[arg0.length - 1]); // here is your URL path

            JSONObject postDataParams = new JSONObject();

            if(arg0.length > 1){
                for(int i = 0; i < arg0.length - 1; i += 2){
                    postDataParams.put(arg0[i], arg0[i+1]);
                }
            }


            Log.e("params",postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();

            }
            else {

                return new String("false : "+responseCode);
            }
        }
        catch(Exception e){

            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("HASIL SEND POST : ", result);
       if(data == DataType.COMIC_GENRE){
            if(ga != null){
                ga.readDataFinish(result);
            }

       }else if(data == DataType.COMIC_POPULAR){
           if(pa != null){
               pa.readDataFinish(result);
           }
       }else if(data == DataType.COMIC_NEWEST){
           if(na != null){
               na.readDataFinish(result);
           }
       }else if(data == DataType.COMIC_FAVORITE){
           if(fa != null){
               fa.readDataFinish(result);
           }
       }else if(data == DataType.SEND_FAVORITE){
           if(ca != null){
               ca.refreshFavorite(result);
           }
       }else if(data == DataType.SEND_UNFAVORITE){
           if(ca != null){
               ca.refreshFavorite(result);
           }
       }else if(data == DataType.EPISODE){
           if(ca != null){
               ca.readDataFinish(result);
           }
       }else if(data == DataType.SEND_RATING){
           if(ca != null){
               ca.refreshRating(result);
           }
       }else if(data == DataType.GET_FAVORITE_RATINGS){
           if(ca != null){
               ca.readFavorite(result);
           }
       }else if(data == DataType.Top_Comment){
            if(ra != null){
                ra.readDataFinish(result);
            }
       }else if(data == DataType.Comment){
            if(coa != null){
                coa.readDataFinish(result);
            }
       }else if(data == DataType.Send_Comment){
            if(coa != null){
                if(result.equals("SUCCESS")){
                    coa.refreshComment();
                }else {
                    //   Toast.makeText(ca, "FAIL", Toast.LENGTH_SHORT).show();
                }
            }
       }else if(data == DataType.Like_Comment_Comment){
            if(coa != null){
                if(result.equals("SUCCESS")){
                    coa.refreshComment();
                }else {
                    //   Toast.makeText(ca, "FAIL", Toast.LENGTH_SHORT).show();
                }
            }
       }else if(data == DataType.Like_Comment_Reading){
           if(ra != null){
               if(result.equals("SUCCESS")){
                   ra.refreshComment();
               }else {
                   //   Toast.makeText(ca, "FAIL", Toast.LENGTH_SHORT).show();
               }
           }
       }else if(data == DataType.Unlike_Comment_Comment){
           if(coa != null){
               if(result.equals("SUCCESS")){
                   coa.refreshComment();
               }else {
                   //   Toast.makeText(ca, "FAIL", Toast.LENGTH_SHORT).show();
               }
           }
       }else if(data == DataType.Unlike_Comment_Reading){
           if(ra != null){
               if(result.equals("SUCCESS")){
                   ra.refreshComment();
               }else {
                   //   Toast.makeText(ca, "FAIL", Toast.LENGTH_SHORT).show();
               }
           }
       }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}

