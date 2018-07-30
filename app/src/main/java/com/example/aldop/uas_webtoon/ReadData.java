package com.example.aldop.uas_webtoon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by andre on 5/1/2017.
 */

public class ReadData extends AsyncTask<String, String, String> {
    private ProgressDialog progressDialog;
    InputStream inputStream = null;
    GenreActivity ma;

    public ReadData(GenreActivity ma) {
        progressDialog = new ProgressDialog(ma);
        this.ma = ma;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Downloading your data...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ReadData.this.cancel(true);
            }
        });
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
          //  ma.readDataFinish(ma.getApplicationContext(), "Result" +  e.getMessage().toString());
        } catch (IOException e) {
            e.printStackTrace();
          //  ma.readDataFinish(ma.getApplicationContext(), "Result" +  e.getMessage().toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

        //ma.readDataFinish(ma.getApplicationContext(), result);
       // Toast.makeText(ma, result, Toast.LENGTH_SHORT).show();
        //return result;
    }
}
