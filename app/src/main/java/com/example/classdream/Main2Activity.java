package com.example.classdream;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.karan.churi.PermissionManager.PermissionManager;

import java.text.SimpleDateFormat;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    LinearLayout calculator,classroom,dictionary,converter;
    DrawerLayout drawer;

   GoogleApiClient df;


    TextView time,day;
    Thread timerun ;
    NavigationView navigationView;
    ImageView unitimage,classimage,dictionaryimage,calculatorimage;

    PermissionManager permissionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.acitivity2);


        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);







        calculator = findViewById(R.id.calculator1);
        classroom  = findViewById(R.id.classroom);
        dictionary = findViewById(R.id.dictionar1);
        converter = findViewById(R.id.unitconverter);
        time = findViewById(R.id.time);
        day = findViewById(R.id.day);
        unitimage = findViewById(R.id.unitconvertorimage);
        classimage= findViewById(R.id.classroomimage);
        dictionaryimage= findViewById(R.id.dictionaryimage);
        calculatorimage= findViewById(R.id.calculatorimage);
        Glide.with(getApplicationContext()).load(R.drawable.clasroomg).into(classimage);
        Glide.with(getApplicationContext()).load(R.drawable.unit1convertor).into(unitimage);
        Glide.with(getApplicationContext()).load(R.drawable.book).into(dictionaryimage);
        Glide.with(getApplicationContext()).load(R.drawable.calculator123).into(calculatorimage);











        timerun = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss a");
                                String dateString1 = sdf1.format(date);
                                time.setText(dateString1);
                                SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd yyyy");
                                String dateString2 = sdf2.format(date);
                                day.setText(dateString2);

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

    timerun.start();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();



       navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_camera);

        navigationView.setItemIconTintList(null);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);



        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start1 = new Intent(getApplicationContext(), Mainact.class);
                startActivity(start1);

            }
        });
        classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start2 = new Intent(getApplicationContext(),LoginActivity_login.class);
                startActivity(start2);
            }
        });
        dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent start3 = new Intent(getApplicationContext(),dictionary.class);
                startActivity(start3);
            }
        });
        converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start3 = new Intent(getApplicationContext(),UnitCoverter.class);
                startActivity(start3);
            }
        });




    }
    public  boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


              drawer.openDrawer(GravityCompat.END);
              return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_camera)
        {
            Intent start3 = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(start3);
        }
        else if (id == R.id.nav_gallery)
        {
            Intent start3 = new Intent(getApplicationContext(),LoginActivity_login.class);
            startActivity(start3);
        }
        else if (id == R.id.nav_slideshow)
        {
            Intent start3 = new Intent(getApplicationContext(),dictionary.class);
            startActivity(start3);

        }
        else if (id == R.id.nav_manage)
        {
            Intent start3 = new Intent(getApplicationContext(),Mainact.class);
            startActivity(start3);
        }
        else if (id == R.id.nav_send)
        {
            Intent start3 = new Intent(getApplicationContext(),dictionary.class);
            startActivity(start3);
        }
        else if (id == R.id.unit)
        {
            Intent start3 = new Intent(getApplicationContext(),UnitCoverter.class);
            startActivity(start3);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        navigationView.setCheckedItem(R.id.nav_camera);
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode,permissions,grantResults);
    }
}
