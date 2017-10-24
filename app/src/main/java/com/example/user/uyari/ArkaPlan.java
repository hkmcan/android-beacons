package com.example.user.uyari;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 7.08.2017.
 */

class ArkaPlan extends AsyncTask<String,String,CustomResponse> {

    private TaskListener listener;

    public ArkaPlan(TaskListener listener){
        this.listener = listener;
    }

    protected CustomResponse doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader br = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                String message = "";
                String satir;
                while ((satir = br.readLine()) != null) {
                    //Log.d("satir:", satir);
                    message += satir;
                }
                return new CustomResponse(message, connection.getResponseCode());
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null)
                connection.disconnect();
        }
        return null;
    }
    protected  void onPostExecute(CustomResponse s){
        //Log.d("postExceutetangelen",s);
        if(s != null)
            listener.onTaskCompleted(s);
    }

}