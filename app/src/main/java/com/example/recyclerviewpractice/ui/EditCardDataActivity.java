package com.example.recyclerviewpractice.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclerviewpractice.R;
import com.example.recyclerviewpractice.data.db.SQLiteAppUrlDatabaseHandler;
import com.example.recyclerviewpractice.model.CardItem;

public class EditCardDataActivity extends AppCompatActivity {

    EditText et_name, et_text, et_type, et_set, et_setname, et_multi_verse_id, et_rarity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_data);


        CardItem cardItem = (CardItem) getIntent().getSerializableExtra("card_item");
        try {
            getSupportActionBar().setTitle(cardItem.getName() + " Edit Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        et_name = (EditText) findViewById(R.id.et_name);
        et_text = (EditText) findViewById(R.id.et_text);
        et_type = (EditText) findViewById(R.id.et_type);
        et_set = (EditText) findViewById(R.id.et_set);
        et_setname = (EditText) findViewById(R.id.et_set_name);
        et_multi_verse_id = (EditText) findViewById(R.id.et_multi_verseid);
        et_rarity = (EditText) findViewById(R.id.et_rarity);

        et_name.setText(cardItem.getName());
        et_text.setText(cardItem.getText());
        et_type.setText(cardItem.getType());
        et_set.setText(cardItem.getSet());
        et_setname.setText(cardItem.getSetname());
        et_multi_verse_id.setText(cardItem.getMultiVerseId());
        et_rarity.setText(cardItem.getRarity());

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currName = et_name.getText().toString().trim();
                String currText = et_text.getText().toString().trim();
                String currType = et_type.getText().toString().trim();
                String currSet = et_set.getText().toString().trim();
                String currSetName = et_setname.getText().toString().trim();
                String currMultiVerseId = et_multi_verse_id.getText().toString().trim();
                String currRarity = et_rarity.getText().toString().trim();

                if (TextUtils.isEmpty(currName) || TextUtils.isEmpty(currText) ||
                        TextUtils.isEmpty(currType) || TextUtils.isEmpty(currSet) || TextUtils.isEmpty(currSetName) ||
                        TextUtils.isEmpty(currMultiVerseId) || TextUtils.isEmpty(currRarity)) {
                    Toast.makeText(getApplicationContext(), "one or more fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    CardItem updatedCardItem = new CardItem(cardItem.getId(), currName, currRarity, currType, currSet, currSetName, currText, currMultiVerseId);
                    SQLiteAppUrlDatabaseHandler db = new SQLiteAppUrlDatabaseHandler(EditCardDataActivity.this);
                    db.updateCard(updatedCardItem);
                    Toast.makeText(getApplicationContext(), "Updated details for card : " + cardItem.getId(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}