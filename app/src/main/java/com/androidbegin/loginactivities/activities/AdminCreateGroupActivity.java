package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Sara on 11/18/14.
 */
public class AdminCreateGroupActivity extends Activity {

    private EditText groupName;
    private String groupNameString;
    private String groupObjectID;
    private Group groupCheck;
    private boolean groupExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_create_group_activity);

        // Edittext objects that users put strings in
        groupName = (EditText) findViewById(R.id.groupName);

        Button saveGroup = (Button) findViewById(R.id.saveGroup);

        saveGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Get group name from edit text
                groupNameString = groupName.getText().toString();

                // Begin various checks of the input
                ParseQuery<Group> query = Group.getQuery();
                query.whereEqualTo("name", groupNameString);
                try {
                    groupObjectID = query.getFirst().getObjectId();
                    groupCheck = query.get(groupObjectID);
                    groupExists = true;
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                    groupExists = false;
                }

                // Checks if the given group name is already taken
                if (groupExists){
                    Toast.makeText(AdminCreateGroupActivity.this,
                            "This group name already exists. Please enter a unique group name.",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    // Get user name from edit text
                    if (groupNameString.length() != 0 && isAlphaNumeric(groupNameString)) {

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

                        Intent intent = new Intent(
                                AdminCreateGroupActivity.this,
                                AdminViewGroupActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please Enter Valid Group Title", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminCreateGroupActivity.this,
                Welcome.class);
        startActivity(intent);
        finish();
    }


    public boolean isAlphaNumeric(String s){
        String pattern= "^[a-zA-Z0-9]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;
    }

}

