package com.example.classdream;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    String id;
    User USER_DETAIL;

    FirebaseDatabase database1;
    DatabaseReference mDatabaseRef1;
    DatabaseReference mDatabaseRef2;
    DatabaseReference mDatabaseRef12;
    ImageView proe;
    TextView nam,emal;
    boolean find =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        proe = findViewById(R.id.edit_pro);
        nam = findViewById(R.id.edit_na);
        emal = findViewById(R.id.edit_emai);

        Bundle data = getIntent().getExtras();
       id = data.getString("user_id");
        database1 = FirebaseDatabase.getInstance();
        mDatabaseRef1 = database1.getReference().child("USERS").child(id);
        mDatabaseRef1.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                find = true;

                USER_DETAIL =(User) dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(USER_DETAIL.PROFILE_IMAGE).into(proe);
                nam.setText(USER_DETAIL.NAME);
                emal.setText(USER_DETAIL.EMAIL);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        proe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(find)
                {
                    Intent op = new Intent(getApplicationContext(), FULL_VIEW.class);
                    op.putExtra("uri", USER_DETAIL.PROFILE_IMAGE);
                    startActivity(op);
                }


                }
        });






    }











    }

