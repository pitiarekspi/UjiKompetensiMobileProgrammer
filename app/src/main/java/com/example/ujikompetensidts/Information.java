package com.example.ujikompetensidts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}