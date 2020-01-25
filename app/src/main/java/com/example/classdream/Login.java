package com.example.classdream;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;



public class Login {
    FirebaseAuth mAuth1;
    String cemail;
    String cpassword;
    Context c;
    boolean result = false;

    public Login(String hemail,String hpassword,Context C)
    {
        this.cemail = hemail;
        this.cpassword =  hpassword;
        this.c = C;
    }


    public boolean Login_account()
    {
        mAuth1= FirebaseAuth.getInstance();

        mAuth1.signInWithEmailAndPassword(cemail,cpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {

                    if(mAuth1.getCurrentUser().isEmailVerified())
                    {
                        Intent start3 = new Intent(c,classroom.class);
                        c.startActivity(start3);
                        Toast.makeText(c,"Account",Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        Dialog dialog = new Dialog(c,"verifiedyouraccount","nothing");
                        dialog.Dialog_Error("Not Verify","Please verify Email Code");

                    }
                }
            }


        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                    if (e instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        Dialog dialog = new Dialog(c,"verifiedyouraccount","nothing");
                        dialog.Dialog_Error("Invalid Password","Check Your Password");
                    }
                    else if (e instanceof FirebaseAuthInvalidUserException)
                    {
                        String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();

                        if (errorCode.equals("ERROR_USER_NOT_FOUND"))
                        {

                            Dialog dialog = new Dialog(c,"verifiedyouraccount","nothing");
                            dialog.Dialog_Error("Account Not Found","Check Your Email");
                        }
                        else
                        {
                            Toast.makeText(c,"error",Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });
        return result;

    }

    public void Sign_Up()
    {
        mAuth1 = FirebaseAuth.getInstance();
        mAuth1.createUserWithEmailAndPassword(cemail,cpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {

                        if(task.isSuccessful())
                        {
                            mAuth1.getCurrentUser().sendEmailVerification().
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        mAuth1.signOut();
                                        Dialog dialog = new Dialog(c,"signup","nothing");
                                        dialog.Dialog_1("Loading","Verify Verification Code in Email");
                                        Toast.makeText(c,"Click On link to Verified Account",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        }

                }
            })
            .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                public void onFailure(@NonNull Exception e)
                 {
                     Toast.makeText(c,"Fail Account "+e,Toast.LENGTH_LONG).show();


                 }
                });

    }
    public void USER_PROFILE()
    {







    }

}
