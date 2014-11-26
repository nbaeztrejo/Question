package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Sara on 11/18/14.
 */
public class AdminCreateGroupActivity extends Activity {

    EditText groupName;
    //EditText user1;

    String groupNameString;
    //String user1Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.creategroup);

        // Edittext objects that users put strings in
        groupName = (EditText) findViewById(R.id.groupName);
       // user1 = (EditText) findViewById(R.id.user1);

        Button saveGroup = (Button) findViewById(R.id.saveGroup);

        saveGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //get group name from edit text
                groupNameString = groupName.getText().toString();

                //get user name from edit text
                //user1Name = user1.getText().toString();

                Group group = new Group();
                group.initialize(groupNameString);


                ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                try {
                    ParseUser temp = userQuery.getFirst();
                    String groupID = group.getObjectId();

                    ParseUser.getCurrentUser().add("groups", groupID);
                    ParseUser.getCurrentUser().saveInBackground();
                } catch (ParseException e) {

                }

                /*ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                userQuery.whereEqualTo("username",user1Name);
                try {
                    //get user associated with username
                    ParseUser temp = userQuery.getFirst();
                    //add that user's objectID to group
                    group.addUser(temp.getObjectId());
                    group.saveInBackground();

                    String groupID = group.getObjectId();

                    //add group to user's list of groups
                    temp.add("groups",groupID);
                    //add group to current user's list of groups
                    ParseUser.getCurrentUser().add("groups",groupID);
                    ParseUser.getCurrentUser().saveInBackground();
                } catch (ParseException e)
                {
                    group.addUser("Failure");
                    group.saveInBackground();
                }*/


                //ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                //        "Group");
                //try {

                //}
                //catch (ParseException e) {
                //    Log.e("Error", e.getMessage());
                //    e.printStackTrace();
                //}

                Intent intent = new Intent(
                        AdminCreateGroupActivity.this,
                        WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCreateGroupActivity.this,
                AdminViewGroupActivity.class);
        startActivity(intent);
        finish();
    }

}