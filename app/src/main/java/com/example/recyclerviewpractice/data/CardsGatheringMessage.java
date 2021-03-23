package com.example.recyclerviewpractice.data;

import org.json.JSONException;
import org.json.JSONObject;

public class CardsGatheringMessage {
    private String name;
    private String type;
    private String rarity;
    private String colors;

    public CardsGatheringMessage(JSONObject card){

        try{
            //name, type, rarity und colors
            this.name = card.getString("name");
            this.type = card.getString( "type");
            this.rarity = card.getString("rarity");
            this.colors = card.getString("colors");

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getMessageData() {
        return "name of card: " + name + "\ntype of card: " + type + rarity + colors;
    }
}
