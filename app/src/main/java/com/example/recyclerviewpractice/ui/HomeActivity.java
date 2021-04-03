package com.example.recyclerviewpractice.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewpractice.R;
import com.example.recyclerviewpractice.adapter.MagicCardRecyclerAdapter;
import com.example.recyclerviewpractice.data.MagicCardData;
import com.example.recyclerviewpractice.data.db.SQLiteAppUrlDatabaseHandler;

public class HomeActivity extends AppCompatActivity {

    private static final int flagSTART = 1;
    private static final int flagTRUNCATE = 2;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progress);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    MagicCardData magicCardData = new MagicCardData(HomeActivity.this, progressBar, recyclerView, flagSTART);
                    magicCardData.loadData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        findViewById(R.id.truncate_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete the database and notifify the recyclerview adapter
                SQLiteAppUrlDatabaseHandler sqLiteAppUrlDatabaseHandler = new SQLiteAppUrlDatabaseHandler(HomeActivity.this);
                MagicCardRecyclerAdapter magicCardRecyclerAdapter = (MagicCardRecyclerAdapter) recyclerView.getAdapter();
                if (magicCardRecyclerAdapter != null && sqLiteAppUrlDatabaseHandler.getCardsCount() != 0) {
                    sqLiteAppUrlDatabaseHandler.deleteAllCardsRecords();
                    int cardsCount = sqLiteAppUrlDatabaseHandler.getCardsCount();
                    magicCardRecyclerAdapter.notifyItemRangeRemoved(0, cardsCount);
                    Toast.makeText(getApplicationContext(), "Data truncated", Toast.LENGTH_SHORT).show();
                    try {

                        MagicCardData magicCardData = new MagicCardData(HomeActivity.this, progressBar, recyclerView, flagTRUNCATE);
                        magicCardData.loadData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to truncate the data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //Refresh your stuff here
        SQLiteAppUrlDatabaseHandler sqLiteAppUrlDatabaseHandler = new SQLiteAppUrlDatabaseHandler(HomeActivity.this);
        MagicCardRecyclerAdapter magicCardRecyclerAdapter = (MagicCardRecyclerAdapter) recyclerView.getAdapter();
        if(magicCardRecyclerAdapter != null){
            try {

                MagicCardData magicCardData = new MagicCardData(HomeActivity.this, progressBar, recyclerView, flagSTART);
                magicCardData.loadData();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}