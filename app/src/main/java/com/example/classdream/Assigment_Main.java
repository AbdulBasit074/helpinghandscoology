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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.database.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assigment_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    /////////////////////////////////////////////BASIC SETUP////////////////////////////////////////
    NavigationView navigationView;
    DrawerLayout drawer;
    BoomMenuButton bmbe;
    TextView emailbr;
    FirebaseAuth mauth1;

    DatabaseReference mDatabaseRef2,mDatabaseRef3,mDatabaseRef5,mDatabaseReffetch;
    FirebaseDatabase database;


    FirebaseUser USER;
    private List<FILE_INFORMATION_ASSIGMENTS> posts_list;
    private List<FILE_INFORMATION_ASSIGMENTS> posts_list_copy;

    Post_Assigment_Adobter Adobter;

    RecyclerView mRecyclerView;

    CREATE_CLASS DATA;

    ImageView cirbar;
    User you;
    String Key;
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
    TextView nam;
    TextView siz;
    ImageView icn;
    ImageView final_load;
    ImageView delete;
    ImageView upload;
    Boolean teacher ;
    LinearLayout upld_menu;
////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment__main);
        Bundle bundle = getIntent().getExtras();
        Key = bundle.getString("key");
        teacher = bundle.getBoolean("teacher");

        posts_list = new ArrayList<>();
        posts_list_copy = new ArrayList<>();

        NAVIGATION_DRAWER();
        INITVIEW();
        MENU_DISPLAY();
        USER_FIREBASE();
        GET_CLASS_DETAIL();
        FETCH_POST();


        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent start3 = new Intent(getApplicationContext(),Assigments_Upload.class);
                start3.putExtra("key",Key);
                startActivity(start3);
            }
        });

    }

    private void GET_CLASS_DETAIL()
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
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
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

    private void USER_FIREBASE() {
        mauth1 = FirebaseAuth.getInstance();
        USER = mauth1.getCurrentUser();
    }

    private void INITVIEW()
    {
        bmbe = findViewById(R.id.bmb);
        upload = findViewById(R.id.upload_file_2);
        upld_menu = findViewById(R.id.upload_menur);
        delete = findViewById(R.id.delete_2);

        if(!teacher)
        {
            upld_menu.setVisibility(View.INVISIBLE);
        }

        mRecyclerView = findViewById(R.id.file_Show_assigment);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
        Adobter = new Post_Assigment_Adobter(posts_list,Key,teacher,this);
        mRecyclerView.setAdapter(Adobter);
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
                    ope.putExtra("key",Key);
                    startActivity(ope);
                    finish();

                }
                if(index==1)
                {
                }
                if(index==2)
                {
                    Intent start1 = new Intent(getApplicationContext(),Material_Main.class);
                    start1.putExtra("key",Key);
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
                    start3.putExtra("key",Key);
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
            Intent ope ;
            if(teacher)
            {
                ope =  new Intent(getApplicationContext(),Class_Home.class);
            }
            else
            {
                ope =  new Intent(getApplicationContext(),Class_Home_STUDENT.class);
            }
            ope.putExtra("key",Key);
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
            start1.putExtra("key",Key);
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

    private void FETCH_POST() {
        database= FirebaseDatabase.getInstance();
        mDatabaseReffetch = database.getReference().child("CLASSES").child(Key).child("ASSIGMENTS").child("INFO");
        mDatabaseReffetch.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                if (dataSnapshot != null && dataSnapshot.getValue() != null)
                {
                    try
                    {
                        FILE_INFORMATION_ASSIGMENTS model = dataSnapshot.getValue(FILE_INFORMATION_ASSIGMENTS.class);

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
                FILE_INFORMATION_ASSIGMENTS model = dataSnapshot.getValue(FILE_INFORMATION_ASSIGMENTS.class);
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



}
