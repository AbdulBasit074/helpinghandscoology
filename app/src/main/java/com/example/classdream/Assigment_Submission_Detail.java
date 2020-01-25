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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.classdream.R.drawable.txt;

public class Assigment_Submission_Detail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseUser USER;
    FirebaseAuth mauth1;
    DatabaseReference mDatabaseRef2,mDatabaseRef3,mDatabaseRef5,mDatabaseReffetch,mDatabases,mDatabaseRef7,mDatabaseRef9;

    FirebaseDatabase database;
    CREATE_CLASS DATA;
    User you;
    ImageView SET_icon,not_complete;
    Boolean submit = false;
    Request_List_adobter adobter4;
    TextView category;
    Request_List_adobter adobter3;

    Boolean pend = false;

    /////////////////////////////////////////////BASIC SETUP////////////////////////////////////////
    FILE_INFORMATION_ASSIGMENTS file_detail;
    String Key_d;
    List<User> user_list;
    Boolean ifsubmit= false;


    Boolean teacher;
    TextView count_mem;


    NavigationView navigationView;
    DrawerLayout drawer;
    BoomMenuButton bmbe;
    TextView emailbr;
    ImageView cirbar;
    HashMap<String,String> member_list;
    HashMap<String,String> submited_list;



    String[] menu1 = {"Submitted","Member","Material","Chat","Assigment","Class","Profile","Logout","About Class"};
    private static int[] imageResources = new int[]
    {
            R.drawable.submission,
            R.drawable.member,
            R.drawable.material,
            R.drawable.chat,
            R.drawable.assigment,
            R.drawable.classroom,
            R.drawable.profile,
            R.drawable.logout,
            R.drawable.mainicon,
    };
    String Title,Instruction,DAte,TIme,Url,Extension,Name,extension_S,Assigment_Key;

    ListView list_showp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment__submission_detail);
        NAVIGATION_DRAWER();
        submited_list = new HashMap<String, String>();

        member_list = new HashMap<String, String>();

        user_list = new ArrayList<>();
        INITVIEW();
        GET_BUNDLE();
        MENU_DISPLAY();
        USER_FIREBASE();
        GET_CLASS_DETAIL();
        MEMBER_LIST();
        SET_ADOBTER();
        list_showp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(ifsubmit)
                {
                    Intent start3 ;
                    start3  = new Intent(getApplicationContext(),Assigment_Submit_Detail.class);
                    start3.putExtra("key",Key_d);
                    start3.putExtra("assigment_key",Assigment_Key);
                    start3.putExtra("data",user_list.get(position).USER_ID);
                    startActivity(start3);
                }
                else
                {
                    Toast.makeText(getApplicationContext()," SELECTED "+position,Toast.LENGTH_LONG).show();
                    Intent open1 = new Intent(getApplicationContext(),User_Detail.class);
                    open1.putExtra("permission",false);
                    open1.putExtra("data",user_list.get(position).USER_ID);
                    open1.putExtra("key",Key_d);
                    startActivity(open1);
                }
            }
        });

    }

    private void SET_ADOBTER()
    {
        adobter3 = new Request_List_adobter(getApplicationContext(),Key_d,user_list,member_list);
        list_showp.setAdapter(adobter3);
        adobter3.notifyDataSetChanged();
        count_mem.setText(" "+user_list.size());
    }

    private void SUBMITTED()
    {
        member_list = new HashMap<String, String>();
        database = FirebaseDatabase.getInstance();
        mDatabases = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                .child("UPLOADS").child(Assigment_Key).child("SUBMISSION");
        mDatabases.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                member_list = (HashMap<String, String>) dataSnapshot.getValue();
                member_list.remove("EMPTY");
                //count_mem.setText(member_list.size());
                GET_SUBMITTED_USER_DETAIL(member_list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });




    }


    private void GET_BUNDLE()
    {
        Bundle bundle = getIntent().getExtras();
        Key_d  = bundle.getString("key");
        teacher = bundle.getBoolean("teacher");
        Title =  bundle.getString("title");
        Instruction =  bundle.getString("instruction");
        DAte =  bundle.getString("date");
        TIme =  bundle.getString("time");
        Url =  bundle.getString("url");
        Extension =  bundle.getString("extension");
        Name =  bundle.getString("name");
        Assigment_Key =  bundle.getString("assigment_key");

    }

    private void INITVIEW()
    {
        bmbe = findViewById(R.id.bmb);
        count_mem =  findViewById(R.id.count_set);
        SET_icon = findViewById(R.id.icon_set);
        list_showp = findViewById(R.id.list_showw2);
        category  = findViewById(R.id.category);
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
                        user_list.clear();
                        member_list.clear();
                        SUBMITTED();
                        ifsubmit = true;
                        category.setText("SUBMITTED");
                        Glide.with(getApplicationContext()).load(R.drawable.submitnow).into(SET_icon);
                        SET_ADOBTER();

                }
                if(index==1)
                {
                    ifsubmit = false;
                    user_list.clear();
                    member_list.clear();
                    MEMBER_LIST();
                    category.setText("MEMBER");
                    Glide.with(getApplicationContext()).load(R.drawable.member).into(SET_icon);
                    SET_ADOBTER();
                    /*
                    ifsubmit = false ;
                    user_list.clear();
                    member_list.clear();
                    PENDING();
                    category.setText("PENDING");
                    Glide.with(getApplicationContext()).load(R.drawable.pending).into(SET_icon);
                    */
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
                    Intent start3 = new Intent(getApplicationContext(),ChatActivity.class);
                    start3.putExtra("key",Key_d);
                    startActivity(start3);
                }
                if(index==4)
                {
                    Intent start3 = new Intent(getApplicationContext(),Assigment_Main.class);
                    start3.putExtra("key",Key_d);
                    start3.putExtra("teacher",teacher);

                    startActivity(start3);

                }
                if(index==5)
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

    private void PENDING()
    {
        member_list = new HashMap<String, String>();
        database = FirebaseDatabase.getInstance();
        mDatabaseReffetch = database.getReference().child("CLASSES").child(Key_d).child("MEMBER LIST");
        mDatabaseReffetch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                member_list =  (HashMap<String, String>) dataSnapshot.getValue();

                member_list.remove("EMPTY");
                database = FirebaseDatabase.getInstance();
                mDatabases = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                        .child("UPLOADS").child(Assigment_Key).child("SUBMISSION");
                mDatabases.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        submited_list = (HashMap<String, String>) dataSnapshot.getValue();
                        submited_list.remove("EMPTY");
                        for(Map.Entry<String, String> entry: submited_list.entrySet())
                        {
                            member_list.remove(entry.getKey());
                        }

                        //count_mem.setText(member_list.size());
                        GET_SUBMITTED_USER_DETAIL(member_list);
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

        /////////////////////////////////////////////////////////////////////////////

      //  SUBMITTED();

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


    private void MEMBER_LIST()
    {

        member_list = new HashMap<String, String>();
        database = FirebaseDatabase.getInstance();
        mDatabaseReffetch = database.getReference().child("CLASSES").child(Key_d).child("MEMBER LIST");
        mDatabaseReffetch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                member_list = (HashMap<String, String>) dataSnapshot.getValue();
                member_list.remove("EMPTY");
                GET_SUBMITTED_USER_DETAIL(member_list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void GET_SUBMITTED_USER_DETAIL( HashMap<String,String> list_itara)
    {
        user_list = new ArrayList<>();
        list_itara.remove("EMPTY");
//        count_mem.setText(list_itara.size());
        for(Map.Entry<String, String> entry: list_itara.entrySet())
        {
            database = FirebaseDatabase.getInstance();
            mDatabaseRef2 = database.getReference().child("USERS").child(entry.getKey());
            mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    User data = dataSnapshot.getValue(User.class);
                    user_list.add(data);
                         SET_ADOBTER();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
        }

    }






















}
