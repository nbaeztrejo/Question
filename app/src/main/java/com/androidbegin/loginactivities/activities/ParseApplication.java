package com.androidbegin.loginactivities.activities;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Question.class);
        ParseObject.registerSubclass(Group.class);

        // Add your initialization code here
        Parse.initialize(this, "OQr8gPEjot4i3ZLK3UiTk18R2mpC1A4qZ50InQ0G", "NbxWrOQYto0WZv3xihfep8xosqgv6nGzTuEQ9YNV");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}