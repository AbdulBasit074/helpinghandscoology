package com.example.classdream;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.classdream.R.drawable.pdf;
import static com.example.classdream.R.drawable.powerpoint;
import static com.example.classdream.R.drawable.profile;
import static com.example.classdream.R.drawable.txt;
import static com.example.classdream.R.drawable.word;


public class Material_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    FirebaseAuth mauth1;
    FirebaseUser USER;
    ImageView upload;
    Uri upload_path = null;
    RecyclerView mRecyclerView;
    LinearLayout upload_mnu;
    MaterialAdobter adobter;
    List<FILE_INFORMATION> files;
    DatabaseReference mDatabaseRef, mDatabaseRef2,mDatabaseRef122 ;
    CREATE_CLASS DATA ;
    FirebaseDatabase mdatabase,database;
    DrawerLayout drawer;
    BoomMenuButton bmbe;
    TextView nam;
    TextView siz;
    ImageView icn;
    ImageView final_load;
    FirebaseStorage mstorage;
    StorageReference mstorageref;
    ImageView cross;

    TextView emailbr;
    ImageView cirbar;
    ImageView delete;



/////////////////////////////////////////////////////////
    String extension;
    FILE_INFORMATION file;
    String file_name;
    String file_size;
    String  DownloadURL;




    /////////////////////////////////////////////////////


    String[] menu1 = {"Classes","Assigment","Material","Member","Chat","Profile","Logout","class","About"};
    private static int[] imageResources = new int[]
    {
            R.drawable.blackboard,
            R.drawable.assigment,
            R.drawable.material,
            R.drawable.member,
            R.drawable.chat,
            profile,
            R.drawable.logout,
            R.drawable.classroom,
            R.drawable.mainicon,
    };
    CardView cardview;
    HashMap<String,String> member_list23;

    String file_extension;
    Boolean teacher;
    String Key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material__main);
        member_list23 = new HashMap<>();
        DATA = new CREATE_CLASS();
        files = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        Key = bundle.getString("key");
        teacher = bundle.getBoolean("teacher");
        USER_FIREBASE();
        GET_CLASS_DETAIL();
        NAVIGATION_DRAWER();
        INITVIEW();
        MENU_DISPLAY();
        MEMBER_LIST();
        RECYCLER_SETTING();
        FIREBASE_MATERIAL_READ();

    delete.setOnClickListener(new View.OnClickListener() {
    @Override
     public void onClick(View v)
      {


      }
      });



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"File Upload...",Toast.LENGTH_LONG).show();
                UPLOAD_FILE();

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                cardview.setVisibility(View.INVISIBLE);
            }
        });

        final_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                    Glide.with(getApplicationContext()).load(R.drawable.fileloading).into(final_load);


                     file = new FILE_INFORMATION();
                    mstorage = FirebaseStorage.getInstance();

                    mstorageref = mstorage.getReference().child("MATERIAL")
                            .child("FILES").child("CLASSES").child(Key).child(file_name);
                    mstorageref.putFile(upload_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                            mstorageref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task)
                                {
                                    DownloadURL = task.getResult().toString();
                                    Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
                                    SET_FILE_INFORMATION();
                                    SAVE_IN_CLASS();


                                }
                            });
                        }
                    });
            }

            private void SET_FILE_INFORMATION()
            {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat1 = new SimpleDateFormat("dd-MMM-yyyy");
                String date = dateformat1.format(c.getTime());
                SimpleDateFormat dateformat2 = new SimpleDateFormat("hh:mm:ss aa");
                String time = dateformat2.format(c.getTime());
                //////////////////////////////////////////////////////////////////////////
                file.FILE_UPLOAD_DATE = date;
                file.FILE_UPLOAD_TIME = time;
                file.FILE_NAME =file_name;
                file.FILE_EXTENSION = extension ;
                file.FILE_SIZE = file_size;
                file.FILE_DOWNLOAD_URL = DownloadURL;
                file.FILE_UPLOAD_CLASS_KEY = Key;
                file.FILE_UPLOAD_USER_ID = USER.getUid();
                ///////////////////////////////////////////////////////////////////////////
            }

            private void SAVE_IN_CLASS()
            {
                mdatabase = FirebaseDatabase.getInstance();
                mDatabaseRef = mdatabase.getReference().child("CLASSES").child(Key).child("FILES");
                mDatabaseRef.push().setValue(file).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Glide.with(getApplicationContext()).load(R.drawable.uploadfile).into(final_load);
                        GET__USER_DETAIL();
                        Dialog dialog = new Dialog(Material_Main.this,"materialfile","nothing");
                        dialog.Dialog_some_msg("Loading","File Upload Successfully");
                        cardview.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

    }

    private void FIREBASE_MATERIAL_READ()
    {

        mdatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mdatabase.getReference().child("CLASSES").child(Key).child("FILES");
        mDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

                if (dataSnapshot != null && dataSnapshot.getValue() != null)
                {
                    try
                    {
                        FILE_INFORMATION model = dataSnapshot.getValue(FILE_INFORMATION.class);
                        files.add(model);
                        Toast.makeText(getApplicationContext(), "ADD", Toast.LENGTH_LONG).show();
                        mRecyclerView.scrollToPosition(files.size() - 1);
                        adobter.notifyItemInserted(files.size() - 1);
                    }
                    catch (Exception ex)
                    {
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }

    private void USER_FIREBASE() {
        mauth1 = FirebaseAuth.getInstance();
        USER = mauth1.getCurrentUser();
    }

    private void RECYCLER_SETTING() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adobter = new MaterialAdobter(files,this,delete);
        mRecyclerView.setAdapter(adobter);
    }

    private void UPLOAD_FILE()
    {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==86 && resultCode==RESULT_OK && data!=null)
        {
            upload_path = data.getData();

            if(upload_path!=null)
            {
                PATH_DETAIL();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void PATH_DETAIL()
    {


        File file = new File(upload_path.getPath());
////////////////////////////////////////////////////////////////
        Cursor returnCursor =
                getContentResolver().query(upload_path, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();

            file_size =  Long.toString(returnCursor.getLong(sizeIndex));
            file_name = returnCursor.getString(nameIndex);
        try {
////////////////////////////////////////////////////////////////
            Toast.makeText(getApplicationContext(), "" + file_name + file_size, Toast.LENGTH_LONG).show();
            extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));

            nam.setText(file_name);
            siz.setText(file_size + "bytes");

            cardview.setVisibility(View.VISIBLE);
            if (extension.equals(".pdf")) {
                Glide.with(getApplicationContext()).load(pdf).into(icn);
                //  icn.setImageResource(pdf);
            } else if (extension.equals(".doc") || extension.equals(".docx")) {

                Glide.with(getApplicationContext()).load(word).into(icn);
            } else if (extension.equals(".ppt") || extension.equals(".pptx")) {
                Glide.with(getApplicationContext()).load(powerpoint).into(icn);
            } else if (extension.equals(".txt")) {
                Glide.with(getApplicationContext()).load(txt).into(icn);
            } else if (extension.equals(".xlsx") || extension.equals(".xlsm")) {
                Glide.with(getApplicationContext()).load(R.drawable.excel).into(icn);

            } else if (extension.equals(".jpg") || extension.equals(".jpeg")
                    || extension.equals(".png") || extension.equals(".gif")) {
                Glide.with(getApplicationContext()).load(profile).into(icn);
            }
        }
        catch (Exception e)
        {

            upload_path = null ;
            Dialog dialog = new Dialog(Material_Main.this,"materialfile1","nothing");
            dialog.Dialog_Error("File Path Error","Read about File path!");

        }
    }
    private String displayName(Uri uri)
    {

        Cursor mCursor =
                getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        int indexedname = mCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        mCursor.moveToFirst();
        String filename = mCursor.getString(indexedname);
        mCursor.close();
        return filename;
    }

    private void INITVIEW()
    {
        bmbe = findViewById(R.id.bmb);
        upload = findViewById(R.id.upload_file);
        mRecyclerView = (RecyclerView) findViewById(R.id.file);
        upload_mnu = findViewById(R.id.upload_menud);
        cardview = findViewById(R.id.card_view_up);
        delete = findViewById(R.id.delete);
        UPLOAD_INIT();
        delete.setVisibility(View.INVISIBLE);
        cardview.setVisibility(View.INVISIBLE);
        if(!teacher)
        {
            upload_mnu.setVisibility(View.INVISIBLE);

        }



    }

    private void UPLOAD_INIT()
    {
        nam = findViewById(R.id.name_u);
        siz = findViewById(R.id.size_u);
        icn = findViewById(R.id.icon_u);
        final_load = findViewById(R.id.final_u);
        cross = findViewById(R.id.cross);
    }

    private void NAVIGATION_DRAWER() {

        View header  =((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);

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
                    Intent start3 = new Intent(getApplicationContext(),classroom.class);
                    startActivity(start3);
                    finish();
                }
                if(index==1)
                {

                    Intent start3 = new Intent(getApplicationContext(),Assigment_Main.class);
                    start3.putExtra("key",Key);
                    start3.putExtra("teacher",teacher);
                    startActivity(start3);
                }
                if(index==2)
                {

                }
                if(index==3)
                {
                    Intent star = new Intent(getApplicationContext(),Member_CLASS.class);
                    star.putExtra("key",Key);
                    star.putExtra("class name",DATA.CLASS_NAME);
                    star.putExtra("teacher",teacher);
                    startActivity(star);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                }
                if(index==4)
                {
                    Intent start3 = new Intent(getApplicationContext(),ChatActivity.class);
                    start3.putExtra("key",Key);
                    startActivity(start3);

                }
                if(index==5)
                {
                    Intent open1 = new Intent(getApplicationContext(),Profile.class);
                    open1.putExtra("user_id", USER.getUid());
                    startActivity(open1);

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

        int id = item.getItemId();


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
            open1.putExtra("user_id", USER.getUid());
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
    private void GET_CLASS_DETAIL()
    {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef2 = database.getReference().child("CLASSES").child(Key).child("CLASS INFO");
        mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                DATA = dataSnapshot.getValue(CREATE_CLASS.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void MEMBER_LIST() {

        member_list23 = new HashMap<String, String>();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef122 = database.getReference().child("CLASSES").child(Key).child("MEMBER LIST");
        mDatabaseRef122.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                member_list23 = (HashMap<String, String>) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
    private void GET__USER_DETAIL()
    {

        member_list23.remove("EMPTY");

        for(Map.Entry<String, String> entry: member_list23.entrySet())
        {
            database = FirebaseDatabase.getInstance();
            mDatabaseRef122 = database.getReference().child("USERS").child(entry.getKey());
            mDatabaseRef122.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {

                    User data = dataSnapshot.getValue(User.class);
                    SEND_NOTIFICATION notif = new SEND_NOTIFICATION();
                    notif.SendNotification( DATA.CLASS_NAME + " New Document Is Upload in Material",
                            data.EMAIL,getApplicationContext());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
        }
        member_list23.put("EMPTY","EMPTY");
    }
}
