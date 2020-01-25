package com.example.classdream;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.classdream.R.drawable.pdf;
import static com.example.classdream.R.drawable.powerpoint;
import static com.example.classdream.R.drawable.profile;
import static com.example.classdream.R.drawable.txt;
import static com.example.classdream.R.drawable.word;

public class Assigments_Upload extends AppCompatActivity {


    Button enddate,endtime,upload_file,final_upload;
  public int mYear, mMonth, mDay, mHour, mMinute;
  ImageView uplod_file_detail;
    List<User> user_list23;
    Uri upload_path_assign = null;
    String file_name_assign;
    FirebaseStorage mstorage;

    DatabaseReference mDatabaseRef2,mDatabaseRef3,mDatabaseRef5,mDatabases,mDatabaseRef122;
    FirebaseDatabase database,database2;
    CREATE_CLASS DATA;
    HashMap<String,String> member_list;

    HashMap<String,String> member_submission;



    StorageReference mstorageref;

    String file_size_assign;
    User teacher_detail;
    String extension_assign;

    FILE_INFORMATION_ASSIGMENTS file;
    TextView nam,siz;
    ImageView icn;
    String Key;
    LinearLayout date_detail;
    String  DownloadURL;
    EditText tit,ins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigments__upload);
        INITVIEW();
        user_list23 = new ArrayList<>();
        member_submission= new HashMap<String, String>();
        member_submission.put("EMPTY","EMPTY");

        Bundle bundle = getIntent().getExtras();
        Key = bundle.getString("key");
        MEMBER_LIST();
        GET_CLASS_DATA();

        enddate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                endtime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        uplod_file_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                UPLOAD_FILE();
            }
        });
        final_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tit.getText().toString().isEmpty())
                {
                    tit.setError("Must Need");
                }
                else if (ins.getText().toString().isEmpty())
                {
                    ins.setError("Must Need");
                }
                else if (upload_path_assign == null)
                {
                    Toast.makeText(getApplicationContext(), "Attach File", Toast.LENGTH_LONG).show();
                }
                else
                {
                    final_upload.setVisibility(View.INVISIBLE);
                    Glide.with(getApplicationContext()).load(R.drawable.fileloading).into(uplod_file_detail);
                    file = new FILE_INFORMATION_ASSIGMENTS();
                    mstorage = FirebaseStorage.getInstance();
                    SET_FILE_INFORMATION();
                    mstorageref = mstorage.getReference().child("MATERIAL")
                            .child("ASSIGMENTS").child("CLASSES").child(Key).child(file.ASSIGMENTS_KEY).child(file_name_assign);
                    mstorageref.putFile(upload_path_assign).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mstorageref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    DownloadURL = task.getResult().toString();
                                    Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_LONG).show();
                                    file.FILE_DOWNLOAD_URL = DownloadURL;
                                    UPLOAD_POST_DATABASE();
                                    SAVE_IN_CLASS();
                                }
                            });
                        }
                      });


                }
            }
        });
    }

    private void SAVE_IN_CLASS()
    {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef3 = database.getReference().child("CLASSES").child(Key).child("ASSIGMENTS").child("INFO");
        mDatabaseRef3.push().setValue(file).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(getApplicationContext(), "Complete DONE", Toast.LENGTH_LONG).show();
                mDatabases = database.getReference().child("CLASSES").child(Key).child("ASSIGMENTS")
                        .child("UPLOADS").child(file.ASSIGMENTS_KEY).child("SUBMISSION");
                mDatabases.setValue(member_submission).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {

                        GET__USER_DETAIL();
                        Dialog dialog = new Dialog(Assigments_Upload.this,"assigmentupload","nothing");
                        dialog.Dialog_1("Loading","Assigment Upload Successfully");

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
            file.FILE_UPLOAD_CLASS_KEY = Key;
            file.ASSIGMENTS_KEY = GENERATE_KEY();
            file.ASSIGMENTS_KEY  = file.ASSIGMENTS_KEY + date;
            file.ASSIGMENTS_KEY = file.ASSIGMENTS_KEY + GENERATE_KEY();
            int mem = member_list.size()-1;
            file.TOTAL_STUDENTS = mem;
            file.TOTAL_STUDENTS_UPLOADS = 0 ;
            file.FILE_TITLE = tit.getText().toString();
            file.FILE_INSTRUCTION = ins.getText().toString();
            ///////////////////////////////////////////////////////////////////////////
    }
    private String GENERATE_KEY() {
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

    private void INITVIEW()
    {
        enddate = findViewById(R.id.end_date);
        endtime = findViewById(R.id.end_time);
        uplod_file_detail = findViewById(R.id.final_u_assign);
        final_upload = findViewById(R.id.final_upload);
        nam = findViewById(R.id.name_u_assign);
        tit = findViewById(R.id.title_assign);
        ins = findViewById(R.id.instruction_assign);
        siz = findViewById(R.id.size_u_assign);
        icn = findViewById(R.id.icon_u_assign);
        date_detail = findViewById(R.id.date_menu);
        date_detail.setVisibility(View.INVISIBLE);
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
        try{

        file_size_assign =  Long.toString(returnCursor.getLong(sizeIndex));
        file_name_assign = returnCursor.getString(nameIndex);
            Toast.makeText(getApplicationContext(), ""+file_name_assign+file_size_assign, Toast.LENGTH_LONG).show();
            extension_assign = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));

            nam.setText(file_name_assign);
            siz.setText(file_size_assign+"bytes");

            if(extension_assign.equals(".pdf"))
            {
                Glide.with(getApplicationContext()).load(pdf).into(icn);
                //  icn.setImageResource(pdf);
            }
            else if(extension_assign.equals(".doc") || extension_assign.equals(".docx"))
            {

                Glide.with(getApplicationContext()).load(word).into(icn);
            }
            else if(extension_assign.equals(".ppt") || extension_assign.equals(".pptx"))
            {
                Glide.with(getApplicationContext()).load(powerpoint).into(icn);
            }
            else if(extension_assign.equals(".txt"))
            {
                Glide.with(getApplicationContext()).load(txt).into(icn);

            }
            else if(extension_assign.equals(".xlsx") || extension_assign.equals(".xlsm") )
            {
                Glide.with(getApplicationContext()).load(R.drawable.excel).into(icn);

            }
            else if(extension_assign.equals(".jpg") ||extension_assign.equals(".jpeg")
                    || extension_assign.equals(".png") || extension_assign.equals(".gif"))
            {
                Glide.with(getApplicationContext()).load(profile).into(icn);

            }


        }
        catch (Exception e)
        {
            upload_path_assign = null;
            Dialog dialog = new Dialog(Assigments_Upload.this,"materialfile1","nothing");
            dialog.Dialog_Error("File Path Error","Read about File path!");
        }
////////////////////////////////////////////////////////////////


    }
    private void MEMBER_LIST() {

        member_list = new HashMap<String, String>();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef2 = database.getReference().child("CLASSES").child(Key).child("MEMBER LIST");
        mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                member_list = (HashMap<String, String>) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
    private void GET__USER_DETAIL()
    {

        member_list.remove("EMPTY");

        for(Map.Entry<String, String> entry: member_list.entrySet())
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
                    notif.SendNotification( DATA.CLASS_NAME+ " New ASSIGMENT Is Upload Please Check",
                            data.EMAIL,getApplicationContext());

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                }
            });
        }
        member_list.put("EMPTY","EMPTY");
    }
    private void UPLOAD_POST_DATABASE()
    {
        Simple_Post post = new Simple_Post();
        Date current = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");
        String time = timeFormat.format(current).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(current).toString();

        post.title = "ASSIGMENT"+file.FILE_TITLE;
        post.instruction=file.FILE_INSTRUCTION;
        post.date = date;
        post.time = time;
        post.teacher_name = teacher_detail.NAME;
        post.teacher_profile = teacher_detail.PROFILE_IMAGE;
        database = FirebaseDatabase.getInstance();
        mDatabaseRef5 = database.getReference().child("CLASSES").child(Key).child("SIMPLEPOST");
        mDatabaseRef5.push().setValue(post);
    }
    private void GET_CLASS_DATA()
    {

        database = FirebaseDatabase.getInstance();
        mDatabaseRef2 = database.getReference().child("CLASSES").child(Key).child("CLASS INFO");

        mDatabaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                DATA = dataSnapshot.getValue(CREATE_CLASS.class);
                database = FirebaseDatabase.getInstance();
                mDatabaseRef3 = database.getReference().child("USERS").child(DATA.TEACHER_ID);
                mDatabaseRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        teacher_detail = dataSnapshot.getValue(User.class);
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
}
