package com.example.classdream;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.mikhaellopez.circularimageview.CircularImageView;
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

public class Member extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    boolean request;

    String name_class;
    Request_List_adobter adobter;
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView cirbar;
    TextView emailbr;

    int i=0;

    DatabaseReference mDatabaseRef1;
    DatabaseReference mDatabaseRef2;
    DatabaseReference mDatabaseRef3;
    DatabaseReference mDatabaseRef5;

    User you;

    FirebaseDatabase database;

    FirebaseAuth mAuth;
    FirebaseUser USER;

    String Class_Key;
    BoomMenuButton bmbr;
    ListView list_show;
    String[] menu2 = {"Classes","Assigment","Material","Requests","Member","Chat","Profile","Logout","About Class"};
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
    List<String> request_user;
    HashMap<String,String> request_list1;
    HashMap<String,String> member_list;



    List<User> user_list;

    TextView page_title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_page3);
        NAVIGATION_DRAWER();
        GET_BUNDLE();
        INITVIEW();
        USER_FIREBASE();
        MEMBER_LIST();
        mDatabaseRef5 = database.getReference().child("USERS").child(USER.getUid());
        mDatabaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                you  = dataSnapshot.getValue(User.class);
                emailbr.setText(you.EMAIL);
                Uri uri = Uri.parse(you.PROFILE_IMAGE);
                Glide.with(getApplicationContext()).load(uri).placeholder(R.drawable.triad_ring).into(cirbar);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        list_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getApplicationContext()," SELECTED "+position,Toast.LENGTH_LONG).show();
                Intent open = new Intent(getApplicationContext(),User_Detail.class);
                open.putExtra("permission",true);
                open.putExtra("data",user_list.get(position).USER_ID);
                open.putExtra("request_list",request_list1);
                open.putExtra("member_list",member_list);
                open.putExtra("key",Class_Key);
                open.putExtra("name_class",name_class);
                startActivity(open);
                finish();
            }
        });

            GET_REQUEST_LIST();

    }

    private void NAVIGATION_DRAWER()
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

        navigationView.setItemIconTintList(null);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

    private void MEMBER_LIST() {

        member_list = new HashMap<>();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef1 = database.getReference().child("CLASSES").child(Class_Key).child("MEMBER LIST");
        mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                member_list = (HashMap<String, String>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void GET_REQUEST_USER_DETAIL()
    {
        i=0;
        user_list = new ArrayList<>();
        request_list1.remove("EMPTY");
        page_title.setText("Request ("+request_list1.size()+")");

        for(Map.Entry<String, String> entry: request_list1.entrySet())
        {
            database = FirebaseDatabase.getInstance();
            mDatabaseRef2 = database.getReference().child("USERS").child(entry.getKey());
            mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    i++;
                    User data = dataSnapshot.getValue(User.class);
                    user_list.add(data);
                    SHOW_LIST();

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
        }
    }

    private void SHOW_LIST()
    {

        adobter = new Request_List_adobter(getApplicationContext(),Class_Key,user_list,request_list1);
        list_show.setAdapter(adobter);

    }

    private void GET_REQUEST_LIST()
    {

        database = FirebaseDatabase.getInstance();
        mDatabaseRef3 = database.getReference().child("CLASSES").child(Class_Key).child("REQUEST LIST");

        mDatabaseRef3.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                request_list1 = new HashMap<String, String>();
                Toast.makeText(getApplicationContext()," MEMBER VALUE CHANGE ",Toast.LENGTH_LONG).show();

                request_list1 = (HashMap<String, String>) dataSnapshot.getValue();
                Toast.makeText(getApplicationContext(),"Get Data", Toast.LENGTH_LONG).show();
                GET_REQUEST_USER_DETAIL();
               // SHOW_LIST();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });







    }

    private void INITVIEW()
    {
        bmbr = findViewById(R.id.bmbr);
        getSupportActionBar().setTitle(name_class);
        page_title = findViewById(R.id.page_title);
        list_show =findViewById(R.id.list_show);
        MENU_DISPLAY();
    }

    private void GET_BUNDLE()
    {
        request_list1 = new HashMap<String,String>();
        Bundle b = getIntent().getExtras();
        request = b.getBoolean("request");
        name_class = b.getString("class name");
        Class_Key = b.getString("key");


    }
    private void USER_FIREBASE()
    {
        mAuth = FirebaseAuth.getInstance();
        USER = mAuth.getCurrentUser();
    }
    private void MENU_DISPLAY()
    {

        bmbr.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmbr.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        bmbr.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);

        for (int i = 0; i < bmbr.getPiecePlaceEnum().pieceNumber(); i++)
            bmbr.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(menu2[i],imageResources));
        bmbr.setUse3DTransformAnimation(true);
        bmbr.setDuration(300);

        bmbr.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                Toast.makeText(getApplicationContext()," SELECTED "+index,Toast.LENGTH_LONG).show();

                if(index==0)
                {
                    Intent start3 = new Intent(getApplicationContext(),classroom.class);
                    startActivity(start3);
                    finish();
                }
                if(index==1)
                {
                    Intent start3 = new Intent(getApplicationContext(),Assigment_Main.class);
                    start3.putExtra("key",Class_Key);
                    start3.putExtra("teacher",true);
                    startActivity(start3);
                }
                if(index==2)
                {

                    Intent start1 = new Intent(getApplicationContext(),Material_Main.class);
                    start1.putExtra("key",Class_Key);
                    start1.putExtra("teacher",true);
                    startActivity(start1);

                }
                if(index==3)
                {
                    Toast.makeText(getApplicationContext()," Already On Request Page",Toast.LENGTH_LONG).show();
                }
                if(index==4)
                {

                    Intent startc = new Intent(getApplicationContext(),Member_CLASS.class);
                    startc.putExtra("key",Class_Key);
                    startc.putExtra("class name",name_class);
                    startc.putExtra("teacher",true);

                    startActivity(startc);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
                if(index==5)
                {
                    Intent start3 = new Intent(getApplicationContext(),ChatActivity.class);
                    start3.putExtra("key",Class_Key);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {

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

            Intent ope = new Intent(getApplicationContext(),Class_Home.class);
            ope.putExtra("key",Class_Key);
            startActivity(ope);
            finish();

        }
        else if (id == R.id.assign)
        {
            Intent start3 = new Intent(getApplicationContext(),Assigment_Main.class);
            start3.putExtra("key",Class_Key);
            start3.putExtra("teacher",true);
            startActivity(start3);


        }
        else if (id == R.id.mater)
        {
            Intent start1 = new Intent(getApplicationContext(),Material_Main.class);
            start1.putExtra("key",Class_Key);
            start1.putExtra("teacher",true);
            startActivity(start1);
        }
        else if (id == R.id.mem)
        {
            Intent start = new Intent(getApplicationContext(),Member_CLASS.class);
            start.putExtra("key",Class_Key);
            start.putExtra("class name",name_class);
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
