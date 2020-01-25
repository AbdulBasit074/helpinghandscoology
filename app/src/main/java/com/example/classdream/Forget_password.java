package com.example.classdream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;

public class Forget_password extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseUser USER1;
    FirebaseDatabase mdatabase;
    DatabaseReference mreference;
    ProgressBar progress;
    FirebaseStorage mstorage;
    StorageReference mstorageref;

    EditText get_email;
    Button recover;
    ProgressBar prg;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.forget_pass);
            get_email = findViewById(R.id.edit_email_forget);
            recover = findViewById(R.id.recover);
            prg = findViewById(R.id.progyt);
            prg.setVisibility(View.INVISIBLE);


            recover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(!get_email.getText().toString().contains("@"))
                    {
                        get_email.setError("Must b correct Email");
                    }
                    else if(get_email.getText().toString().equals(""))
                    {
                        get_email.setError("Must need Email");
                    }
                    else
                    {
                        prg.setVisibility(View.VISIBLE);
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.sendPasswordResetEmail(get_email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    prg.setVisibility(View.INVISIBLE);
                                    Dialog dialog = new Dialog(Forget_password.this,"forget","nothing");
                                    dialog.Dialog_1("Loading","Check Your Email for Reset");

                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                prg.setVisibility(View.INVISIBLE);
                                Dialog dialog = new Dialog(Forget_password.this,"forget","nothing");
                                dialog.Dialog_Error("Loading","Email Not Register");

                            }
                        });


                    }



                }
            });











        }
}