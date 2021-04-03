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

import com.example.recyclerviewpractice.adapter.MagicCardRecyclerAdapter;
import com.example.recyclerviewpractice.data.db.SQLiteAppUrlDatabaseHandler;
import com.example.recyclerviewpractice.model.CardItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MagicCardData {
    private static final int flagSTART = 1;
    private static final int flagTRUNCATE = 2;

    private static final String url = "https://api.magicthegathering.io/v1/cards";
    private static final String TAG = "MagicCardData";
    public static ArrayList<CardItem> cardItems = new ArrayList<>();
    public int flag;
    String out;
    Context context;
    ProgressBar progressBar;
    RecyclerView recyclerview;

    public MagicCardData(Context context, ProgressBar progressBar, RecyclerView recyclerview, int flag) {
        this.context = context;
        this.progressBar = progressBar;
        this.recyclerview = recyclerview;
        this.flag = flag;

    }


    public void loadData() {
        cardItems.clear();
        progressBar.setVisibility(View.VISIBLE);

        SQLiteAppUrlDatabaseHandler sqLiteAppUrlDatabaseHandler = new SQLiteAppUrlDatabaseHandler(context);
        if (sqLiteAppUrlDatabaseHandler.getCardsCount() == 0 && flag == flagSTART) {
            // sqlite db is empty , pull data from api
            Log.d(TAG, "Sqlite DB is empty, getting data from api");
            loadDataFromAPI(sqLiteAppUrlDatabaseHandler);
        } else {
            Handler mainHandler = new Handler(Looper.getMainLooper());

            ArrayList<CardItem> cardItemsFromLocalDB = sqLiteAppUrlDatabaseHandler.getCardData();

            mainHandler.post(() -> {

                if (cardItemsFromLocalDB.size() > 0) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerview.setLayoutManager(linearLayoutManager);


                } else {
                    Toast.makeText(context, "Empty size.", Toast.LENGTH_SHORT).show();

                }
                MagicCardRecyclerAdapter magicCardRecyclerAdapter = new MagicCardRecyclerAdapter(context, cardItemsFromLocalDB);
                recyclerview.setAdapter(magicCardRecyclerAdapter);
                progressBar.setVisibility(View.GONE);
            });
        }


    }

    public void loadDataFromAPI(SQLiteAppUrlDatabaseHandler sqLiteAppUrlDatabaseHandler) {
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
                        CardItem cardItem = new CardItem("id:" + i, currName, currSet, currRarity, currType, currSetName, currText, multiVerseId);
                        cardItems.add(cardItem);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                // insert the card items to sqlite db
                for (CardItem cardItem : cardItems) {
                    sqLiteAppUrlDatabaseHandler.insertRow(cardItem);
                }

                Handler mainHandler = new Handler(Looper.getMainLooper());

                ArrayList<CardItem> cardItemsFromLocalDB = sqLiteAppUrlDatabaseHandler.getCardData();

                mainHandler.post(() -> {

                    if (cardItemsFromLocalDB.size() > 0) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        recyclerview.setLayoutManager(linearLayoutManager);

                        MagicCardRecyclerAdapter magicCardRecyclerAdapter = new MagicCardRecyclerAdapter(context, cardItemsFromLocalDB);
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
}
