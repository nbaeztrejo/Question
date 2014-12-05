package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class NormalQuestionListingActivity extends Activity {

    private String groupID;
    private boolean isAdmin;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_question_listing_activity);

        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");
        isAdmin = b.getBoolean("isAdmin");
        groupName = b.getString("groupName");

        //Past Question Button
        Button closedQuestion = (Button) findViewById(R.id.closedQuestion);

        //Past Question Listener
        closedQuestion.setOnClickListener(new View.OnClickListener(){

            public void onClick (View arg0){
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putString("groupName", groupName);

                Intent intent = new Intent(NormalQuestionListingActivity.this,
                        NormalPastQuestionActivity.class);

                intent.putExtras(b);

                startActivity(intent);

                finish();
            }
        });

        //Current Question Button
        Button openQuestion = (Button) findViewById(R.id.openQuestion);

        //Current Question Listener
        openQuestion.setOnClickListener(new View.OnClickListener(){

            public void onClick (View arg0){
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putString("groupName", groupName);

                Intent intent = new Intent(NormalQuestionListingActivity.this,
                        NormalCurrentQuestionActivity.class);

                intent.putExtras(b);

                startActivity(intent);

                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {

        Bundle b = new Bundle();
        b.putString("groupID", groupID);
        b.putBoolean("isAdmin", isAdmin);
        b.putString("groupName", groupName);

        Intent intent = new Intent(NormalQuestionListingActivity.this,
                NormalViewGroupActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}

