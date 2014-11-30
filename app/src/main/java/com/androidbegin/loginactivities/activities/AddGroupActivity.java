package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class AddGroupActivity extends Activity {

    private Group group;
    private EditText groupEditText;
    private Button groupAddButton;
    private String groupObjectID;
    private boolean groupExists;
    private List<ParseUser> groupUsers;
    private String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_activity);

        // Asks the user for an input (username)
        groupEditText = (EditText) findViewById(R.id.groupText);
        // Button to confirm user information
        groupAddButton = (Button) findViewById(R.id.saveGroup);
        // Listener
        groupAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleUsername();
            }
        });

        // Pulls relevant information from the bundle
        // Bundle b = this.getIntent().getExtras();
        // groupID = b.getString("groupID");

    }

    public void handleUsername() {

        ParseUser currentUser = ParseUser.getCurrentUser();

        String groupname = groupEditText.getText().toString().trim();

        // Begin various checks of the input
        ParseQuery<Group> query = Group.getQuery();
        query.whereEqualTo("name", groupname);
        try {
            groupObjectID = query.getFirst().getObjectId();
            group = query.get(groupObjectID);
            groupUsers = group.getUsers();
            groupExists = true;
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
            groupObjectID = "false";
            groupExists = false;
        }

        // Pre-sets the error to be false
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder("");

        // Checks if the input is of length 0
        if (groupname.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_groupname));
        }

        // Checks if the user is already in the group
        else if (groupUsers.contains(currentUser)){
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append("You are already in this group");
        }

        // Checks if the input is a valid group
        else if (!groupExists && groupname.length() > 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append("Group does not exist. Please enter a valid group name");
        }

        // Checks if the user is in the group
        /*else if (currentUser.getObjectId() == group.getAdmin().getObjectId()){
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append("You are the admin of this group");
        }*/

        // Checks if the user is in the group
        else if (currentUser.getList("groups").contains(groupObjectID)){
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append("You are already in this group");
        }

        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(AddGroupActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Update user info
        final ProgressDialog dialog = new ProgressDialog(AddGroupActivity.this);
        dialog.setMessage("Adding group...");
        dialog.show();


        // Retrieve the object by id
        //query.getFirst().getList("contacts");

        /*query.getInBackground(userObjectID, new GetCallback<ParseUser>() {
            public void done(ParseUser toBeAdded, ParseException e) {
                if (e == null) {
                    toBeAdded.add("contacts", currentUser.getObjectId());
                    toBeAdded.saveInBackground();
                }
            }
        });*/

        // Add userID to Group ParseObject subclass
        // Add groupID to ParseUser group array <---- NEEDS A WORKAROUND, NO ACL PERMISSION
        ParseQuery<Group> groupQuery = Group.getQuery();
        try {
            currentUser.add("groups", groupObjectID);
            currentUser.saveInBackground();
            Group group = groupQuery.get(groupObjectID);
            group.addUser(currentUser.getObjectId());
            group.saveInBackground();
        } catch (com.parse.ParseException e){}

        dialog.dismiss();

        Toast.makeText(AddGroupActivity.this, "Successfully added a new group.", Toast.LENGTH_LONG).show();

        //Bundle b = new Bundle();
        //b.putString("groupID", groupID);

        Intent intent = new Intent(AddGroupActivity.this, GroupDirectActivity.class);
        //intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddGroupActivity.this,
                Welcome.class);
        startActivity(intent);
        finish();
    }

}