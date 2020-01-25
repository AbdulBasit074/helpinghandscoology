package com.example.classdream;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User{
    public String NAME;
    public String USER_ID;
    public String PROFILE_IMAGE;
    public String EMAIL;
    public Boolean DONE=false;
    public HashMap<String,String> Z_JOIN_CLASSES = new HashMap<String,String>();
    public HashMap<String,String> Z_CREATE_CLASSES = new HashMap<String,String>();
    public HashMap<String,String> Z_NOTIFICATION = new HashMap<String,String>();

    public User()
    {
    }

}