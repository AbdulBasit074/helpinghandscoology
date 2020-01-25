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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;




public class classroom extends AppCompatActivity implements
        Dialog_Text.Dialog_text_listner,Dialog_Text_2.Dialog_text_listner_2,NavigationView.OnNavigationItemSelectedListener{

    FirebaseAuth mAuth;
    FirebaseUser USER;

    FirebaseDatabase database1;
    DatabaseReference mDatabaseRef1;
    DatabaseReference mDatabaseRef2;
    DatabaseReference mDatabaseRef12;

    ImageView cirbar;
    TextView emailbr;


    Boolean result_check ;
    RecyclerView recyclerView;
    int tog = 0 ;
    DrawerLayout drawer;
    NavigationView navigationView;

    String key_value;
    Adobter_recycler adbter;
    String backGround = "https://firebasestorage.googleapis.com/v0/b/classdream-a85b4.appspot.com/o/ClassFolder%2Fclass.jpg?alt=media&token=4a7d4bb4-ad82-433d-a567-22f49c2a65a9";
    User USER_DETAIL;
    List<Class_Info> list;
    List<String>    Strlist;
    BoomMenuButton bmb3;
    LinearLayout Not_show;
    TextView titl;
    String ID;
    String NAM;
    HashMap<String,String> request_list1;
    Class_Info empty;
    HashMap<String,String> member_list1;
    HashMap<String,String> member_email_list;



    String[] menu1 = {"Join Classes","Join New","Create Classes","Create New","Profile","Home","Log Out","Calculator","aAbout"};
    private static int[] imageResources = new int[]
            {
                R.drawable.join,
                R.drawable.joinnew,
                R.drawable.createclasses,
                R.drawable.createnew,
                R.drawable.profile,
                R.drawable.home,
                R.drawable.logout,
                    R.drawable.calculator,
                    R.drawable.mainicon
            };

    ProgressBar progbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classpage1);

        Not_show = findViewById(R.id.not_show);
        progbar = findViewById(R.id.prog6);
        Not_show.setVisibility(View.INVISIBLE);

        NAVIGATION_DRAWER();
        Strlist  = new ArrayList<>();
        USER();
        USER_DETAIL();
        bmb3 = (BoomMenuButton) findViewById(R.id.bmb3);
        bmb3.setVisibility(View.INVISIBLE);
        bmb3.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb3.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        bmb3.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);

        for (int i = 0; i < bmb3.getPiecePlaceEnum().pieceNumber(); i++)
            bmb3.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(menu1[i],imageResources));
        bmb3.setUse3DTransformAnimation(true);
        bmb3.setDuration(300);



        bmb3.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                if(index==0)
                {
                    tog=0;
                    titl.setText("JOIN CLASSES");
                    empty = null;
                    Join_CLASSES();

                }
                if(index==1)
                {
                    OpenDialog_Join();
                }
                if(index==2)
                {
                    tog=1;
                    titl.setText("CREATE CLASSES");
                    empty = null;
                    CREATE_CLASSES();

                }
                if(index==3)
                {
                    OpenDialog_Create();
                }
                if(index==4)
                {

                    Intent open1 = new Intent(getApplicationContext(),Profile.class);
                    open1.putExtra("user_id",USER_DETAIL.USER_ID);
                    startActivity(open1);
                }
                if(index==5)
                {
                    Intent start3 = new Intent(getApplicationContext(),Main2Activity.class);
                    startActivity(start3);
                    finish();
                }
                if(index==6)
                {
                    FirebaseAuth.getInstance().signOut();
                    Intent start3 = new Intent(getApplicationContext(),LoginActivity_login.class);
                    startActivity(start3);
                    finish();
                }
                if(index==7)
                {
                    Intent start1 = new Intent(getApplicationContext(), Mainact.class);
                    startActivity(start1);
                    finish();
                }
                if(index==8)
                {


                }

            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });


    }
    //////////////////////////////

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

        if (id == R.id.Classroom)
        {
            Toast.makeText(getApplicationContext(),"You are in Class Room",Toast.LENGTH_LONG).show();

        }
        else if (id == R.id.Home)
        {
            Intent start3 = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(start3);
            finish();

        }
        else if (id == R.id.profiled)
        {
            Intent open1 = new Intent(getApplicationContext(),Profile.class);
            open1.putExtra("user_id",USER_DETAIL.USER_ID);
            startActivity(open1);

        }
        else if (id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent start3 = new Intent(getApplicationContext(),LoginActivity_login.class);
            startActivity(start3);
            finish();
        }
        else if (id == R.id.nav_send)
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
    ////////////////////////

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
        navigationView.setCheckedItem(R.id.Classroom);
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

    private void OpenDialog_Join()
    {
        Dialog_Text dial = new Dialog_Text("Class Key","JOIN",1);
        dial.show(getSupportFragmentManager(),"Text Dialog");


    }

    private void OpenDialog_Create()
    {
            Dialog_Text_2 dial2 = new Dialog_Text_2("Class Detail","CREATE",0);
            dial2.show(getSupportFragmentManager(),"Text Dialog");
    }


    private void USER_DETAIL()
    {
         result_check = true;
         /*
         User use = new User();
         use.EMAIL = USER.getEmail();
         use.USER_ID = USER.getUid();
         use.done = false;
         use.NAME = "Abdul Basir";
         use.Z_CREATE_CLASSES.put("SP14","1WE");
         use.Z_CREATE_CLASSES.put("SP14","1WE");
         use.Z_CREATE_CLASSES.put("SP14","1WE");
         use.Z_JOIN_CLASSES.put("spe","dsfds");
         use.Z_JOIN_CLASSES.put("spe","dsfds");
         use.Z_JOIN_CLASSES.put("spe","dsfds");
         database = FirebaseDatabase.getInstance();
         mDatabaseRef = database.getReference().child("USERS").child(USER.getUid());
         mDatabaseRef.setValue(use);

                */
        database1 = FirebaseDatabase.getInstance();
        mDatabaseRef1 = database1.getReference().child("USERS");
        mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(USER.getUid()))
                {
                    Not_show.setVisibility(View.VISIBLE);
                    progbar.setVisibility(View.INVISIBLE);
                    bmb3.setVisibility(View.VISIBLE);
                    INITVIEW();
                    USER_DETAIL_INFO();
                }
                else
                {
                    Intent start3 = new Intent(getApplicationContext(),Update_User_Profile.class);
                    start3.putExtra("key",true);
                    startActivity(start3);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void INITVIEW() {

        recyclerView = findViewById(R.id.recycler_view);
        titl = findViewById(R.id.title);

    }

    private void USER_DETAIL_INFO()
    {
             database1 = FirebaseDatabase.getInstance();
        mDatabaseRef1 = database1.getReference().child("USERS").child(USER.getUid());

        mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 USER_DETAIL = dataSnapshot.getValue(User.class);
                 emailbr.setText(USER_DETAIL.EMAIL);
                 Uri uri = Uri.parse(USER_DETAIL.PROFILE_IMAGE);

                 Glide.with(getApplicationContext()).load(uri).placeholder(R.drawable.triad_ring).into(cirbar);

                  list = new ArrayList<>();
                    if(tog==0) {
                        Join_CLASSES();
                    }
                    if(tog==1)
                    {
                        CREATE_CLASSES();
                    }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });





    }

    private void USER()
    {
        mAuth = FirebaseAuth.getInstance();
        USER = mAuth.getCurrentUser();

    }
    @Override
    public void ApplyText(String name,int i)
    {

        if (name.isEmpty())
        {
                    Toast.makeText(getApplicationContext(), "Must need class key", Toast.LENGTH_LONG).show();
                    OpenDialog_Join();
                }
                else
                {

                    request_list1 = new HashMap<String,String>();

                    ID = USER_DETAIL.USER_ID;
                    NAM = USER_DETAIL.NAME;

                    Toast.makeText(getApplicationContext(), "Searching... " + name + i, Toast.LENGTH_LONG).show();
                    database1 = FirebaseDatabase.getInstance();
                    mDatabaseRef1 = database1.getReference().child("CLASSES");
                    mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.hasChild(name))
                            {

                                    if(CHECK_JOIN_CLASS(name))
                                    {
                                        Dialog dialog = new Dialog(classroom.this,"requestfail","nothing");
                                        dialog.Dialog_Error("Sorry","You Already Join");


                                    }
                                    else if(CHECK_CREATE_CLASS(name))
                                    {
                                        Dialog dialog = new Dialog(classroom.this,"requestfail","nothing");
                                        dialog.Dialog_Error("Sorry","Created by You");
                                    }
                                    else
                                    {



                                                                database1 = FirebaseDatabase.getInstance();
                                                                mDatabaseRef1 = database1.getReference().child("CLASSES").child(name).child("REQUEST LIST");
                                                                mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                                    {
                                                                        request_list1= (HashMap<String, String>) dataSnapshot.getValue();
                                                                        request_list1.put(ID,NAM);
                                                                        database1 = FirebaseDatabase.getInstance();
                                                                        mDatabaseRef1 = database1.getReference().child("CLASSES").child(name).child("REQUEST LIST");
                                                                        mDatabaseRef1.setValue(request_list1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid)
                                                                            {
                                                                                ID  = "";
                                                                                NAM = "";

                                                                            }



                                                                        });
                                                                        Dialog dialog = new Dialog(classroom.this,"requestpass","nothing");
                                                                        dialog.Dialog_some_msg("Loading","Request Send For Join Class");

                                                                        // request_list.add(USER_DETAIL.USER_ID);
                                                                    }
                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                                                    {

                                                                    }
                                                                });

                                    }

                            }
                            else
                            {

                                Dialog dialog = new Dialog(classroom.this,"requestfail","nothing");
                                dialog.Dialog_Error("INVALID","Key Not Found!");

                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });
                }

    }
    public boolean  CHECK_CREATE_CLASS(String match1)
    {

        boolean result1 = false;
        list = new ArrayList<>();
        for (Map.Entry<String, String> entry : USER_DETAIL.Z_CREATE_CLASSES.entrySet())
        {
            if(entry.getKey().equals(match1))
            {
                result1 = true;
            }
        }

        return result1;
    }
    public boolean  CHECK_JOIN_CLASS(String match)
    {

        boolean result = false;
        list = new ArrayList<>();
        for (Map.Entry<String, String> entry : USER_DETAIL.Z_JOIN_CLASSES.entrySet())
        {
            if(entry.getKey().equals(match))
            {
                result = true;
            }
        }

return result;
    }



    public void  Join_CLASSES()
    {

        list = new ArrayList<>();
        for (Map.Entry<String, String> entry : USER_DETAIL.Z_JOIN_CLASSES.entrySet())
        {
                Strlist.add(entry.getValue());
              // Joinclasskey\.add(entry.getKey());

            Class_Info cls = new Class_Info(entry.getValue().toString(),entry.getKey().toString(),backGround);

            if(cls.Class_Name.equals("EMPTY"))
            {
                empty = cls;
            }

            else
            {
                list.add(cls);
            }
            Toast.makeText(getApplicationContext(),"Class :"+entry.getKey(), Toast.LENGTH_LONG).show();

        }
        if(list.isEmpty())
        {

            list.add(empty);
        }

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager1);
//       recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adbter = new Adobter_recycler(list,getApplicationContext(),classroom.this,tog);
        recyclerView.setAdapter(adbter);
        adbter.notifyDataSetChanged();
    }

    private void CREATE_CLASS_FIREBASE(String name,String section,String subject)
    {

        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        String time = timeFormat.format(current).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(current).toString();
        SimpleDateFormat dateFormatkey = new SimpleDateFormat("ddmmyyyy");
        String datekey = dateFormatkey.format(current).toString();
        key_value = GENERATE_KEY();
        key_value = key_value+datekey;
        key_value = key_value+GENERATE_KEY();

        CREATE_CLASS class1 = new CREATE_CLASS();
        class1.CLASS_NAME=name;
        class1.SUBJECT=subject;
        class1.DATE=date;
        class1.TIME=time;
        class1.TOTAL_STUDENTS = 1;
        class1.KEY = key_value;
        class1.CLASS_SECTION  = section;
        class1.TEACHER_EMAIL  = USER_DETAIL.EMAIL;
        class1.TEACHER_NAME   = USER_DETAIL.NAME;
        class1.TEACHER_ID = USER_DETAIL.USER_ID;
        database1 = FirebaseDatabase.getInstance();
        mDatabaseRef1 = database1.getReference().child("CLASSES").child(key_value).child("CLASS INFO");
        mDatabaseRef1.setValue(class1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                request_list1 = new HashMap<String,String>();
                member_list1 = new HashMap<String,String>();
                member_email_list = new HashMap<String, String>();
                member_email_list.put("EMPTY","EMPTY");
                request_list1.put("EMPTY","EMPTY");
                member_list1.put("EMPTY","EMPTY");

                database1 = FirebaseDatabase.getInstance();
                mDatabaseRef1 = database1.getReference().child("CLASSES").child(key_value).child("REQUEST LIST");

                mDatabaseRef1.setValue(request_list1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {

                        USER_DETAIL.Z_CREATE_CLASSES.put(key_value,name);
                        mDatabaseRef1 = database1.getReference().child("USERS").child(USER.getUid());
                        mDatabaseRef1.setValue(USER_DETAIL).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {

                                mDatabaseRef1 = database1.getReference().child("CLASSES").child(key_value).child("CHAT");
                                mDatabaseRef1.push().setValue(new Chat("WellCome!",USER.getUid(),USER_DETAIL.PROFILE_IMAGE,USER_DETAIL.NAME,"dfs"));
                                OneSignal.sendTag("User_ID",USER_DETAIL.EMAIL);
                                SEND_NOTIFICATION notif = new SEND_NOTIFICATION();
                                notif.SendNotification("CREATED NEW CLASS : "+" with Key = "+class1.KEY,
                                        USER_DETAIL.EMAIL,getApplicationContext());
                                Dialog dialog = new Dialog(classroom.this,"createclass","nothing");
                                dialog.Dialog_1("Loading","Create Class Successfully1");

                            }
                        });
                    }
                });
                database1 = FirebaseDatabase.getInstance();
                mDatabaseRef1 = database1.getReference().child("CLASSES").child(key_value).child("MEMBER LIST");
                mDatabaseRef1.setValue(member_list1).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {

                    }
                });

            }
        });



    }

    private String GENERATE_KEY() {
         char[] chars= "A@B#CDEFGHIJ%KLMNOPQR&STUVWXYZ?!abcdefghijkmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder result = new StringBuilder();
        Random random  = new Random();
        for(int i=0; i<3 ;i++)
        {
                char c = chars[random.nextInt(chars.length)];
                result.append(c);
        }
        return result.toString();
    }

    public void CREATE_CLASSES()
    {

        list = new ArrayList<>();

        for (Map.Entry<String, String> entry : USER_DETAIL.Z_CREATE_CLASSES.entrySet())
        {
            Strlist.add(entry.getValue());

            Class_Info cls = new Class_Info(entry.getValue().toString(),entry.getKey().toString(),backGround);

            if(cls.Class_Name.equals("EMPTY"))
            {
                empty = cls;
            }
            else
            {
                list.add(cls);
            }

        }

        if(list.isEmpty())
        {
            list.add(empty);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adbter = new Adobter_recycler(list,getApplicationContext(),classroom.this,tog);
        recyclerView.setAdapter(adbter);
        adbter.notifyDataSetChanged();

    }

    @Override
    public void ApplyText(String name, String section1, String subject, int call_raio)
    {
        if (name.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "No TEXT", Toast.LENGTH_LONG).show();
            OpenDialog_Create();
        }
        else
        {
            if(section1.isEmpty())
            {
                    section1="null";
            }
            if(subject.isEmpty())
            {
                subject = "null";
            }
            Toast.makeText(getApplicationContext(), "CLASS :" + name +section1+subject, Toast.LENGTH_LONG).show();

            CREATE_CLASS_FIREBASE(name,section1,subject);
        }

    }


}
