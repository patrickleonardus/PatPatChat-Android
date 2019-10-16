package com.example.patpatchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public class ChatLogActivity extends AppCompatActivity {

    Toolbar toolbar;

    String titleTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_log);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setNavBar();

    }

    public void setNavBar(){

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            titleTemp = String.valueOf(bundle.get("title"));
            setTitle(titleTemp);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }
}
