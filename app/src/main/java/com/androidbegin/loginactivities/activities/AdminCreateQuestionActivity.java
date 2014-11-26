package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class AdminCreateQuestionActivity extends Activity {

    EditText titleText;
    EditText choice1Text;
    EditText choice2Text;
    EditText choice3Text;
    EditText choice4Text;

    String questionString;
    String choice1;
    String choice2;
    String choice3;
    String choice4;
    //String choice5;

    String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.createquestion);

        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");


        // Edittext objects that users put strings in
        titleText = (EditText) findViewById(R.id.questionTitle);
        choice1Text = (EditText) findViewById(R.id.choice1);
        choice2Text = (EditText) findViewById(R.id.choice2);
        choice3Text = (EditText) findViewById(R.id.choice3);
        choice4Text = (EditText) findViewById(R.id.choice4);
        //EditText choice5Text = (EditText) findViewById(R.id.choice5);

        // get input strings
        //choice3 = choice3Text.getText().toString();
        //choice4 = choice4Text.getText().toString();
        //choice5 = choice5Text.getText().toString();

        // Save question to parse
        Button saveQuestion = (Button) findViewById(R.id.saveQuestion);

        saveQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                questionString = titleText.getText().toString();
                choice1 = choice1Text.getText().toString();
                choice2 = choice2Text.getText().toString();
                choice3 = choice3Text.getText().toString();
                choice4 = choice4Text.getText().toString();

                Question question = new Question();

                //question.initialize("Hi I am testing");
                //question.addResponse("A");

                question.initialize(questionString);

                question.addResponse(choice1);
                question.addResponse(choice2);
                question.addResponse(choice3);
                question.addResponse(choice4);
                //question.addResponse(choice5);

                question.saveInBackground();

                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Group");
                try {
                    ParseObject group = query.get(groupID);
                    group.add("questions", question.getObjectId());
                    group.saveInBackground();

                }
                catch (ParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }


                Bundle b = new Bundle();
                b.putString("groupID", groupID);

                Intent intent = new Intent(
                        AdminCreateQuestionActivity.this,
                        AdminQuestionListingActivity.class);
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
        Intent intent = new Intent(AdminCreateQuestionActivity.this,
                AdminQuestionListingActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();

    }

}
