package com.example.recyclerviewpractice.model;

import android.os.Parcel;

import java.io.Serializable;


public class CardItem implements Serializable {

    private String name;
    private String rarity;
    private String type;
    private String set;
    private String setname;
    private String text;
    private String multiVerseId;

    public CardItem(String name, String rarity, String type, String set, String setname, String text, String multiVerseId) {
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.set = set;
        this.setname = setname;
        this.text = text;
        this.multiVerseId = multiVerseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetname() {
        return setname;
    }

    public void setSetname(String setname) {
        this.setname = setname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMultiVerseId() {
        return multiVerseId;
    }

    public void setMultiVerseId(String multiVerseId) {
        this.multiVerseId = multiVerseId;
    }
}