package com.example.classdream;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindViews;
import butterknife.ButterKnife;


public class LogInFragment_login extends AuthFragment_login {

    TextInputEditText editTextemail,editTextpassword;
    @BindViews(value = {R.id.email_input_edit, R.id.password_input_edit})
    protected List<TextInputEditText> views;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile
            ("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                  //  "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");


    @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (view != null)
    {
                caption.setText(getString(R.string.log_in_label));
                view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_log_in));
                for (TextInputEditText editText : views)
               {
                       if (editText.getId() == R.id.password_input_edit)
                       {
                                   editTextpassword =editText;
                               final TextInputLayout inputLayout = ButterKnife.findById(view, R.id.password_input);
                               Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                               inputLayout.setTypeface(boldTypeface);
                               editText.addTextChangedListener(new TextWatcherAdapter_login()
                               {
                                @Override
                                public void afterTextChanged(Editable editable)
                                {
                                  inputLayout.setPasswordVisibilityToggleEnabled(editable.length() > 0);
                                }
                              });
                       }
                       editText.setOnFocusChangeListener((temp, hasFocus) ->
                       {
                              if (!hasFocus)
                              {
                                boolean isEnabled = editText.getText().length() > 0;
                                editText.setSelected(isEnabled);
                              }
                        });
                       if(editText.getId() == R.id.email_input_edit)
                       {
                           editTextemail = editText;
                       }
                }


    }








    caption.setOnClickListener(new View.OnClickListener()
           {

                      @Override
                      public void onClick(View v)
                      {
                            if(ValidateEmail() && ValidatePassword())
                            {
                                Toast.makeText(getContext(),"Loading...",Toast.LENGTH_LONG).show();

                                String email_login = editTextemail.getText().toString().trim();
                                String password_login = editTextpassword.getText().toString().trim();
                                Login user = new Login(email_login,password_login,getContext());
                               if(user.Login_account())
                                {
                                }
                            }
                      }


           });







    }

///////////////////////////////////////////////VALIDATE PHASE///////////////////////////////////////

    public boolean ValidateEmail()
    {



        String email1=editTextemail.getText().toString().trim();
        if(editTextemail.getText().toString().isEmpty())
        {
            editTextemail.setError("Field can't empty ");
            editTextemail.requestFocus();

            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
        {
            editTextemail.setError("Please enter valid email");
            editTextemail.requestFocus();
            return false;
        }
        else
        {
            return true;
        }


    }

    public  boolean ValidatePassword()
    {
        String password1 = editTextpassword.getText().toString().trim();
        if(editTextpassword.getText().toString().isEmpty())
        {
            editTextpassword.setError("Please enter password ");
            editTextpassword.requestFocus();

            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(password1).matches())
        {
            editTextpassword.setError("More then 4 character");
            editTextpassword.requestFocus();

            return false;
        }
        else
        {
            return true;
        }
    }

    /////////////////////////////Login/////////////////////////////////////////////////////////////
    public void Login(String email,String password)
    {

    }
  ///////////////////////////////////////////////ALERT////////////////////////////////////////////////////////

public void dialog()
{
    new SweetAlertDialog(getActivity() , SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Oops...")
            .setContentText("Email not Register !")
            .show();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public int authLayout() {
    return R.layout.login_fragment;
  }

  @Override
  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public void fold() {
    lock = false;
    Rotate_login transition = new Rotate_login();
    transition.setEndAngle(-90f);
    transition.addTarget(caption);
    TransitionSet set = new TransitionSet();
    set.setDuration(getResources().getInteger(R.integer.duration));
    ChangeBounds changeBounds = new ChangeBounds();
    set.addTransition(changeBounds);
    set.addTransition(transition);
    TextSizeTransition_login sizeTransition = new TextSizeTransition_login();
    sizeTransition.addTarget(caption);
    set.addTransition(sizeTransition);
    set.setOrdering(TransitionSet.ORDERING_TOGETHER);
    final float padding = getResources().getDimension(R.dimen.folded_label_padding) / 2;
    set.addListener(new Transition.TransitionListenerAdapter() {
      @Override
      public void onTransitionEnd(Transition transition) {
        super.onTransitionEnd(transition);
        caption.setTranslationX(-padding);
        caption.setRotation(0);
        caption.setVerticalText(true);
        caption.requestLayout();

      }
    });
    TransitionManager.beginDelayedTransition(parent, set);
    caption.setTextSize(TypedValue.COMPLEX_UNIT_PX, caption.getTextSize() / 2);
    caption.setTextColor(Color.WHITE);
    ConstraintLayout.LayoutParams params = getParams();
    params.leftToLeft = ConstraintLayout.LayoutParams.UNSET;
    params.verticalBias = 0.5f;
    caption.setLayoutParams(params);
    caption.setTranslationX(caption.getWidth() / 8 - padding);
  }

  @Override
  public void clearFocus() {
    for (View view : views) view.clearFocus();
  }

}
