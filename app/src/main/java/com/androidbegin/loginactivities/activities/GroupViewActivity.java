package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kevin on 11/29/2014.
 */
public class GroupViewActivity extends Activity {

    private boolean isAdmin;
    private String groupID;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_view_activity);

        Bundle b = this.getIntent().getExtras();
        isAdmin = b.getBoolean("isAdmin");
        groupID = b.getString("groupID");
        groupName = b.getString("groupName");

        // Locate TextView in welcome.xmlroups.xml
        TextView txt = (TextView) findViewById(R.id.txt);

        // Set the currentUser String into TextView
        txt.setText("You are viewing the group, " + groupName);

        // View Questions Button
        Button viewQuestions = (Button) findViewById(R.id.viewQuestions);

        // View Questions Listener
        viewQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putBoolean("isAdmin", isAdmin);
                b.putString("groupName", groupName);
                if (isAdmin) {
                    intent = new Intent(GroupViewActivity.this,
                            AdminQuestionListingActivity.class);
                }
                else{
                    intent = new Intent(GroupViewActivity.this,
                            NormalQuestionListingActivity.class);
                }
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        // View Users Button
        Button viewUsers = (Button) findViewById(R.id.viewUsers);

        // View Users Listener
        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putBoolean("isAdmin", isAdmin);
                b.putString("groupName", groupName);
                Intent intent = new Intent(GroupViewActivity.this,
                        SeeUsersActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        Bundle b = new Bundle();
        b.putString("groupID", groupID);
        b.putBoolean("isAdmin", isAdmin);
        b.putString("groupName", groupName);
        if (isAdmin) {
            intent = new Intent(GroupViewActivity.this,
                    AdminViewGroupActivity.class);
        }
        else {
            intent = new Intent (GroupViewActivity.this,
                    NormalViewGroupActivity.class);
        }
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

}
