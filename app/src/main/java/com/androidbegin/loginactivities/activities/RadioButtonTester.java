package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;

public class RadioButtonTester extends Activity {

    private RadioGroup radio0;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;

    private RadioButton selectRadio;
    private int radioIndex;

    private Button submitButton;
    private Button closeQuestionButton;

    private String questionID;
    private String groupID;
    private String userID;
    private ArrayList<String> userCollect;
    private ArrayList<Integer> responseCollect;
    private String[] choicesArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.radiobuttonsingleitemview);

        Bundle b = this.getIntent().getExtras();
        //String[] array = b.getStringArray("ansArray");
        //final String askerID = b.getString("askerID");
        choicesArray = b.getStringArray("ansArray");
        questionID = b.getString("questionID");
        //groupID = b.getString("groupID");
        //userID = b.getString("userID");
        userCollect = b.getStringArrayList("userCollect");
        responseCollect = b.getIntegerArrayList("responseCollect");

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        //declare radio buttons and submit and close button
        radio0 = (RadioGroup) findViewById(R.id.radio0);
        submitButton = (Button) findViewById(R.id.submit);
        closeQuestionButton = (Button) findViewById(R.id.closeQuestion);

        //display question choices in radiobutton
        for (int i = 0; i < choicesArray.length; i++) {
            ((RadioButton) radio0.getChildAt(i)).setText(choicesArray[i]);
        }

        submitButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseQuery<Question> query = ParseQuery.getQuery("Question");
                //check if user has already answered this question
                if (userCollect.contains(userID)) {
                    Toast.makeText(
                            getApplicationContext(),
                            "You have already submitted a response.",
                            Toast.LENGTH_LONG).show();
                }
                // If not, proceed normally
                else {
                    // get selected radio button from radioGroup
                    int selectedId = radio0.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    selectRadio = (RadioButton) findViewById(selectedId);

                    radioIndex = radio0.indexOfChild(selectRadio);

                    try {
                        Question question = query.get(questionID);

                        //updates the array in our Question ParseObject with a new response
                        question.incrementResponse(radioIndex);
                        question.incrementUser(userID);
                        question.saveInBackground();

                        //inform user they submitted
                        Toast.makeText(
                                getApplicationContext(),
                                "You have submitted your response: " + selectRadio.getText(),
                                Toast.LENGTH_LONG).show();

                        //redirect to current questions
                        Intent intent = new Intent(RadioButtonTester.this,
                                AdminQuestionListingActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (com.parse.ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(RadioButtonTester.this,
                                "Failed", Toast.LENGTH_SHORT).show();
                    }
                    // Retrieve the object by id
                    /*query.getInBackground(questionID, new GetCallback<Question>() {
                        public void done(Question question, com.parse.ParseException e) {
                            if (e == null) {
                                //if ((opinion != "") || opinion != null) {
                                    //updates the array in our Question ParseObject with a new response
                                    question.incrementResponse(radioIndex);
                                    question.incrementUser(userID);
                                    question.saveInBackground();

                                    //inform user they submitted
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "You have submitted your response: " + selectRadio.getText(),
                                            Toast.LENGTH_LONG).show();

                                    //redirect to current questions
                                    Intent intent = new Intent(RadioButtonTester.this,
                                            AdminQuestionListingActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Please select a response.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                    });*/


                    Toast.makeText(RadioButtonTester.this,
                            submitButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }

        });

        //Close question if closeQuestion button clicked and redirect to pastQuestions page
        /*closeQuestionButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ParseQuery<Question> query = ParseQuery.getQuery("Question");

                // Retrieve the object by id
                query.getInBackground(groupID, new GetCallback<Question>() {
                    public void done(Question question, com.parse.ParseException e) {
                        if (e == null) {
                            // update the condition isOpen to false
                            question.put("isOpen", false);
                            question.saveInBackground();
                        }
                    }

                });

                //redirect to past questions
                Intent intent = new Intent(RadioButtonTester.this,
                        AdminQuestionListingActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

    }
}