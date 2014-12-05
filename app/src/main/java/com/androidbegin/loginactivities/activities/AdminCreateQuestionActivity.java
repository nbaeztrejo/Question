package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class AdminCreateQuestionActivity extends Activity {

    private EditText titleText;
    private EditText choice1Text;
    private EditText choice2Text;
    private EditText choice3Text;
    private EditText choice4Text;

    private String questionString;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    private String groupID;
    private boolean isAdmin;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_create_question_activity);

        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");
        isAdmin = b.getBoolean("isAdmin");
        groupName = b.getString("groupName");

        // Edittext objects that users put strings in
        titleText = (EditText) findViewById(R.id.questionTitle);
        choice1Text = (EditText) findViewById(R.id.choice1);
        choice2Text = (EditText) findViewById(R.id.choice2);
        choice3Text = (EditText) findViewById(R.id.choice3);
        choice4Text = (EditText) findViewById(R.id.choice4);

        // Save question to parse
        Button saveQuestion = (Button) findViewById(R.id.saveQuestion);

        // Save question listener
        saveQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // String values for the Question class
                questionString = titleText.getText().toString();
                choice1 = choice1Text.getText().toString();
                choice2 = choice2Text.getText().toString();
                choice3 = choice3Text.getText().toString();
                choice4 = choice4Text.getText().toString();

                // Checks input for errors
                if (choice1.length() == 0 || choice2.length() == 0 ||
                        choice3.length() == 0 || choice4.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter All Answer Choices", Toast.LENGTH_LONG).show();
                } else if (titleText.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Question Title", Toast.LENGTH_LONG).show();
                } else {

                    // Create an instance of the Question ParseObject subclass
                    Question question = new Question();

                    question.initialize(questionString, groupID, ParseUser.getCurrentUser().getObjectId());

                    question.addResponse(choice1);
                    question.addResponse(choice2);
                    question.addResponse(choice3);
                    question.addResponse(choice4);

                    question.saveInBackground();

                    ParseQuery<Group> groupQuery = Group.getQuery();
                    try {
                        Group group = groupQuery.get(groupID);
                        group.addQuestion(question);
                        group.saveInBackground();
                    }
                    catch (ParseException e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }

                    Bundle b = new Bundle();
                    b.putString("groupID", groupID);
                    b.putBoolean("isAdmin", isAdmin);
                    b.putString("groupName", groupName);
                    Intent intent = new Intent(
                            AdminCreateQuestionActivity.this,
                            AdminQuestionListingActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

        Bundle b = new Bundle();
        b.putString("groupID", groupID);
        b.putBoolean("isAdmin", isAdmin);
        b.putString("groupName", groupName);
        Intent intent = new Intent(AdminCreateQuestionActivity.this,
                AdminQuestionListingActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();

    }

}
