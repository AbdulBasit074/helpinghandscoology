package com.example.classdream;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class  Mainact extends AppCompatActivity {

   LinearLayout Scientific,standard,unit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact);
        Scientific = findViewById(R.id.sn);
        standard =findViewById(R.id.st);
        unit = findViewById(R.id.un);
        Scientific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start1 = new Intent(getApplicationContext(),ScientificCal.class);
                startActivity(start1);
            }
        });
        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start2 = new Intent(getApplicationContext(),StandardCal.class);
            startActivity(start2);

            }
        });
        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start3 = new Intent(getApplicationContext(),UnitCoverter.class);
            startActivity(start3);
            }
        });

    }
}
