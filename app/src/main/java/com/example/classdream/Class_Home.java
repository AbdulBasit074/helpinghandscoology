package com.example.classdream;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.operation.ListenComplete;
import com.google.firebase.storage.FirebaseStorage;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.xml.validation.ValidatorHandler;

public class Class_Home extends AppCompatActivity implements Simle_Post.Dialog_post_listner,NavigationView.OnNavigationItemSelectedListener{





    String Key;
    BoomMenuButton bmbe;
    RecyclerView mRecyclerView;
    User you;
    Post_Adobter Adobter;
    HashMap<String,String> member_email;

    String inszt;


    private List<Simple_Post> posts_list;

    private List<Simple_Post> posts_list_copy;

    String[] menu1 = {"Classes","Assigment","Material","Requests","Member","Chat","Profile","Logout","About"};
    private static int[] imageResources = new int[]{
            R.drawable.blackboard,
            R.drawable.assigment,
            R.drawable.material,
            R.drawable.request,
            R.drawable.member,
            R.drawable.chat,
            R.drawable.profile,
            R.drawable.logout,
            R.drawable.mainicon,
    };


    ImageView cirbar;

    TextView emailbr;

    CREATE_CLASS DATA;
    FirebaseAuth mAuth;
    ConstraintLayout layoutswap;
    String write="";
    FirebaseUser USER;

    ImageView request,notification;
    String email;

    NavigationView navigationView;
    DrawerLayout drawer;
    int tog;


    HashMap<String,String> request_list1;

    FirebaseDatabase database;

    FirebaseDatabase databasepost;

    DatabaseReference mDatabaseRef1;
    DatabaseReference mDatabaseRefpost;


    DatabaseReference mDatabaseRef2;
    DatabaseReference mDatabaseRef3;
    DatabaseReference mDatabaseReffetch;
    FirebaseDatabase databasefetch;

    DatabaseReference mDatabaseRef5;

    TextView class_name;

    ImageView add_post;

