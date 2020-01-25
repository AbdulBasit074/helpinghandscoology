package com.example.classdream;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.classdream.R.drawable.assigment_check;
import static com.example.classdream.R.drawable.done;
import static com.example.classdream.R.drawable.pdf;
import static com.example.classdream.R.drawable.powerpoint;
import static com.example.classdream.R.drawable.profile;
import static com.example.classdream.R.drawable.txt;
import static com.example.classdream.R.drawable.word;

public class Assigment_Submit_Detail extends AppCompatActivity
{

    FirebaseUser USER;
    FirebaseAuth mauth1;
    DatabaseReference mDatabaseRef3,mDatabaseReffetch,mDatabases,mDatabaseRef7,mDatabaseRef9;
    FILE_INFORMATION_ASSIGMENTS file_detail;

    FirebaseDatabase database;

    User assigment_user;
    boolean file_load = false ;
    boolean user_detail_load = false ;



    TextView date_submit,time_submit,user_name;
    String Title,Instruction,DAte,TIme,Url,Extension,Name,extension_S,Assigment_Key,Key_d,user_id;
    boolean teacher;

    ImageView file_icon,user_profile,user_detail;
    Button view_assigment;
    ImageView mark_assigment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment__submit__detail);
        file_detail = new FILE_INFORMATION_ASSIGMENTS();
        GET_BUNDLE();
        USER_FIREBASE();
        INITVIEW();
        GET_ASSIGMENT_USER_DETAIL();
        GET_USER_ASSIGMENT();
        user_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(user_detail_load)
                {
                    Intent open1 = new Intent(getApplicationContext(),User_Detail.class);
                    open1.putExtra("permission",false);
                    open1.putExtra("data",user_id);
                    open1.putExtra("key",Key_d);
                    startActivity(open1);
                }

            }
        });
        view_assigment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(file_load) {
                    Intent start3 = new Intent(getApplicationContext(), Material_Document_View.class);
                    start3.putExtra("url", file_detail.FILE_DOWNLOAD_URL);
                    start3.putExtra("extension", file_detail.FILE_EXTENSION);
                    start3.putExtra("name", file_detail.FILE_NAME);
                    startActivity(start3);
                }
            }
        });
        mark_assigment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(file_load) {

                    Intent start3;
                    start3 = new Intent(getApplicationContext(),CopyLeaksLibrary.class);
                    start3.putExtra("key", Key_d);
                    start3.putExtra("assigment_key", Assigment_Key);
                    start3.putExtra("data", user_id);
                    startActivity(start3);
                }

            }
        });





    }

    private void GET_USER_ASSIGMENT()
    {

        database = FirebaseDatabase.getInstance();
        mDatabaseRef7 = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                .child("UPLOADS").child(Assigment_Key)
                .child("UPLOADS_ASSIGMENTS").child(user_id);
        mDatabaseRef7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                file_detail = (FILE_INFORMATION_ASSIGMENTS) dataSnapshot.getValue(FILE_INFORMATION_ASSIGMENTS.class);
                date_submit.setText(file_detail.FILE_UPLOAD_DATE);
                time_submit.setText(file_detail.FILE_UPLOAD_TIME);
                file_load = true ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void SET_FILE_EXTENSTION()
    {
            Glide.with(getApplicationContext()).load(assigment_check).into(file_icon);
    }

    private void GET_ASSIGMENT_USER_DETAIL()
    {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef3 = database.getReference().child("USERS").child(user_id);
        mDatabaseRef3.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                assigment_user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(assigment_user.PROFILE_IMAGE).into(user_profile);
                user_name.setText(assigment_user.NAME);
                user_detail_load = true ;
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
        user_id = bundle.getString("data");
        Key_d  = bundle.getString("key");
        Assigment_Key =  bundle.getString("assigment_key");
    }
    private void INITVIEW()
    {
        date_submit = findViewById(R.id.date_submit);
        time_submit= findViewById(R.id.time_submit);
        user_name= findViewById(R.id.name_submit);
        file_icon= findViewById(R.id.icon_u_submit);
        user_profile = findViewById(R.id.profile_submit);
        user_detail= findViewById(R.id.detail_assigment);
        view_assigment= findViewById(R.id.view_submit);
        mark_assigment= findViewById(R.id.mark_submit);
        Glide.with(getApplicationContext()).load(done).into(mark_assigment);
        SET_FILE_EXTENSTION();


    }

    private void USER_FIREBASE() {
        mauth1 = FirebaseAuth.getInstance();
        USER = mauth1.getCurrentUser();
    }



}
