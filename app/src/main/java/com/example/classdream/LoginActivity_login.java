package com.example.classdream;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;

import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import java.util.List;
import java.util.regex.Pattern;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ir.smrahmadi.easydialog.EasyDialog;







public class LoginActivity_login extends AppCompatActivity{

      TextInputEditText email;
      TextInputEditText password;

      Button login;
      private static final Pattern PASSWORD_PATTERN = Pattern.compile
            ("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    public int i = -1;


    TextView progressWithoutBg;
    //////////////////////////////////////////////////////////
  public static FirebaseAuth mAuth;


  @BindViews(value = {R.id.logo})
  protected List<ImageView> sharedElements;
  EasyDialog easyDialog = new EasyDialog();

    FirebaseUser USER2;
    VerticalTextView_login lay;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if(CHECKLOGINACTIVITY())
      {
          Intent start3 = new Intent(getApplicationContext(),classroom.class);
          startActivity(start3);
          finish();
      }
      setContentView(R.layout.activity_login);
      //////////////////////////////////////////////////////////////
          progressWithoutBg = findViewById(R.id.progressWithoutBg);
          progressWithoutBg.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent start3 = new Intent(getApplicationContext(),Forget_password.class);
                  startActivity(start3);
              }
          });
      //////////////////////////////////////////////////////////////////////
      ButterKnife.bind(this);
      final AnimatedViewPager_login pager = ButterKnife.findById(this, R.id.pager);
      final ImageView background = ButterKnife.findById(this, R.id.scrolling_background);
      int[] screenSize = screenSize();

      for (ImageView element : sharedElements) {
          @ColorRes int color = element.getId() != R.id.logo ? R.color.white_transparent : R.color.color_logo_log_in;
          DrawableCompat.setTint(element.getDrawable(), ContextCompat.getColor(this, color));
      }
      //load a very big image and resize it, so it fits our needs
      Glide.with(this)
              .load(R.drawable.busy)
              .asBitmap()
              .override(screenSize[0] * 2, screenSize[1])
              .diskCacheStrategy(DiskCacheStrategy.RESULT)
              .into(new ImageViewTarget<Bitmap>(background) {
                  @Override
                  protected void setResource(Bitmap resource) {
                      background.setImageBitmap(resource);
                      background.post(() -> {
                          //we need to scroll to the very left edge of the image
                          //fire the scale animation
                          background.scrollTo(-background.getWidth() / 2, 0);
                          ObjectAnimator xAnimator = ObjectAnimator.ofFloat(background, View.SCALE_X, 4f, background.getScaleX());
                          ObjectAnimator yAnimator = ObjectAnimator.ofFloat(background, View.SCALE_Y, 4f, background.getScaleY());
                          AnimatorSet set = new AnimatorSet();
                          set.playTogether(xAnimator, yAnimator);
                          set.setDuration(getResources().getInteger(R.integer.duration));
                          set.start();
                      });
                      pager.post(() ->
                      {
                          AuthAdapter_login adapter = new AuthAdapter_login(getSupportFragmentManager(), pager, background, sharedElements);
                          pager.setAdapter(adapter);
                      });
                  }
              });





  }

    private boolean CHECKLOGINACTIVITY()
    {
        mAuth = FirebaseAuth.getInstance();
        USER2 = mAuth.getCurrentUser();

        if(USER2 == null)
        {
                return false;
        }
        else
        {
                return true;
        }

    }

    private int[] screenSize()
  {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new int[]{size.x, size.y};
  }

  public void Login(String email,String password)
  {
      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if(task.isSuccessful())
          {
            Toast.makeText(getApplicationContext(),"PASS",Toast.LENGTH_LONG).show();
          }
        }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {

              if (e instanceof FirebaseAuthInvalidCredentialsException)
              {
                  Toast.makeText(getApplicationContext(),"Invalid Password",Toast.LENGTH_LONG).show();

              }
              else if (e instanceof FirebaseAuthInvalidUserException)
              {
                  String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

                  if (errorCode.equals("ERROR_USER_NOT_FOUND"))
                  {
                      Toast.makeText(getApplicationContext(),"User account has been disabled",Toast.LENGTH_LONG).show();
                     dialog("ACCOUNT NOT REGISTER","Create account on this email ?",email);
  //                    Toast.makeText(getApplicationContext(),"No Matching Account Found",Toast.LENGTH_LONG).show();
                  }
                  else
                  {
                      Toast.makeText(getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                  }
              }
          }
      });
  }
  public void Signup(String email,String password)
  {
    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful())
            {
                Toast.makeText(getApplicationContext(),"User Create",Toast.LENGTH_LONG).show();
            }


        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });
  }
  public void dialog(String msg1, String msg2, String email_id)
  {
      easyDialog.twoItem(LoginActivity_login.this,
              msg1+" \n "+msg2,
              email_id,R.drawable.account_not_found,
              "YES",
              "NO",
              new EasyDialog.showClickTwoItem() {
                  @Override
                  public void firstItem() {
                      Toast.makeText(LoginActivity_login.this, "click on yes", Toast.LENGTH_SHORT).show();
                    dialogsuccess();
                  }

                  @Override
                  public void secondItem() {
                      Toast.makeText(LoginActivity_login.this, "click on no", Toast.LENGTH_SHORT).show();
                  }
              });
  }
  public void dialogsuccess()
  {

      final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
              .setTitleText("Loading");
      pDialog.show();
      pDialog.setCancelable(false);
      new CountDownTimer(800 * 7, 800) {
          public void onTick(long millisUntilFinished) {
              // you can change the progress bar color by ProgressHelper every 800 millis
              i++;
              switch (i){
                  case 0:
                      pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                      break;
                  case 1:
                      pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                      break;
                  case 2:
                      pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                      break;
                  case 3:
                      pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                      break;
                  case 4:
                      pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                      break;
                  case 5:
                      pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                      break;
                  case 6:
                      pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                      break;
              }
          }

          public void onFinish() {
              i = -1;
              pDialog.setTitleText("Success!")
                      .setConfirmText("OK")
                      .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
          }
      }.start();




  }

 public boolean ValidateEmail()
 {
        String email1=email.getText().toString().trim();
     if(email.getText().toString().isEmpty())
     {
         email.setError("Field can't empty ");
         return false;
     }
     else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
     {
            email.setError("Please enter valid email");
            return false;
     }
     else
     {
         return true;
     }

 }
 public  boolean ValidatePassword()
 {
     String password1 = email.getText().toString().trim();
     if(password.getText().toString().isEmpty())
     {
         password.setError("Please enter password ");
         return false;
     }
     else if(!PASSWORD_PATTERN.matcher(password1).matches())
     {
         password.setError("Enter a valid email");
         return false;
     }
     else
     {
         return true;
     }
 }









}
