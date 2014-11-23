package com.androidbegin.loginactivities.activities;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Welcome extends Activity {

    // Declare Variable
    Button logout;
    Button createGroups;
    Button viewGroups;
    //    private EditText result;
    final Context context = this;
    //    private Button button; //delete
    String groupName;
    ArrayList<String> groupUsers;

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
                       ViewGroupActivity2.class);
                startActivity(intent);
                finish();
            }
        });


        createGroups.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this,
                        //changed from
                        //ViewGroupActivity.class);
                        CreateGroupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // createGroups Button Click Listener with Dialog
        /*createGroups.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                // get creategroups.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.creategroups, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set creategroups.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                              // get user input and set it to result
//                              // edit text
//                              result.setText(userInput.getText() + " group created");

                                //save the group question
                                groupName = userInput.getText().toString();

                                //create a new group object
                                Group group = new Group();
                                group.saveInBackground();
                                group.initialize(groupName);


                                //String groupID = group.getObjectId();
                                //String groupID = "failure";
                                //currentUser.add("groups",groupID);
                                //currentUser.saveInBackground();

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });*/
    }
}