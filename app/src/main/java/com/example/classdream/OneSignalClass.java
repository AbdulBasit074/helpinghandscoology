package com.example.classdream;

import android.app.Application;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import com.onesignal.OneSignal;
public class OneSignalClass extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

    }
}
