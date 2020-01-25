package com.example.classdream;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.onesignal.OneSignal;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class CopyLeaksLibrary extends AppCompatActivity
{
    DatabaseReference mDatabaseRef3,mDatabaseReffetch,mDatabases,mDatabaseRef7,mDatabaseRef19,mDatabaseRef17;
    WebView webopen;
    FILE_INFORMATION_ASSIGMENTS file;
    ProgressBar Colorcapsule;
    ResultRecord[] result;
    FILE_INFORMATION_ASSIGMENTS file_detail;
    int currentpercentage=0;

    boolean mark_press=false;
    FirebaseDatabase database;
    ImageView plagrismD;
    TextView resultout,detailcopy;
    EditText resultin;
    Button mark,Start_plagrism;
    int expect=0,percentage=0,copied=0,identical=0,total=0,selecttotal=100;
    Button Marke;
    String Key_d,user_id,Assigment_Key;
    RESULT Result_student;
    CopyleaksProcess copyprocess;
    ComparisonResult comparisionresult;
    ImageView resultshow;
    CircleProgressBar progressBar;
    User submit_studen;
    WebSettings webSettings ;
    CopyleaksProcess createdProcess;
    String uri;
    CircularProgressIndicator circularProgress_percent,circularProgress_expect,circularProgress_copy,circularProgress_identical,circularProgress_total;

    private String url = "http://www.androidstation.info/people.json";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_leaks_library);
        Result_student = new RESULT();
        file = new FILE_INFORMATION_ASSIGMENTS();
        submit_studen = new User();
        circularProgress_percent = findViewById(R.id.circular_progress_percentage);
        circularProgress_expect= findViewById(R.id.circular_progress_expected);
        circularProgress_copy = findViewById(R.id.circular_progress_copied);
        circularProgress_identical = findViewById(R.id.circular_progress_identical);
        circularProgress_total = findViewById(R.id.circular_progress_total);
        Marke = findViewById(R.id.mark1);
        Marke.setVisibility(View.INVISIBLE);


        circularProgress_percent.setMaxProgress(100);


        circularProgress_copy.setMaxProgress(100);


        circularProgress_expect.setMaxProgress(100);


        circularProgress_identical.setMaxProgress(100);


        circularProgress_total.setMaxProgress(100);



        INITVIEW();
        Colorcapsule.setVisibility(View.VISIBLE);

        BUNDLE_DATA();

        Marke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Result_student.COPIED_WORDS = copied ;
                Result_student.EXPECTED = expect;
                Result_student.IDENTICAL_WORDS = identical;
                Result_student.PERCENTAGE  = percentage;
                Result_student.TOTAL_WORDS = total;
                Result_student.URI = uri ;


                database = FirebaseDatabase.getInstance();
                mDatabaseRef19 = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                        .child("UPLOADS").child(Assigment_Key)
                        .child("ASSIGMENT_RESULTS").child(user_id);
                mDatabaseRef19.setValue(Result_student).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        file.RESULT_OUT = true;
                        MARK_SUBMIT();


                    }
                });











            }
        });

        GET_RESULT_DETAIL();
        //execute asynctask object this will resolve NetworkOnMainThreadExcection
    }

    private void MARK_SUBMIT()
    {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef3 = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                .child("UPLOADS").child(Assigment_Key)
                .child("UPLOADS_ASSIGMENTS").child(user_id);
        mDatabaseRef3.setValue(file).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                OneSignal.sendTag("User_ID",submit_studen.EMAIL);
                SEND_NOTIFICATION notif = new SEND_NOTIFICATION();
                notif.SendNotification(submit_studen.NAME+" Your assigment is marked check your report"
                        ,
                        submit_studen.EMAIL,getApplicationContext());
                Dialogmark dialog = new Dialogmark(CopyLeaksLibrary.this,"mark","nothing");
                dialog.Dialog_1("Loading","Assigment Report Update");

            }
        });

    }

    private void GET_RESULT_DETAIL()
    {

        database = FirebaseDatabase.getInstance();
        mDatabaseRef17 = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                .child("UPLOADS").child(Assigment_Key)
                .child("UPLOADS_ASSIGMENTS").child(user_id);
        mDatabaseRef17.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                file = (FILE_INFORMATION_ASSIGMENTS) dataSnapshot.getValue(FILE_INFORMATION_ASSIGMENTS.class);

                if(!file.RESULT_OUT)
                {
                    Marke.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext()," Searching For Result.... ",Toast.LENGTH_LONG).show();
                    GET_USER_ASSIGMENT();
                }
                else
                {

                    database = FirebaseDatabase.getInstance();
                    mDatabaseReffetch = database.getReference().child("CLASSES").child(Key_d).child("ASSIGMENTS")
                            .child("UPLOADS").child(Assigment_Key)
                            .child("ASSIGMENT_RESULTS").child(user_id);
                    mDatabaseReffetch.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            Colorcapsule.setVisibility(View.INVISIBLE);


                            Result_student = dataSnapshot.getValue(RESULT.class);
                            Toast.makeText(getApplicationContext()," OUT "+Result_student.TOTAL_WORDS,Toast.LENGTH_LONG).show();
                            total = Result_student.TOTAL_WORDS;
                            expect = Result_student.EXPECTED;
                            copied = Result_student.COPIED_WORDS;
                            identical = Result_student.IDENTICAL_WORDS;
                            percentage = Result_student.PERCENTAGE;
                            uri = Result_student.URI;
                            CALLMETHOD();
                            SET_PERCENTAGE();

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
                new GetJSONTask().execute(file_detail.FILE_DOWNLOAD_URL);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }











    private void BUNDLE_DATA()
    {
        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getString("data");
        Key_d  = bundle.getString("key");
        Assigment_Key =  bundle.getString("assigment_key");
        GET_USER();



    }

    private void GET_USER()
    {
        database = FirebaseDatabase.getInstance();
        mDatabaseRef3 = database.getReference().child("USERS").child(user_id);
        mDatabaseRef3.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                submit_studen = dataSnapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    private void INITVIEW()
    {

        resultshow = findViewById(R.id.copy_word_image);

        plagrismD = findViewById(R.id.percentage_image);
        Colorcapsule = findViewById(R.id.loading23);
        Glide.with(getApplicationContext()).load(R.drawable.checked_percentage).into(plagrismD);
        Glide.with(getApplicationContext()).load(R.drawable.checked_words).into(resultshow);
        webopen= (WebView) findViewById(R.id.webview);

    }

    public void getDataFromUrl() {
        try {
            Utility.downloadDataFromUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread(For Avoid
    // NetworkOnMainThreadException). This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the
    // connection
    // has been established, the AsyncTask downloads the contents of the data as
    // an InputStream. Than, the InputStream is converted into a string, which
    // is
    // displayed in the TextView by the AsyncTask's onPostExecute method. Which
    // called after doInBackgroud Complete
    public class GetJSONTask extends AsyncTask<String, Void, String> {

        // onPreExecute called before the doInBackgroud start for display
        // progress dialog.
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls)
        {

            try
            {
                CopyleaksCloud copyleaks = new CopyleaksCloud(eProduct.Businesses,getApplication());
                System.out.print("Login to Copyleaks cloud...");
                copyleaks.Login("Sibteali4@gmail.com","6336D9BF-EDC8-4713-8191-01C839FEB5B2");
                System.out.println("Done!");
                int creditsBalance = copyleaks.getCredits();
                 result = PROCESS(copyleaks);

                if(result==null)
                    return "DONE"+creditsBalance+"NULL";


              //  Toast.makeText(getApplicationContext()," RESULT "+result[0].getPercents(),Toast.LENGTH_LONG).show();
                return "DONE"+creditsBalance+result;

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext()," Please Check Your API ",Toast.LENGTH_LONG).show();
            }
            return "FAIL";
        }

        // onPostExecute displays the results of the doInBackgroud and also we
        // can hide progress dialog.
        @Override
        protected void onPostExecute(String resultd) {

            comparisionresult = new ComparisonResult();
            CALLMETHOD();

            SET_PERCENTAGE();

            Marke.setVisibility(View.VISIBLE);

        }
    }

    private void CALLMETHOD()
    {

        webopen.setWebViewClient(new WebViewClient());

        webSettings= webopen.getSettings();

        webopen.getSettings().setAllowContentAccess(true);
        webopen.getSettings().setAllowFileAccess(true);
        webopen.getSettings().setDatabaseEnabled(true);
        webopen.getSettings().setDomStorageEnabled(true);
        webopen.getSettings().setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webopen.getSettings().setAppCacheEnabled(true);
        webopen.getSettings().setDisplayZoomControls(true);


        webopen.loadUrl(uri);
    }

    public  ResultRecord[] PROCESS(CopyleaksCloud copyleaks) throws URISyntaxException, InterruptedException {
        ResultRecord[] results;

        ProcessOptions scanOptions = new ProcessOptions();
        scanOptions.setSandboxMode(false);
        createdProcess = copyleaks.CreateByUrl(new URI(file_detail.FILE_DOWNLOAD_URL), scanOptions);
        System.out.println("Scanning...");
        int percents1 = 0;
        percents1 = createdProcess.getCurrentProgress();
        while (percents1 != 100 && percents1 <= 100)
        {
            if (percents1 != 100)
                Thread.sleep(9000);

            percents1 = createdProcess.getCurrentProgress();
        }

        percents1 = createdProcess.getCurrentProgress();

        results = createdProcess.GetResults(getApplicationContext());


        for (int i = 0; i < results.length; ++i)
        {
            System.out.println();
            System.out.println("------------------------------------------------");
            System.out.println(String.format("Url: %1$s", results[i].getURL()));
            System.out.println(String.format("Information: %1$s copied words (%2$s%%)",
                    results[i].getNumberOfCopiedWords(), results[i].getPercents()));
            percentage = results[i].getPercents();
            copied = results[i].getNumberOfCopiedWords();

            System.out.println(String.format("Comparison Report: %1$s", results[i].getComparisonReport()));
            System.out.println(String.format("Title: %1$s", results[i].getTitle()));
            System.out.println(String.format("Introduction: %1$s", results[i].getIntroduction()));
            System.out.println(String.format("Embeded Comparison: %1$s", results[i].getEmbededComparison()));

            uri = results[i].getEmbededComparison();

            // Optional: Download result full text. Uncomment to
            // activate

                System.out.println("Result full-text:");
                System.out.println("*****************");
                System.out.println(createdProcess.DownloadResultText(results[i]));
                System.out.println("2");
                System.out.println("*****************");
                comparisionresult = createdProcess.DownloadResultComparison(results[i]);
                total = comparisionresult.getTotalWords();
                identical = comparisionresult.getIdenticalCopiedWords();
                System.out.println(comparisionresult.getTotalWords());


        }

// Optional: Download source full text. Uncomment to activate.
         System.out.println("Source full-text:");
        System.out.println("*****************");
         System.out.println(createdProcess.DownloadSourceText());



       return results;
    }

    private void SET_PERCENTAGE()
    {
        Colorcapsule.setVisibility(View.INVISIBLE);
        circularProgress_percent.setCurrentProgress(percentage);
        circularProgress_copy.setCurrentProgress(copied);
        expect = selecttotal - percentage;
        circularProgress_expect.setCurrentProgress(expect);
        circularProgress_total.setCurrentProgress(total);
        circularProgress_identical.setCurrentProgress(identical);
        Colorcapsule.setVisibility(View.INVISIBLE);
    }


    public class Dialogmark {

        int i = -1;
        Context contex;
        String data;
        String detail;

        public Dialogmark(Context contex, String dao, String detail) {
            this.contex = contex;
            this.data = dao;
            this.detail = detail;

        }

        public void Dialog_1(String msg1, String msg2) {

            final SweetAlertDialog pDialog = new SweetAlertDialog(contex, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg1);
            pDialog.show();
            pDialog.setCancelable(false);
            new CountDownTimer(800 * 1, 100) {
                public void onTick(long millisUntilFinished) {
                    // you can change the progress bar color by ProgressHelper every 800 millis
                    i++;
                    switch (i) {
                        case 0:
                            pDialog.getProgressHelper().setBarColor(R.color.blue_btn_bg_color);
                            break;
                        case 1:
                            pDialog.getProgressHelper().setBarColor(R.color.material_deep_teal_50);
                            break;
                        case 2:
                            pDialog.getProgressHelper().setBarColor(R.color.success_stroke_color);
                            break;
                        case 3:
                            pDialog.getProgressHelper().setBarColor(R.color.material_deep_teal_20);
                            break;
                        case 4:
                            pDialog.getProgressHelper().setBarColor(R.color.material_blue_grey_80);
                            break;
                        case 5:
                            pDialog.getProgressHelper().setBarColor(R.color.warning_stroke_color);
                            break;
                        case 6:
                            pDialog.getProgressHelper().setBarColor(R.color.success_stroke_color);
                            break;
                    }
                }

                public void onFinish() {
                    i = -1;
                    pDialog.setTitleText(msg2)
                            .setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                                 Intent start3;
                                 start3 = new Intent(contex, CopyLeaksLibrary.class);
                                 start3.putExtra("key", Key_d);
                                 start3.putExtra("assigment_key", Assigment_Key);
                                 start3.putExtra("data", user_id);
                                 startActivity(start3);
                                 ((CopyLeaksLibrary) contex).finish();


                        }
                    })
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                }
            }.start();

        }
    }




}
