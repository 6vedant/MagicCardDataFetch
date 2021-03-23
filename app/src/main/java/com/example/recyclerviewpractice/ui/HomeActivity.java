package com.example.recyclerviewpractice.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewpractice.R;
import com.example.recyclerviewpractice.data.MagicCardData;

public class HomeActivity extends AppCompatActivity {

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

                    MagicCardData magicCardData = new MagicCardData(HomeActivity.this, progressBar, recyclerView);
                    magicCardData.loadData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }
}