    User teacher_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_home_page2);
        Bundle bundle = getIntent().getExtras();
        Key = bundle.getString("key");
        member_email = new HashMap<>();
        posts_list = new ArrayList<>();
        posts_list_copy = new ArrayList<>();

        NAVIGATION_DRAWER();
        INITVIEW();
        MENU_DISPLAY();
        Glide.with(getApplicationContext()).load(R.drawable.plus1).into(add_post);
        HOME();


        add_post.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CALL_POST_DIALOG();
            }

        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent start = new Intent(getApplicationContext(),Member.class);
                if(request_list1.size()>1)
                {

                    start.putExtra("request",true);
                    start.putExtra("list",request_list1);
                }
                else
                {
                    start.putExtra("request",false);
                }

                start.putExtra("key",DATA.KEY);
                start.putExtra("class name",DATA.CLASS_NAME);
                startActivity(start);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void CALL_POST_DIALOG()
    {
        Simle_Post post = new Simle_Post("Instruction","POST",write);
        post.show(getSupportFragmentManager(),"Text Dialog");
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


                if(index==0)
                {
                    Intent start3 = new Intent(getApplicationContext(),classroom.class);
                    startActivity(start3);
                    finish();
                }
                if(index==1)
                {
                    Intent start3 = new Intent(getApplicationContext(),Assigment_Main.class);
                    start3.putExtra("key",Key);
                    start3.putExtra("teacher",true);
                    startActivity(start3);
                }
                if(index==2)
                {
                    Intent start1 = new Intent(getApplicationContext(),Material_Main.class);
                    start1.putExtra("key",Key);
                    start1.putExtra("teacher",true);
                    startActivity(start1);
                }
                if(index==3)
                {
                    Intent start = new Intent(getApplicationContext(),Member.class);
                    if(request_list1.size()>1)
                    {
                        start.putExtra("request",true);
                        start.putExtra("list",request_list1);
                    }
                    else
                    {
                        start.putExtra("request",false);
                    }
                    start.putExtra("key",DATA.KEY);
                    start.putExtra("class name",DATA.CLASS_NAME);
                    start.putExtra("teacher",true);

                    startActivity(start);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                }
                if(index==4)
                {

                    Intent startc = new Intent(getApplicationContext(),Member_CLASS.class);
                    startc.putExtra("key",DATA.KEY);
                    startc.putExtra("class name",DATA.CLASS_NAME);
                    startc.putExtra("teacher",true);
                    startActivity(startc);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                }
                if(index==5)
                {
                    Intent start3 = new Intent(getApplicationContext(),ChatActivity.class);
                    start3.putExtra("key",Key);
                    startActivity(start3);

                }
                if(index==6)
                {
                    Intent open1 = new Intent(getApplicationContext(),Profile.class);
                    open1.putExtra("user_id",you.USER_ID);
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


    private void NAVIGATION_DRAWER() {


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
        if(tog==0)
        {

        }

    }



    private void INITVIEW()
    {
        bmbe = findViewById(R.id.bmb);
        add_post = findViewById(R.id.add_post);
        request = findViewById(R.id.request);
        layoutswap = findViewById(R.id.homeswap);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
        Adobter = new Post_Adobter(posts_list,getApplicationContext());
        mRecyclerView.setAdapter(Adobter);


    }
    private void HOME()
    {
            USER_FIREBASE();
            GET_CLASS_DATA();
            GET_CLASS_REQUEST();
            SET_REQUEST_ICON();
            FETCH_POST();

    }

    private void FETCH_POST() {
        databasefetch = FirebaseDatabase.getInstance();
        mDatabaseReffetch = databasefetch.getReference().child("CLASSES").child(Key).child("SIMPLEPOST");
        mDatabaseReffetch.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                if (dataSnapshot != null && dataSnapshot.getValue() != null)
                {
                    try
                    {
                        Simple_Post model = dataSnapshot.getValue(Simple_Post.class);
                        posts_list_copy.add(model);
                        posts_list.clear();
                        posts_list.addAll(posts_list_copy) ;
                        Collections.reverse(posts_list);
                     mRecyclerView.scrollToPosition(posts_list.size() - posts_list.size());
                     Adobter.notifyItemInserted(posts_list.size() - 1);
                     Adobter.notifyDataSetChanged();

                    }
                    catch (Exception ex)
                    {
                        //Log.e(TAG, ex.getMessage());
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                Simple_Post model = dataSnapshot.getValue(Simple_Post.class);
                posts_list_copy.add(model);
                posts_list.clear();
                posts_list.addAll(posts_list_copy) ;
                ;                        Collections.reverse(posts_list);
                mRecyclerView.scrollToPosition(posts_list.size() - 1);
                Adobter.notifyItemInserted(posts_list.size() - 1);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
              //  Log.d(TAG, databaseError.getMessage());
            }
        });




    }

    private void SET_REQUEST_ICON()
    {
        if(request_list1.size()>1)
        {
            request.setImageResource(R.drawable.yes_request);
        }
        else
        {
            request.setImageResource(R.drawable.no_request);
        }

    }

    private void GET_CLASS_REQUEST()
    {
        request_list1 = new HashMap<String,String>();

        database = FirebaseDatabase.getInstance();
        mDatabaseRef1 = database.getReference().child("CLASSES").child(Key).child("REQUEST LIST");
       mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                request_list1 = (HashMap<String, String>) dataSnapshot.getValue();
                SET_REQUEST_ICON();
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
    private void GET_CLASS_DATA()
    {

        database = FirebaseDatabase.getInstance();
        mDatabaseRef2 = database.getReference().child("CLASSES").child(Key).child("CLASS INFO");

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
                        teacher_detail = dataSnapshot.getValue(User.class);
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

    private void USER_FIREBASE() {
        mAuth = FirebaseAuth.getInstance();
        USER = mAuth.getCurrentUser();

    }

////////////////////////////////SIMPLE POST GETTING VALUE///////////////////////////////////////////
    @Override
    public void ApplyText(String title, String instruction)
    {
        if(title.isEmpty())
        {
            CALL_POST_DIALOG();
        }
        else if(instruction.isEmpty())
        {
            CALL_POST_DIALOG();
        }
        else
        {
            UPLOAD_POST_DATABASE(title,instruction);
            inszt = instruction;
            MEMBER_LIST();

        }
    }
    private void MEMBER_LIST()
    {

        member_email = new HashMap<String, String>();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef1 = database.getReference().child("CLASSES").child(Key).child("MEMBER LIST");
        mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                member_email = (HashMap<String, String>) dataSnapshot.getValue();
                member_email.remove("EMPTY");
                GET__USER_DETAIL();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void GET__USER_DETAIL()
    {

        for(Map.Entry<String, String> entry: member_email.entrySet())
        {
            database = FirebaseDatabase.getInstance();
            mDatabaseRef2 = database.getReference().child("USERS").child(entry.getKey());
            mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {

                    User data = dataSnapshot.getValue(User.class);
                    OneSignal.sendTag("User_ID",data.EMAIL);
                    SEND_NOTIFICATION notif = new SEND_NOTIFICATION();
                    notif.SendNotification("Annoucment From  "+DATA.CLASS_NAME+" : "+inszt,
                            data.EMAIL,getApplicationContext());

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
        }
    }


    private void UPLOAD_POST_DATABASE(String title, String instruction)
    {
        Simple_Post post = new Simple_Post();
        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");
        String time = timeFormat.format(current).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(current).toString();


        post.title = title;
        post.instruction=instruction;
        post.date = date;
        post.time = time;
        post.teacher_name = teacher_detail.NAME;
        post.teacher_profile = teacher_detail.PROFILE_IMAGE;
        databasepost = FirebaseDatabase.getInstance();
        mDatabaseRefpost = databasepost.getReference().child("CLASSES").child(Key).child("SIMPLEPOST");
        mDatabaseRefpost.push().setValue(post);
    }

    private void UPLOAD_POST(String title,String instruction)
    {
        String key_value="null";

        ///////////////////////////key//////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////////
        String teacher_name  = teacher_detail.NAME;
        String teacher_profile=teacher_detail.PROFILE_IMAGE;
        if(teacher_name.isEmpty())
        {
            teacher_name = "null";
        }
        if(teacher_profile.isEmpty())
        {
            teacher_profile = "null";
        }

        if(key_value.isEmpty())
        {
            key_value="null";
        }
        Simple_Post upload = new Simple_Post();

        ////////////////////////////////////////////////////////////////////////////////////////////

        upload.teacher_name=teacher_name;
        upload.teacher_profile=teacher_profile;
        ////////////////////////////////////////////////////////////////////////////////////////////
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

        if (id == R.id.Classs)
        {
            Toast.makeText(getApplicationContext(),"Already on Class "+class_name,Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.assign)
        {
            Intent start3 = new Intent(getApplicationContext(),Assigment_Main.class);
            start3.putExtra("key",Key);
            start3.putExtra("teacher",true);

            startActivity(start3);

        }
        else if (id == R.id.chat)
        {
            Intent start3 = new Intent(getApplicationContext(),ChatActivity.class);
            start3.putExtra("key",Key);
            startActivity(start3);

        }
        else if (id == R.id.mater)
        {
            Intent start1 = new Intent(getApplicationContext(),Material_Main.class);
            start1.putExtra("key",Key);
            start1.putExtra("teacher",true);
            startActivity(start1);
        }
        else if (id == R.id.mem)
        {
            Intent start = new Intent(getApplicationContext(),Member_CLASS.class);
            start.putExtra("key",DATA.KEY);
            start.putExtra("class name",DATA.CLASS_NAME);
            start.putExtra("teacher",true);

            startActivity(start);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        else if (id == R.id.classes)
        {
            Intent start3 = new Intent(getApplicationContext(),classroom.class);
            startActivity(start3);
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



}