package com.example.aldop.uas_webtoon;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by aldop on 12/10/2017.
 */

public class SendComment extends AsyncTask<String, Void, String> {
    protected void onPreExecute(){}

    CommentActivity ca;

    public void SendComment(int eps_id, int user_id, String comment, CommentActivity ca){
        this.ca = ca;
        String link = Connection.Ip + "sendComment.php";
        execute("eps_id", eps_id+"", "user_id", user_id+"", "comment", comment, link);
    }


    @Override
    protected String doInBackground(String... arg0) {
        try {

         //   URL url = new URL(arg0[6]); // here is your URL path
            URL url = new URL(arg0[arg0.length - 1]);

            JSONObject postDataParams = new JSONObject();
          /*  postDataParams.put(arg0[0], arg0[1]);
            postDataParams.put(arg0[2], arg0[3]);
            postDataParams.put(arg0[4], arg0[5]);*/

            for(int i = 0; i < arg0.length - 1; i += 2){
                postDataParams.put(arg0[i], arg0[i+1]);
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
                Toast.makeText(GenreActivity.instance.getApplicationContext() , "ELSE",
                        Toast.LENGTH_LONG).show();
                return new String("false : "+responseCode);
            }
        }
        catch(Exception e){
          /*  Toast.makeText(context , "EXCEPTION",
                    Toast.LENGTH_LONG).show();*/
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(ca != null){
            Toast.makeText(ca, result, Toast.LENGTH_SHORT).show();
            if(result.equals("SUCCESS")){
                ca.refreshComment();
            }else {
             //   Toast.makeText(ca, "FAIL", Toast.LENGTH_SHORT).show();
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
