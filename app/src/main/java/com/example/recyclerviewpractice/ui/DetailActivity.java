package com.example.recyclerviewpractice.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclerviewpractice.R;
import com.example.recyclerviewpractice.data.db.SQLiteAppUrlDatabaseHandler;
import com.example.recyclerviewpractice.model.CardItem;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    TextView textView;
    CardItem cardItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        cardItem = (CardItem) getIntent().getSerializableExtra("card_detail");
        SQLiteAppUrlDatabaseHandler sqLiteAppUrlDatabaseHandler = new SQLiteAppUrlDatabaseHandler(DetailActivity.this);
        cardItem = sqLiteAppUrlDatabaseHandler.getCardItem(cardItem.getId());
        try {
            getSupportActionBar().setTitle(cardItem.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        textView = (TextView) findViewById(R.id.textview_detail);

        textView.setText("Name: " + cardItem.getName() + "\nText: " + cardItem.getText() + "\nType: " + cardItem.getType() + "\nSet: " + cardItem.getSet() + "\nSetName: " + cardItem.getSetname() + "\nMultiVerseId: " + cardItem.getMultiVerseId() + "\nRarity: " + cardItem.getRarity());


        findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteAppUrlDatabaseHandler sqLiteAppUrlDatabaseHandler = new SQLiteAppUrlDatabaseHandler(DetailActivity.this);
                sqLiteAppUrlDatabaseHandler.deleteCard(cardItem);

                Toast.makeText(getApplicationContext(), "" + cardItem.getName() + " : " + cardItem.getId() + " deleted.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditCardDataActivity.class);
                intent.putExtra("card_item", cardItem);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Refresh your stuff here
        SQLiteAppUrlDatabaseHandler sqLiteAppUrlDatabaseHandler = new SQLiteAppUrlDatabaseHandler(DetailActivity.this);
        if (cardItem != null) {
            CardItem updatedCardItem = sqLiteAppUrlDatabaseHandler.getCardItem(cardItem.getId());
            if (textView != null) {
                textView.setText("Name: " + updatedCardItem.getName() + "\nText: " + updatedCardItem.getText() + "\nType: " + updatedCardItem.getType() + "\nSet: " + updatedCardItem.getSet() + "\nSetName: " + updatedCardItem.getSetname() + "\nMultiVerseId: " + updatedCardItem.getMultiVerseId() + "\nRarity: " + updatedCardItem.getRarity());
            }
            Objects.requireNonNull(getSupportActionBar()).setTitle(updatedCardItem.getName());

        }

    }
}