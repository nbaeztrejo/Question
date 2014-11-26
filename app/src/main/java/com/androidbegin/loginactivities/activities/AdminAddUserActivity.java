package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;

public class AdminAddUserActivity extends Activity {

    private EditText userEditText;
    private Button userAddButton;
    private String userObjectID;
    private boolean userExists;
    private String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);

        userEditText = (EditText) findViewById(R.id.userText);
        //button and listener
        userAddButton = (Button) findViewById(R.id.saveUser);
        userAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleUsername();
            }
        });

        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");

    }

    public void handleUsername() {

        ParseUser currentUser = ParseUser.getCurrentUser();

        String username = userEditText.getText().toString().trim();

        // userExists = false;
        // Defined Array values to show in ListView
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);
        try {
            userObjectID = query.getFirst().getObjectId();
            userExists = true;
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
            userObjectID = "false";
            userExists = false;
        }

        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder("");
        if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }

        else if (!userExists && username.length() > 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append("User does not exist. Please enter a valid username");
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(AdminAddUserActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        //update User info
        final ProgressDialog dialog = new ProgressDialog(AdminAddUserActivity.this);
        dialog.setMessage("Adding user to group...");
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

        //add other user to contacts so you can creep
        ParseQuery<Group> groupQuery = Group.getQuery();
        try {
            ParseUser user = query.get(userObjectID);
            user.add("groups", groupID);
            user.saveInBackground();
            Group group = groupQuery.get(groupID);
            group.addUser(userObjectID);
            group.saveInBackground();
        } catch (com.parse.ParseException e){}

        dialog.dismiss();

        Toast.makeText(AdminAddUserActivity.this, "Successfully added a new user to your group.", Toast.LENGTH_LONG).show();

        Bundle b = new Bundle();
        b.putString("groupID", groupID);

        Intent intent = new Intent(AdminAddUserActivity.this, AdminQuestionListingActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menu.findItem(R.id.action_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(AddContactActivity.this, SettingsActivity.class));
                return true;
            }
        });
        return true;
    }*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminAddUserActivity.this,
                AdminQuestionListingActivity.class);
        startActivity(intent);
        finish();
    }

}
