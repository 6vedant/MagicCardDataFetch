package com.example.recyclerviewpractice.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclerviewpractice.R;
import com.example.recyclerviewpractice.model.CardItem;

public class DetailActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CardItem cardItem = (CardItem) getIntent().getSerializableExtra("card_detail");

        try {
            getSupportActionBar().setTitle(cardItem.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        textView = (TextView) findViewById(R.id.textview_detail);

        textView.setText("Name: " + cardItem.getName() + "\nText: " + cardItem.getText() + "\nType: " + cardItem.getType() + "\nSet: " + cardItem.getSet() + "\nSetName: " + cardItem.getSetname() + "\nMultiVerseId: " + cardItem.getMultiVerseId() + "\nRarity: " + cardItem.getRarity());


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}