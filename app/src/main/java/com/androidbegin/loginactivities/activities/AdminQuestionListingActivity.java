package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AdminQuestionListingActivity extends Activity {

    private String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminquestionlist);

        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");

        //Past Question Button
        Button pastQuestion = (Button) findViewById(R.id.pastQuestion);

        //Past Question Listener
        pastQuestion.setOnClickListener(new View.OnClickListener(){

            public void onClick (View arg0){
                Bundle b = new Bundle();
                b.putString("groupID", groupID);

                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminPastQuestionActivity.class);

                intent.putExtras(b);

                startActivity(intent);

                finish();
            }
        });

        //Current Question Button
        Button currentQuestion = (Button) findViewById(R.id.currentQuestion);

        //Current Question Listener
        currentQuestion.setOnClickListener(new View.OnClickListener(){

            public void onClick (View arg0){
                Bundle b = new Bundle();
                b.putString("groupID", groupID);

                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminCurrentQuestionActivity.class);

                intent.putExtras(b);

                startActivity(intent);

                finish();

            }
        });

        //Future Question Button
        /*Button futureQuestion = (Button) findViewById(R.id.futureQuestion);

        //Future Question Listener
        futureQuestion.setOnClickListener(new View.OnClickListener(){

            public void onClick (View arg0){
                Bundle b = new Bundle();
                b.putString("groupID", groupObjectID);

                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        FutureQuestionActivity.class);

                intent.putExtras(b);

                startActivity(intent);
            }
        });*/


        //Create Question Button
        Button createQuestion = (Button) findViewById(R.id.createQuestion);

        //Create Question Listener
        createQuestion.setOnClickListener(new View.OnClickListener(){

            public void onClick (View arg0){
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminCreateQuestionActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        //Add User Button
        Button addUser = (Button) findViewById(R.id.addUser);

        //Add User Listener
        addUser.setOnClickListener(new View.OnClickListener(){

            public void onClick (View arg0){
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminAddUserActivity.class);
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
        Intent intent = new Intent(AdminQuestionListingActivity.this,
                WelcomeActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}

