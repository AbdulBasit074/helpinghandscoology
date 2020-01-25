package com.example.classdream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends Activity {
    private static final String TAG = ChatActivity.class.getName();

    private EditText metText;
    private Button mbtSent;
    FirebaseAuth mAuth;
    FirebaseUser USER;
    private DatabaseReference mFirebaseRef;
    private DatabaseReference mFirebaseRef1;
    private DatabaseReference mFirebaseRef2;


    FirebaseDatabase database1;
    User you;


    private List<Chat> mChats;

    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private String uId;
    private String uname;
    private String uprofile;
    private String utime;
    private String Key;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Bundle bundle = getIntent().getExtras();
        Key = bundle.getString("key");


        GET_USER();
        GET_USER_DETAIL();

        metText = (EditText) findViewById(R.id.etText);
        mbtSent = (Button) findViewById(R.id.btSent);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvChat);


        mChats = new ArrayList<>();

/////////////////////////////////////////////////////////////

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mRecyclerView.setItemAnimator(new SlideInOutLeftItemAnimator(mRecyclerView));
        mAdapter = new ChatAdapter(mChats, uId,uprofile,uname,utime,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        mFirebaseRef1 = database1.getReference().child("CLASSES").child(Key).child("CHAT");


        ///////////////////////////////////SENT MESSAGE/////////////////////////////////    1
        mbtSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messages = metText.getText().toString();

                if (!messages.isEmpty())
                {
                    /**
                     * Firebase - Send message
                     */
                    mFirebaseRef1.push().setValue(new Chat(messages,USER.getUid(),you.PROFILE_IMAGE,you.NAME,"dfs"));
                }
                metText.setText("");
            }
        });
        ///////////////////////////////////READ MESSAGES/////////////////////////////////
        mFirebaseRef1.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                if (dataSnapshot != null && dataSnapshot.getValue() != null)
                {
                    try
                    {
                        Chat model = dataSnapshot.getValue(Chat.class);
                        mChats.add(model);

                        mRecyclerView.scrollToPosition(mChats.size() - 1);
                        mAdapter.notifyItemInserted(mChats.size() - 1);
                    }
                    catch (Exception ex)
                    {
                        Log.e(TAG, ex.getMessage());
                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

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
                Log.d(TAG, databaseError.getMessage());
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////

    }

    private void SET_DATA()
    {
        uId = USER.getUid();
        uprofile = you.PROFILE_IMAGE;
        uname = you.NAME;
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss a");
        String dateString1 = sdf1.format(date);
        utime = dateString1;
    }

    private void GET_USER_DETAIL()
    {
        database1 = FirebaseDatabase.getInstance();
        mFirebaseRef = database1.getReference().child("USERS").child(USER.getUid());
        mFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                you = dataSnapshot.getValue(User.class);
                SET_DATA();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void GET_USER()
    {
        mAuth = FirebaseAuth.getInstance();
        USER = mAuth.getCurrentUser();
    }
}
