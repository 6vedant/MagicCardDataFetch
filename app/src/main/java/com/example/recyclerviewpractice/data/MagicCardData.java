package com.example.recyclerviewpractice.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewpractice.R;
import com.example.recyclerviewpractice.adapter.MagicCardRecyclerAdapter;
import com.example.recyclerviewpractice.model.CardItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MagicCardData {
    private static final String url = "https://api.magicthegathering.io/v1/cards";
    private static final String TAG = "MagicCardData";
    public static ArrayList<CardItem> cardItems = new ArrayList<>();
    String out;
    Context context;
    ProgressBar progressBar;
    RecyclerView recyclerview;

    public MagicCardData(Context context, ProgressBar progressBar, RecyclerView recyclerview) {
        this.context = context;
        this.progressBar = progressBar;
        this.recyclerview = recyclerview;

    }

    public void loadData() {
        cardItems.clear();
        progressBar.setVisibility(View.VISIBLE);

        Handler mainHandler = new Handler(Looper.getMainLooper());


//        WebRunnable webRunnable = new WebRunnable("https://api.magicthegathering.io/v1/cards");
//        //WebRunnable webRunnable = new WebRunnable("https://api.magicthegathering.io/v1/cards?page=" + 1000);
//        //deactivate
//        new Thread(webRunnable).start();

        APIFetchData fetchData = new APIFetchData(context);
        String dataStr = fetchData.fetchDataFromUrl(url);
        Log.d("Data", dataStr);
        ///parsing the data
        //  making it into the forms of json format
        if (!dataStr.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(dataStr);
                JSONArray cardsArray = obj.getJSONArray("cards");

                for (int i = 0; i < cardsArray.length(); i++) {
                    JSONObject currObj = cardsArray.getJSONObject(i);

                    Log.d(TAG, "Index: " + i);
                    // name, rarity, type, set, setname, text, multiverseid
                    try {
                        String currName = (String) currObj.getString("name");
                        String currRarity = (String) currObj.getString("rarity");
                        String currType = (String) currObj.getString("type");
                        String currSet = (String) currObj.getString("set");
                        String currSetName = (String) currObj.getString("setName");
                        String currText = (String) currObj.getString("text");
                        String multiVerseId = (String) currObj.getString("layout");
                        //create a model and populate data from the current json object
                        CardItem cardItem = new CardItem(currName, currSet, currRarity, currType, currSetName, currText, multiVerseId);
                        cardItems.add(cardItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                mainHandler.post(() -> {

                    if (cardItems.size() > 0) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        recyclerview.setLayoutManager(linearLayoutManager);

                        MagicCardRecyclerAdapter magicCardRecyclerAdapter = new MagicCardRecyclerAdapter(context, cardItems);
                        recyclerview.setAdapter(magicCardRecyclerAdapter);

                    } else {
                        Toast.makeText(context, "Empty size.", Toast.LENGTH_SHORT).show();

                    }
                    progressBar.setVisibility(View.GONE);
                });
            } catch (Exception e) {

                Log.d("MagicCardData", "Error: " + e.getMessage());
            }


        }


    }

    class WebRunnable implements Runnable {

        private static final String TAG = "MagicCardData";
        URL url;

        WebRunnable(String url) {
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                int status = urlConnection.getResponseCode();
                urlConnection.setRequestMethod("GET");

                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                Handler mainHandler = new Handler(Looper.getMainLooper());
                out = "";

                if (scanner.hasNext()) {

                    JSONObject root = new JSONObject(scanner.next());
                    JSONArray cards = root.getJSONArray("cards");
                    Log.d(TAG, "Size: of array cards: " + cards.length());

                    if (cards.length() == 0) {
                        //Toast.makeText(getApplicationContext(), R.string.err_no_cards, Toast.LENGTH_SHORT).show();

                        //den handler braucht man wenn man zwischen Klassen kommuniziert
                        //handler = the middle man
                        mainHandler.post(() -> {
                            Toast.makeText(context, R.string.err_no_cards, Toast.LENGTH_SHORT).show();
                        });
                    }

                    Toast.makeText(context, "Size: " + cards.length(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < cards.length(); i++) {
                        JSONObject card = cards.getJSONObject(i);
                        JSONObject currObj = card;

//                        CardsGatheringMessage tm = new CardsGatheringMessage(card);
//                        out += tm.getMessageData();

                        try {
                            String currName = (String) currObj.getString("name");
                            String currRarity = (String) currObj.getString("rarity");
                            String currType = (String) currObj.getString("type");
                            String currSet = (String) currObj.getString("set");
                            String currSetName = (String) currObj.getString("setName");
                            String currText = (String) currObj.getString("text");
                            String multiVerseId = (String) currObj.getString("layout");
                            //create a model and populate data from the current json object
                            CardItem cardItem = new CardItem(currName, currSet, currRarity, currType, currSetName, currText, multiVerseId);
                            cardItems.add(cardItem);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    String finalOut = out;
                    Log.d(TAG, "Size: of prototypes" + cardItems.size());
                    mainHandler.post(() -> {
                        if (cardItems.size() > 0) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            recyclerview.setLayoutManager(linearLayoutManager);

                            MagicCardRecyclerAdapter magicCardRecyclerAdapter = new MagicCardRecyclerAdapter(context, cardItems);
                            recyclerview.setAdapter(magicCardRecyclerAdapter);

                        } else {
                            Toast.makeText(context, "Empty size.", Toast.LENGTH_SHORT).show();

                        }
                        progressBar.setVisibility(View.GONE);

                    });

                }
            } catch (JSONException jex) {
                Log.d(TAG, "JSon: " + jex.getMessage());
            } catch (IOException ioex) {
                // print to console
                ioex.printStackTrace();

                // read error codes and dsiplay for the given bad request


                String errmsg = ioex.getMessage();

                String msg = "";

                if (errmsg.contains("400")) {
                    msg = "We could not process that action, see details below: " + ioex.getMessage();
                } else if (errmsg.contains("403")) {
                    msg = "You exceeded the rate limit: " + ioex.getMessage();
                } else if (errmsg.contains("404")) {
                    msg = "The requested resource could not be found: " + ioex.getMessage();

                } else if (errmsg.contains("500")) {
                    msg = "We had a problem with our server. Please try again later: " + ioex.getMessage();

                } else if (errmsg.contains("503")) {
                    msg = "We are temporarily offline for maintenance. Please try again later: " + ioex.getMessage();

                } else {
                    msg = "There was a problem with your request, see details: " + ioex.getMessage();

                }

                // build message for user and display the toast
                displayExceptionMessage(msg);
            } catch (RuntimeException rex) {
                // general Exception, always
            } catch (Exception ex) {

                // print to console
                ex.printStackTrace();

                // build message for user and display the toast
                String msg = "There has been an error in your request, see details below: " + ex.getMessage();
                displayExceptionMessage(msg);
            }
        }


        public void displayExceptionMessage(String msg) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
