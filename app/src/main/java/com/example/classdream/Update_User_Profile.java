package com.example.classdream;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;

import static com.example.classdream.UserAuthentication.c;

public class  Update_User_Profile extends AppCompatActivity {


    FirebaseAuth mAuth;
    FirebaseUser USER1;
    FirebaseDatabase mdatabase;
    DatabaseReference mreference;
    ProgressBar progress;
    FirebaseStorage mstorage;
    StorageReference mstorageref;
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    String DownloadURL;
    private static final int PICK_IMAGE_REQUEST = 100;
    EditText name1;
    TextView email1;
    Button edit,upload;
    ImageView profile;
    String save_image;
    String save_name;
    Uri selectedImage=null;
    boolean RES;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__user__profile);

        Bundle data = getIntent().getExtras();
        RES = data.getBoolean("key");
        INITVIEW();
        USER_INFO();

        if(RES)
        {
            email1.setText(USER1.getEmail());
        }
        /////////////////////////////////UPDATE PROFILE/////////////////////////////////////////////
        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });
        ////////////////////////////////////EDIT BUTTON/////////////////////////////////////////////
        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                    if(name1.getText().toString().isEmpty())
                    {
                        name1.setError("Must need");
                    }
                    else if(name1.getText().toString().length()<3)
                    {
                        name1.setError("Must Your Real Name");
                    }
                    else
                    {
                            STORE_IMAGE_PROFILE();
                           progress.setVisibility(View.VISIBLE);

                    }


            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void STORE_IMAGE_PROFILE()
    {


        if(selectedImage==null)
            {
                DownloadURL =
                        "https://firebasestorage.googleapis.com/v0/b/classdream-a85b4.appspot.com/o/USER_PROFILE%2Fprofile_pic.png?alt=media&token=bd9053b7-b8a6-467d-9e80-dfad3b51b122";
                UPLOAD_DATA();
            }
            else
            {
                mstorage = FirebaseStorage.getInstance();
                mstorageref = mstorage.getReference().child("USER_PROFILE").child(USER1.getUid());
                mstorageref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        mstorageref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Toast.makeText(getApplicationContext(), "Link" + task.getResult().toString(), Toast.LENGTH_LONG).show();
                                DownloadURL = task.getResult().toString();
                                UPLOAD_DATA();
                            }
                        });

                    }
                });
            }

    }

    private void UPLOAD_DATA()
    {
        if(selectedImage!=null)
        {
            Glide.with(this)
                    .load(selectedImage)
                    .into(profile);
        }
            SET_USER();

    }
    private void GET_USER()
    {

    }

    private void SET_USER()
    {
        User user = new User();
        user.NAME = name1.getText().toString().trim();
        user.USER_ID = USER1.getUid();
        user.EMAIL=USER1.getEmail();
        user.DONE = true;
        user.PROFILE_IMAGE = DownloadURL;
        user.Z_NOTIFICATION.put("EMPTY","EMPTY");
        user.Z_CREATE_CLASSES.put("EMPTY","EMPTY");
        user.Z_JOIN_CLASSES.put("EMPTY","EMPTY");
       // mdatabase = FirebaseDatabase.getInstance();
     ///   mreference = mdatabase.getReference().child("USERS").child(USER1.getUid());

        FirebaseDatabase.getInstance().getReference().child("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).

        setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                try
                {
                    OneSignal.sendTag("User_ID",USER1.getEmail());
                    SEND_NOTIFICATION notif = new SEND_NOTIFICATION();
                    notif.SendNotification(" WELLCOME TO CLASS DREAM best for "+
                                    "all class room activities and make work easy for teachers and students",
                            USER1.getEmail(),getApplicationContext());
                    Intent start3 = new Intent(getApplicationContext(),classroom.class);
                    startActivity(start3);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                }
                catch(Exception e)
                {

                }
            }
        });
        if(RES)
        {
        }

    }

    private void USER_INFO() {
        mAuth = FirebaseAuth.getInstance();
        USER1 = mAuth.getCurrentUser();
    }

    private void INITVIEW()
    {
        name1 = findViewById(R.id.edit_name);
        email1 = findViewById(R.id.edit_email);
        edit = findViewById(R.id.edit);
        profile = findViewById(R.id.edit_profile12);
        upload = findViewById(R.id.upload_profile);
        progress = findViewById(R.id.prog);
        progress.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICK_IMAGE_REQUEST:
                if(resultCode == RESULT_OK){

                   selectedImage = data.getData();
                   profile.setImageURI(selectedImage);
                   // profile.setImageURI(selectedImage);
                }
                break;
        }
    }


}
