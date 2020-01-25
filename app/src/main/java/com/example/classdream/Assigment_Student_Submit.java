package com.example.classdream;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

import static com.example.classdream.R.drawable.pdf;
import static com.example.classdream.R.drawable.powerpoint;
import static com.example.classdream.R.drawable.profile;
import static com.example.classdream.R.drawable.txt;
import static com.example.classdream.R.drawable.word;

public class Assigment_Student_Submit extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FirebaseUser USER;
    FirebaseAuth mauth1;
    DatabaseReference mDatabaseRef2,mDatabaseRef3,mDatabaseRef5,mDatabaseReffetch,mDatabaseRef7,mDatabaseRef17,mdatabaseuserref,submitref,mDatabaseRef19;
    StorageReference mstorageref;
    String Title,Instruction,DAte,TIme,Url,Extension,Name,extension_S,Assigment_Key;
    CardView card_file;
    User you;

    String DownloadURL;

    FirebaseDatabase database;
    Uri upload_path_assign = null;
    String file_name_assign;
    FirebaseStorage mstorage;

    CREATE_CLASS DATA;
    User current_user;

    /////////////////////////////////////////////BASIC SETUP////////////////////////////////////////
    FILE_INFORMATION_ASSIGMENTS file_detail;
    String Key_d;
    Boolean teacher;
    String file_size_assign;
    FILE_INFORMATION_ASSIGMENTS file;

    ImageView icon,attach;
    ImageView upload;
    TextView plagrismresul,file_name,file_size;
    String extension_assign;

    LinearLayout result_show;
    Boolean uploaded = false;

    NavigationView navigationView;
    HashMap<String,String> submited_list;
    CardView card1,card2;
    DrawerLayout drawer;
    int percentage,expected,copied,identical,total;
    BoomMenuButton bmbe;
    TextView emailbr;
    RESULT Result;
    ImageView cirbar;
    double val = 100;
    TextView a_s,b_s,c_s;
    CircularProgressIndicator circularProgress_percent_S,circularProgress_expect_S,circularProgress_copy_S,circularProgress_identical_S,circularProgress_total_S;

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


    CircularProgressIndicator circularProgress_percent_student,circularProgress_expect_student,
            circularProgress_copy_student,circularProgress_identical_student,circularProgress_total_student;

    ImageView percentage_image,words_imge;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment__student_submit);
        submited_list = new HashMap<String, String>();
        file = new FILE_INFORMATION_ASSIGMENTS();
        card1 = findViewById(R.id.percentage1);
        card2 = findViewById(R.id.plagrismsd);
        card1.setVisibility(View.INVISIBLE);
        card2.setVisibility(View.INVISIBLE);






        //a_s = findViewById(R.id.a);
       // b_s = findViewById(R.id.b);
      //  c_s = findViewById(R.id.c);

        circularProgress_percent_student = findViewById(R.id.circular_progress_percentage_student);
        circularProgress_expect_student= findViewById(R.id.circular_progress_expected_student);
        circularProgress_copy_student = findViewById(R.id.circular_progress_copied_student);
        circularProgress_identical_student = findViewById(R.id.circular_progress_identical_student);
        circularProgress_total_student = findViewById(R.id.circular_progress_total_student);


        circularProgress_percent_student.setMaxProgress(100);


        circularProgress_copy_student.setMaxProgress(100);


        circularProgress_expect_student.setMaxProgress(100);


        circularProgress_identical_student.setMaxProgress(100);


        circularProgress_total_student.setMaxProgress(100);



















        current_user = new User();
        Result = new RESULT();
        NAVIGATION_DRAWER();
        INITVIEW();
        GET_BUNDLE();
        MENU_DISPLAY();
        USER_FIREBASE();
        GET_USER_DETIAL();
        GET_CLASS_DETAIL();
        CHECK_ASSIGMENT_SUBMITED();


        card_file.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view)
            {

                if(uploaded)
                {
                    Intent start3 = new Intent(getApplicationContext(),Material_Document_View.class);
                    start3.putExtra("url",file.FILE_DOWNLOAD_URL);
                    start3.putExtra("extension",file.FILE_EXTENSION);
                    start3.putExtra("name",file.FILE_NAME);
                    startActivity(start3);
                }
            }

            });










        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                UPLOAD_FILE();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                    UPLOAD_ASSIGMENT();
            }
        });

    }

    private void CHECK_ASSIGMENT_SUBMITED()
    {
        submited_list = new HashMap<>();
        database = FirebaseDatabase.getInstance();
        submitref = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                .child("UPLOADS").child(Assigment_Key).child("SUBMISSION");
        submitref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                submited_list = (HashMap<String, String>) dataSnapshot.getValue();
                if(submited_list.containsKey(USER.getUid()))
                {

                    database = FirebaseDatabase.getInstance();
                    mDatabaseRef17 = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                            .child("UPLOADS").child(Assigment_Key)
                            .child("UPLOADS_ASSIGMENTS").child(USER.getUid());
                    mDatabaseRef17.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {

                                file = (FILE_INFORMATION_ASSIGMENTS) dataSnapshot.getValue(FILE_INFORMATION_ASSIGMENTS.class);
                                attach.setVisibility(View.INVISIBLE);
                                upload.setVisibility(View.INVISIBLE);
                                file_name.setText(file.FILE_NAME);
                                file_size.setText(file.FILE_SIZE);
                                extension_assign = file.FILE_EXTENSION;
                                uploaded = true ;
                                SET_FILE_EXTENSION();
                                if(!file.RESULT_OUT)
                                {
                                    result_show.setVisibility(View.VISIBLE);
                                    plagrismresul.setText("RESULT : NOT AVAILABLE");


                                }
                                else
                                {

                                             database = FirebaseDatabase.getInstance();
                                             mDatabaseRef19 = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                                            .child("UPLOADS").child(Assigment_Key)
                                            .child("ASSIGMENT_RESULTS").child(USER.getUid());
                                            mDatabaseRef19.addListenerForSingleValueEvent(new ValueEventListener()
                                            {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {

                                                    card1.setVisibility(View.VISIBLE);
                                                    card2.setVisibility(View.VISIBLE);
                                                    Result = dataSnapshot.getValue(RESULT.class);
                                                    result_show.setVisibility(View.VISIBLE);
                                                    plagrismresul.setText("RESULT : AVAILABLE");
                                                    circularProgress_copy_student.setCurrentProgress(Result.COPIED_WORDS);
                                                    circularProgress_percent_student.setCurrentProgress(Result.PERCENTAGE);
                                                    circularProgress_total_student.setCurrentProgress(Result.TOTAL_WORDS);
                                                    circularProgress_identical_student.setCurrentProgress(Result.IDENTICAL_WORDS);
                                                    circularProgress_expect_student.setCurrentProgress(Result.EXPECTED);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });





                                }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else
                {

                    result_show.setVisibility(View.INVISIBLE);
                    upload.setVisibility(View.VISIBLE);
                    attach.setVisibility(View.VISIBLE);
                    uploaded = false;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });












    }

    private void SET_FILE_EXTENSION()
    {
        if(extension_assign.equals(".pdf"))
        {
            Glide.with(getApplicationContext()).load(pdf).into(icon);
            //  icn.setImageResource(pdf);
        }
        else if(extension_assign.equals(".doc") || extension_assign.equals(".docx"))
        {

            Glide.with(getApplicationContext()).load(word).into(icon);
        }
        else if(extension_assign.equals(".ppt") || extension_assign.equals(".pptx"))
        {
            Glide.with(getApplicationContext()).load(powerpoint).into(icon);
        }
        else if(extension_assign.equals(".txt"))
        {
            Glide.with(getApplicationContext()).load(txt).into(icon);

        }
        else if(extension_assign.equals(".xlsx") || extension_assign.equals(".xlsm") )
        {
            Glide.with(getApplicationContext()).load(R.drawable.excel).into(icon);

        }
        else if(extension_assign.equals(".jpg") ||extension_assign.equals(".jpeg")
                || extension_assign.equals(".png") || extension_assign.equals(".gif"))
        {
            Glide.with(getApplicationContext()).load(profile).into(icon);

        }


    }

    private void GET_USER_DETIAL()
    {

        database = FirebaseDatabase.getInstance();
        mdatabaseuserref= database.getReference().child("USERS").child(USER.getUid());
        mdatabaseuserref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                current_user = dataSnapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });



    }

    private void UPLOAD_ASSIGMENT()
    {
        if (upload_path_assign == null)
        {
           Toast.makeText(getApplicationContext(), "Attach File", Toast.LENGTH_LONG).show();
        }
        else
        {
            Glide.with(getApplicationContext()).load(R.drawable.fileloading).into(attach);

            upload.setVisibility(View.INVISIBLE);
            Glide.with(getApplicationContext()).load(R.drawable.fileloading).into(upload);
            file = new FILE_INFORMATION_ASSIGMENTS();
            mstorage = FirebaseStorage.getInstance();
            SET_FILE_INFORMATION();
            mstorageref = mstorage.getReference().child("MATERIAL")
                    .child("ASSIGMENTS").child("CLASSES").child(Key_d).child(file.ASSIGMENTS_KEY).child(file_name_assign);
            mstorageref.putFile(upload_path_assign).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mstorageref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            DownloadURL = task.getResult().toString();
                            Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
                            file.FILE_DOWNLOAD_URL = DownloadURL;
                            SAVE_IN_CLASS();
                        }
                    });
                }
            });
        }
    }
    private void SAVE_IN_CLASS()
    {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef3 = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                .child("UPLOADS").child(Assigment_Key)
                .child("UPLOADS_ASSIGMENTS").child(USER.getUid());
        mDatabaseRef3.setValue(file).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(getApplicationContext(), "Complete DONE", Toast.LENGTH_LONG).show();
                mDatabaseReffetch = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                        .child("UPLOADS").child(Assigment_Key).child("SUBMISSION");


                mDatabaseReffetch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        submited_list = (HashMap<String, String>) dataSnapshot.getValue();
                        submited_list.put(USER.getUid(),current_user.NAME);
                        mDatabaseReffetch.setValue(submited_list).addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(getApplicationContext(), "UPLOAD SUCCESSFULLY", Toast.LENGTH_LONG).show();
                                Glide.with(getApplicationContext()).load(R.drawable.uploadfile).into(attach);

                                Dialog dialog = new Dialog(Assigment_Student_Submit.this, "materialfile1", "nothing");
                                dialog.Dialog_some_msg("Loading", "Assigment Submit Successfully.");
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });

            }
        });


    }
    public void SET_FILE_INFORMATION()
    {


        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat1 = new SimpleDateFormat("dd-MMM-yyyy");
        String date = dateformat1.format(c.getTime());
        SimpleDateFormat dateformat2 = new SimpleDateFormat("hh:mm:ss aa");
        String time = dateformat2.format(c.getTime());
        //////////////////////////////////////////////////////////////////////////
        file.FILE_UPLOAD_DATE = date;
        file.FILE_UPLOAD_TIME = time;
        file.FILE_NAME = file_name_assign ;
        file.FILE_EXTENSION = extension_assign ;
        file.FILE_SIZE = file_size_assign ;
        // file.FILE_DOWNLOAD_URL = DownloadURL;
        file.FILE_UPLOAD_CLASS_KEY = Key_d;
        file.ASSIGMENTS_KEY = Assigment_Key;
        ///////////////////////////////////////////////////////////////////////////
    }
    private String GENERATE_KEY()
    {
        char[] chars= "A@B#CDEFGHIJ%KLMNOPQR&STUVWXYZ?!abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder result = new StringBuilder();
        Random random  = new Random();
        for(int i=0; i<3 ;i++)
        {
            char c = chars[random.nextInt(chars.length)];
            result.append(c);
        }
        return result.toString();
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
            upload_path_assign = data.getData();

            if(upload_path_assign!=null)
            {
                PATH_DETAIL();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void PATH_DETAIL()
    {





        try {

            File file = new File(upload_path_assign.getPath());
////////////////////////////////////////////////////////////////
            Cursor returnCursor =
                    getContentResolver().query(upload_path_assign, null, null, null, null);
            /*
             * Get the column indexes of the data in the Cursor,
             * move to the first row in the Cursor, get the data,
             * and display it.
             */
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            file_size_assign = Long.toString(returnCursor.getLong(sizeIndex));
            file_name_assign = returnCursor.getString(nameIndex);

////////////////////////////////////////////////////////////////
            Toast.makeText(getApplicationContext(), "" + file_name_assign + file_size_assign, Toast.LENGTH_LONG).show();
            extension_assign = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));

            file_name.setText(file_name_assign);
            file_size.setText(file_size_assign + "bytes");

        }
        catch (Exception e)
        {
            upload_path_assign = null;
            Dialog dialog = new Dialog(Assigment_Student_Submit.this, "materialfile1", "nothing");
            dialog.Dialog_Error("File Path Error", "Read about File path!");
        }



    }




























    private void INITVIEW()
    {
        bmbe = findViewById(R.id.bmb);
         icon =  findViewById(R.id.icon_u_assign1);
         attach=  findViewById(R.id.upload_assign1);
         upload=  findViewById(R.id.file_upload_assigment1);

         percentage_image = findViewById(R.id.percentage_image_student);
         words_imge = findViewById(R.id.copy_word_image_student);



        Glide.with(getApplicationContext()).load(R.drawable.checked_percentage).into(percentage_image);
        Glide.with(getApplicationContext()).load(R.drawable.checked_words).into(words_imge);






         plagrismresul = findViewById(R.id.plagrismresult);
        result_show = findViewById(R.id.result_detail);
        attach.setVisibility(View.INVISIBLE);
        upload.setVisibility(View.INVISIBLE);
        result_show.setVisibility(View.INVISIBLE);

         file_name = findViewById(R.id.name_u_assign1);
         file_size = findViewById(R.id.size_u_assign1);
         result_show = findViewById(R.id.result_detail);
         result_show.setVisibility(View.INVISIBLE);
         card_file = findViewById(R.id.card_view_up);

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
                mDatabaseRef7 = database.getReference().child("USERS").child(DATA.TEACHER_ID);
                mDatabaseRef7.addListenerForSingleValueEvent(new ValueEventListener() {
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
