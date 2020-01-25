package com.example.classdream;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.ParsedUrl;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.onesignal.OneSignal;

import java.net.URI;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public class User_Detail extends AppCompatActivity {

    TextView name,email,create,join;
    Button permissoin,denied;
    ImageView profile;
    String CLASS_KEY;
    String name_class;
    User user_data;
    boolean per=false;
    String user_id;
    LinearLayout layout,back;
    ValueEventListener listner;
    DatabaseReference mDatabaseRefref;
    DatabaseReference mDatabaseRefref2;
    DatabaseReference mDatabaseRefref12;


    FirebaseDatabase databaseref;
    HashMap<String,String> request_list1;
    HashMap<String,String> member_list;
    HashMap<String,String> member_email_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__detail);
        member_email_list = new HashMap<String, String>();
        GET_BUNDLE();
        INITVIEW();
        USER_DETAIL();
        if(per) {
            permissoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ADD_MEMBER();
                }
            });
            denied.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DENIED();

                }
            });
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_data!=null) {
                    Intent op = new Intent(getApplicationContext(), FULL_VIEW.class);
                    op.putExtra("uri", user_data.PROFILE_IMAGE);
                    startActivity(op);
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void DENIED() {
        request_list1.put("EMPTY","EMPTY");
        request_list1.remove(user_data.USER_ID);
        mDatabaseRefref = databaseref.getReference().child("CLASSES").child(CLASS_KEY).child("REQUEST LIST");
        mDatabaseRefref.setValue(request_list1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                user_data.Z_NOTIFICATION.put("DECISION"+CLASS_KEY,"DENIED : Your Request denied for Join Group"+name_class);
                databaseref = FirebaseDatabase.getInstance();
                mDatabaseRefref = databaseref.getReference().child("USERS").child(user_data.USER_ID);
                mDatabaseRefref.setValue(user_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {


                        permissoin.setVisibility(View.GONE);
                        denied.setBackgroundColor(getResources().getColor(R.color.ACP_CAN));
                        denied.setText("Permission Denied");


                        OneSignal.sendTag("User_ID",user_data.EMAIL);
                        SEND_NOTIFICATION notif = new SEND_NOTIFICATION();
                        notif.SendNotification("DENIED: Your Request For Joinning "+name_class,
                                user_data.EMAIL,getApplicationContext());

                        Intent start = new Intent(getApplicationContext(),Member.class);
                        start.putExtra("key",CLASS_KEY);
                        start.putExtra("class name",name_class);
                        startActivity(start);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        finish();




                    }
                });

            }
        });


    }

    private void ADD_MEMBER() {

         member_list.put(user_data.USER_ID,user_data.NAME);
        request_list1.put("EMPTY","EMPTY");
        request_list1.remove(user_data.USER_ID);
        databaseref = FirebaseDatabase.getInstance();
        mDatabaseRefref = databaseref.getReference().child("CLASSES").child(CLASS_KEY).child("MEMBER LIST");
        mDatabaseRefref.setValue(member_list).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                databaseref = FirebaseDatabase.getInstance();
                mDatabaseRefref = databaseref.getReference().child("CLASSES").child(CLASS_KEY).child("REQUEST LIST");
                mDatabaseRefref.setValue(request_list1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        user_data.Z_JOIN_CLASSES.put(CLASS_KEY,name_class);
                        user_data.Z_NOTIFICATION.put("DECISION"+CLASS_KEY," ASSEPTED: Your Request Assepted for Join Group"+name_class);
                        databaseref = FirebaseDatabase.getInstance();
                        mDatabaseRefref = databaseref.getReference().child("USERS").child(user_data.USER_ID);
                        mDatabaseRefref.setValue(user_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {

                                OneSignal.sendTag("User_ID",user_data.EMAIL);
                                SEND_NOTIFICATION notif = new SEND_NOTIFICATION();
                                notif.SendNotification("ASSEPTED : Your Request For Joinning "+name_class,
                                        user_data.EMAIL,getApplicationContext());
                                Intent start = new Intent(getApplicationContext(),Member.class);
                                start.putExtra("key",CLASS_KEY);
                                start.putExtra("class name",name_class);
                                startActivity(start);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                finish();

                                /// denied.setVisibility(View.GONE);
                                ///permissoin.setBackgroundColor(getResources().getColor(R.color.ACP_CAN));
                                ///permissoin.setText("Member Now");
                            }
                        });


                    }
                });



            }
        });


    }


    private void SET_DETAIL() {
        Glide.with(getApplicationContext()).load(user_data.PROFILE_IMAGE).into(profile);
    name.setText(user_data.NAME);
    email.setText(user_data.EMAIL);

    int a = user_data.Z_CREATE_CLASSES.size()-1;
    int b = user_data.Z_JOIN_CLASSES.size()-1;
    create.setText("Create Groups \n "+a);
    join.setText("Join Groups \n "+b);
    }

    private void USER_DETAIL() {
        databaseref = FirebaseDatabase.getInstance();
        mDatabaseRefref = databaseref.getReference().child("USERS").child(user_id);
         mDatabaseRefref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                    user_data = dataSnapshot.getValue(User.class);
                    SET_DETAIL();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void INITVIEW()
    {
        layout = findViewById(R.id.menu);
        layout.setVisibility(View.INVISIBLE);
        if(per)
        {
            layout.setVisibility(View.VISIBLE);
            permissoin = findViewById(R.id.permission);
            denied = findViewById(R.id.denied);
        }
        profile = findViewById(R.id.profile);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        create =findViewById(R.id.create);
        join = findViewById(R.id.join);
    }

    private void GET_BUNDLE() {

        Bundle bundle  = getIntent().getExtras();
        user_id = bundle.getString("data");
        per = bundle.getBoolean("permission");
        CLASS_KEY = bundle.getString("key");
        if(per)
        {
            member_list = new HashMap<String, String>();
            member_list = (HashMap<String, String>) bundle.get("member_list");
            name_class = bundle.getString("name_class");
            request_list1 = new HashMap<String, String>();
            request_list1 = (HashMap<String, String>) bundle.get("request_list");
        }
        if(per!=true)
        {
        }

    }





}
