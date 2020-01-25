package com.example.classdream;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FULL_VIEW extends AppCompatActivity {
    ImageView image;
    String image_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__view);
        Bundle bundle = getIntent().getExtras();
        image_uri = bundle.getString("uri");
        image = findViewById(R.id.full_view);
        Glide.with(getApplicationContext()).load(image_uri).into(image);
    }
}
