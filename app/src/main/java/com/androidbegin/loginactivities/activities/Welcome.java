package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;

public class Welcome extends Activity {

    // Declare Variable
    private Button logout;
    private Button createGroups;
    private Button viewGroups;
    //    private EditText result;
    final Context context = this;
    //    private Button button; //delete
    private String groupName;
    private ArrayList<String> groupUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from welcome.xml
        setContentView(R.layout.welcome);

        // Retrieve current user from Parse.com
        final ParseUser currentUser = ParseUser.getCurrentUser();

        // Convert currentUser into String
        final String struser = currentUser.getUsername().toString();
        final String userID = currentUser.getObjectId();

        // Locate TextView in welcome.xmlroups.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        // Set the currentUser String into TextView
        txtuser.setText("You are logged in as " + struser);

        // Locate Button in creategroups.xml
        logout = (Button) findViewById(R.id.logout);
        viewGroups = (Button) findViewById(R.id.viewGroups);
        createGroups = (Button) findViewById(R.id.createGroups);
//        result = (EditText) findViewById(R.id.editTextResult);
//        button = (Button) findViewById(R.id.buttonPrompt);


        // Logout Button Click Listener
        logout.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                ParseUser.logOut();
                Intent intent = new Intent(
                        Welcome.this,
                        LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // viewGroups Button Click Listener
        viewGroups.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(Welcome.this,
                        //changed from
                        //ViewGroupActivity.class);
                       GroupDirectActivity.class);
                startActivity(intent);
                finish();
            }
        });


        createGroups.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this,
                    AdminCreateGroupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public boolean isAlphaNumeric(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }
}

