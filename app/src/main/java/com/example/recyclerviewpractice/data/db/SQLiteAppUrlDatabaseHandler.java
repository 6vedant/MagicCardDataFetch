package com.example.recyclerviewpractice.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recyclerviewpractice.model.CardItem;

import java.util.ArrayList;

public class SQLiteAppUrlDatabaseHandler extends SQLiteOpenHelper {

    // db version
    private static final int DATABASE_VERSION = 1;

    // db name
    private static final String DATABASE_NAME = "cardDataDB";

    // db table name
    private static final String TABLE_APP_URL = "CARDS";

    // app_url table columns name
    private static final String KEY_ID = "id";
    private String NAME = "name";
    private String RARITY = "rarity";
    private String TYPE = "type";
    private String SET = "set_";
    private String SETNAME = "setname";
    private String TEXT = "text";
    private String MULTI_VERSE_ID = "multi_verse_id";


    public SQLiteAppUrlDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_APP_URL_TABLE = "CREATE TABLE " + TABLE_APP_URL +
                "(" + KEY_ID + " INTEGER_PRIMARY_KEY," + NAME + " TEXT, " +
                RARITY + " TEXT, " + TYPE + " TEXT, " + SET + " TEXT, " + SETNAME + " TEXT, " +
                TEXT + " TEXT, " + MULTI_VERSE_ID + " TEXT " + ")";

        sqLiteDatabase.execSQL(CREATE_APP_URL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_URL);
        // create the table again
        onCreate(sqLiteDatabase);
    }

    /*
        CRUD Operations
     */

    public void insertRow(CardItem cardItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, cardItem.getId());
        values.put(NAME, cardItem.getName());
        values.put(RARITY, cardItem.getRarity());
        values.put(TYPE, cardItem.getType());
        values.put(SET, cardItem.getSet());
        values.put(SETNAME, cardItem.getSetname());
        values.put(TEXT, cardItem.getText());
        values.put(MULTI_VERSE_ID, cardItem.getMultiVerseId());

        // insert into db
        db.insert(TABLE_APP_URL, null, values);
        db.close();
    }

    public CardItem getCardItem(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_APP_URL, new String[]{KEY_ID, NAME, RARITY, TYPE, SET, SETNAME, TEXT, MULTI_VERSE_ID},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        CardItem cardItem = new CardItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        return cardItem;
    }

    //get all data
    public ArrayList<CardItem> getCardData() {
        ArrayList<CardItem> result = new ArrayList<>();
        result.clear();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_APP_URL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        //looping through cursor
        if (cursor.moveToFirst()) {
            do {
                CardItem cardItem = new CardItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                result.add(cardItem);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public int updateCard(CardItem cardItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, cardItem.getId());
        values.put(NAME, cardItem.getName());
        values.put(RARITY, cardItem.getRarity());
        values.put(TYPE, cardItem.getType());
        values.put(SET, cardItem.getSet());
        values.put(SETNAME, cardItem.getSetname());
        values.put(TEXT, cardItem.getText());
        values.put(MULTI_VERSE_ID, cardItem.getMultiVerseId());

        // updating row
        return db.update(TABLE_APP_URL, values, KEY_ID + " = ?",
                new String[]{cardItem.getId()});

    }

    // Deleting single item
    public void deleteCard(CardItem cardItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APP_URL, KEY_ID + " = ?",
                new String[]{cardItem.getId()});
        db.close();
    }

    // Deleting all item
    public void deleteAllCardsRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APP_URL, null, null);
        db.close();
    }

    // Getting items count
    public int getCardsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APP_URL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

}
