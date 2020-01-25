package com.example.classdream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assigment_Part2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseUser USER;
    FirebaseAuth mauth1;
    DatabaseReference mDatabaseRef2,mDatabaseRef3,mDatabaseRef5,mDatabaseReffetch,mDatabaseRef1;
    FirebaseDatabase database;
    CREATE_CLASS DATA;
    User you;






    HashMap<String,String> member_email;


    /////////////////////////////////////////////BASIC SETUP////////////////////////////////////////
    FILE_INFORMATION_ASSIGMENTS file_detail;
    String Key_d;
    Boolean teacher;



    NavigationView navigationView;
    DrawerLayout drawer;
    BoomMenuButton bmbe;
    TextView emailbr;
    ImageView cirbar;
    String[] menu1 = {"Class","Assigment","Material","Classes","Member","Chat","Profile","Logout","About Class"};
    private static int[] imageResources = new int[]{
            R.drawable.blackboard,
            R.drawable.assigment,
            R.drawable.material,
            R.drawable.classroom,
            R.drawable.member,
            R.drawable.chat,
            R.drawable.profile,
            R.drawable.logout,
            R.drawable.mainicon,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment__part);
        NAVIGATION_DRAWER();
        member_email = new HashMap<>();
        INITVIEW();
        GET_BUNDLE();

        MENU_DISPLAY();
        USER_FIREBASE();
        GET_CLASS_DETAIL();

    }

    private void GET_BUNDLE()
    {
        Bundle bundle = getIntent().getExtras();
        Key_d  = bundle.getString("key");
        teacher = bundle.getBoolean("teacher");





       // file_detail = (FILE_INFORMATION_ASSIGMENTS) getIntent().getSerializableExtra("assigment");

       // Gson gson = new Gson();
        //file_detail = gson.fromJson(getIntent().getStringExtra("assigment"), (Type) FILE_INFORMATION_ASSIGMENTS.class);
      //Toast.makeText(getApplicationContext()," SELECTED "+file_detail.FILE_NAME,Toast.LENGTH_LONG).show();

    }

    private void INITVIEW()
    {
        bmbe = findViewById(R.id.bmb);
    }
   public void NAVIGATION_DRAWER()
   {

        View header  =((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        //View headerw  =((NavigationView)findViewById(R.id.nav_view)).getHeaderView(1);
        emailbr = header.findViewById(R.id.emailbar);
        cirbar = header.findViewById(R.id.profilebar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.Classs);
        navigationView.setItemIconTintList(null);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }
    private void MENU_DISPLAY()
    {

        bmbe.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmbe.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        bmbe.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);

        for (int i = 0; i < bmbe.getPiecePlaceEnum().pieceNumber(); i++)
            bmbe.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(menu1[i],imageResources));
        bmbe.setUse3DTransformAnimation(true);
        bmbe.setDuration(300);

        bmbe.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                Toast.makeText(getApplicationContext()," SELECTED "+index,Toast.LENGTH_LONG).show();


                if(index==0)
                {
                    Intent ope ;
                    if(teacher)
                    {
                        ope =  new Intent(getApplicationContext(),Class_Home.class);
                    }
                    else
                    {
                        ope =  new Intent(getApplicationContext(),Class_Home_STUDENT.class);
                    }
                    ope.putExtra("key",Key_d);
                    startActivity(ope);
                    finish();

                }
                if(index==1)
                {
                }
                if(index==2)
                {
                    Intent start1 = new Intent(getApplicationContext(),Material_Main.class);
                    start1.putExtra("key",Key_d);
                    start1.putExtra("teacher",teacher);
                    startActivity(start1);
                }
                if(index==3)
                {
                    Intent start3 = new Intent(getApplicationContext(),classroom.class);
                    startActivity(start3);

                }
                if(index==4)
                {
                    Intent start = new Intent(getApplicationContext(),Member_CLASS.class);
                    start.putExtra("key",DATA.KEY);
                    start.putExtra("class name",DATA.CLASS_NAME);
                    start.putExtra("teacher",teacher);
                    startActivity(start);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                }
                if(index==5)
                {
                    Intent start3 = new Intent(getApplicationContext(),ChatActivity.class);
                    start3.putExtra("key",Key_d);
                    startActivity(start3);
                }
                if(index==6)
                {
                    Intent open1 = new Intent(getApplicationContext(),Profile.class);
                    open1.putExtra("user_id", you.USER_ID);
                    startActivity(open1);

                }
                if(index==7)
                {

                    FirebaseAuth.getInstance().signOut();
                    Intent start3 = new Intent(getApplicationContext(),LoginActivity_login.class);
                    startActivity(start3);
                    finish();

                }
                if(index==8)
                {

                }

            }

            @Override
            public void onBackgroundClick()
            {

            }

            @Override
            public void onBoomWillHide()
            {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow()
            {

            }
        });
    }

    ////////////////////////////////SIMPLE POST GETTING VALUE///////////////////////////////////////////


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
        else
        {
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

        if (id == R.id.Classs)
        {
            Intent ope ;
            if(teacher)
            {
                ope =  new Intent(getApplicationContext(),Class_Home.class);
            }
            else
            {
                ope =  new Intent(getApplicationContext(),Class_Home_STUDENT.class);
            }
            ope.putExtra("key",Key_d);
            startActivity(ope);
            finish();
        }
        else if (id == R.id.assign)
        {
            Toast.makeText(getApplicationContext(),"Already On Assigment Page",Toast.LENGTH_LONG).show();

        }
        else if (id == R.id.mater)
        {
            Intent start1 = new Intent(getApplicationContext(),Material_Main.class);
            start1.putExtra("key",Key_d);
            start1.putExtra("teacher",teacher);
            startActivity(start1);
        }
        else if (id == R.id.classes)
        {
            Intent start3 = new Intent(getApplicationContext(),classroom.class);
            startActivity(start3);

        }
        else if (id == R.id.mem)
        {
            Intent start = new Intent(getApplicationContext(),Member_CLASS.class);
            start.putExtra("key",DATA.KEY);
            start.putExtra("class name",DATA.CLASS_NAME);
            start.putExtra("teacher",teacher);

            startActivity(start);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        }
        else if (id == R.id.pro)
        {
            Intent open1 = new Intent(getApplicationContext(),Profile.class);
            open1.putExtra("user_id", you.USER_ID);
            startActivity(open1);
        }
        else if (id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent start3 = new Intent(getApplicationContext(),LoginActivity_login.class);
            startActivity(start3);
            finish();

        }
        else if (id == R.id.aboutclass)
        {

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

    private void USER_FIREBASE() {
        mauth1 = FirebaseAuth.getInstance();
        USER = mauth1.getCurrentUser();
    }
    private void GET_CLASS_DETAIL()
    {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef2 = database.getReference().child("CLASSES").child(Key_d).child("CLASS INFO");
        mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                DATA = dataSnapshot.getValue(CREATE_CLASS.class);
                SET_IN_SCREEN();
                database = FirebaseDatabase.getInstance();
                mDatabaseRef3 = database.getReference().child("USERS").child(DATA.TEACHER_ID);
                mDatabaseRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                mDatabaseRef5 = database.getReference().child("USERS").child(USER.getUid());
                mDatabaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        you  = dataSnapshot.getValue(User.class);
                        emailbr.setText(you.EMAIL);
                        Uri uri = Uri.parse(you.PROFILE_IMAGE);
                        Glide.with(getApplicationContext()).load(uri).placeholder(R.drawable.loading).into(cirbar);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void SET_IN_SCREEN()
    {
        getSupportActionBar().setTitle(DATA.CLASS_NAME);
    }


















}
