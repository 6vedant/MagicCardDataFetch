package com.example.recyclerviewpractice.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class APIFetchData {
    private static final String TAG = "APIFetchData";
    Context context;

    public APIFetchData(Context context) {
        this.context = context;
    }

    public String fetchDataFromUrl(String url) {

        StringBuilder content = new StringBuilder();

        try {
            URL u = new URL(url);
            //estbalishing connection to that url
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            Log.d(TAG, "TAG: " + uc.getResponseCode());


            if (uc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //fetch the data and append it to the string
                InputStream is = uc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }

            } else {
                throw new IOException(uc.getResponseMessage());
            }


        } catch (Exception e) {
            Log.d(TAG, e.getMessage() + " Exception: " + e.toString());

            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        return content.toString();
    }

}
