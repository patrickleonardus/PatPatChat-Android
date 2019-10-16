package com.example.patpatchat.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.patpatchat.HomeFragment;

import static android.content.Context.MODE_PRIVATE;

public class Messages {

    private String fromId, text, toId;
    private Double timestamp;

    public SharedPreferences sharedPreferences;
    public Context context;

    public Messages() {

    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }


    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String chatPartnerId(){

        sharedPreferences = context.getSharedPreferences("SaveData",MODE_PRIVATE);
        String uid = sharedPreferences.getString("uiduser",null);

        if(fromId == uid){
            return toId;
        }

        else {
            return fromId;
        }
    }
}
