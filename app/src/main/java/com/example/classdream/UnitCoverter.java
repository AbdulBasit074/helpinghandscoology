package com.example.classdream;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class UnitCoverter extends AppCompatActivity {

    LinearLayout area,length1,weight,temperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_coverter);
        ///////////////////////////////////////////////
        ///////////////////////////////////////////////
        area = findViewById(R.id.area);
        length1 = findViewById(R.id.length);
        weight = findViewById(R.id.weight);
        temperature = findViewById(R.id.tempearture);
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i=new Intent(getApplicationContext(),UnitArea.class);
                startActivity(i);

            }
        });
       length1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i=new Intent(getApplicationContext(),UnitLength.class);
                startActivity(i);

            }
        });
        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i=new Intent(getApplicationContext(),UnitWeight.class);
                startActivity(i);

            }
        });
       temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i=new Intent(getApplicationContext(),UnitTemperature.class);
                startActivity(i);

            }
        });



    }

    public void onClick(View v)
    {
        Intent i;
        switch(v.getId())
        {
            case R.id.area:
                break;
            case R.id.length:
                i=new Intent(this,UnitLength.class);
                startActivity(i);
                break;
            case R.id.weight:
                i=new Intent(this,UnitWeight.class);
                startActivity(i);
                break;
            case R.id.tempearture:
                i=new Intent(this,UnitTemperature.class);
                startActivity(i);
                break;
        }
    }

}
