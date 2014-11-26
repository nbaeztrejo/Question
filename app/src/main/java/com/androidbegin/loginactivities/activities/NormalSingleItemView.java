package com.androidbegin.loginactivities.activities;

/**
 * Created by Sara Shi on 11/22/2014.
 */

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
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class NormalSingleItemView extends Activity {

    //Close question button
    Button closeQuestion;
    Button submit;
    RadioGroup rgOpinion;
    RadioButton selectRadio;
    int responseChoice = -1;
    String opinion;
    public int radioIndex;
    private String groupID;


    //called when the activity is first created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.normal_singleitemview);

        Bundle b = this.getIntent().getExtras();
        String[] array = b.getStringArray("ansArray");
        groupID = b.getString("groupID");
        final String askerID = b.getString("askerID");
        final String questionID = b.getString("questionID");
        final String userID = b.getString("userID");
        final ArrayList<String> userCollect = b.getStringArrayList("userCollect");
        final ArrayList<Integer> responseCollect = b.getIntegerArrayList("responseCollect");

        // Init Widget GUI
        rgOpinion = (RadioGroup) findViewById(R.id.radio0);

        // update the text for each radio button
        for (int i = 0; i < array.length; i++) {
            ((RadioButton) rgOpinion.getChildAt(i)).setText(array[i].toString());
        }

        // Submit Response Button
        submit = (Button) findViewById(R.id.submit);

        // Listener
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ParseQuery<Question> query = ParseQuery.getQuery("Question");

                if (userCollect.contains(userID)) {
                    Toast.makeText(
                            getApplicationContext(),
                            "You have already submitted a response.",
                            Toast.LENGTH_LONG).show();
                }
                // If not, proceed normally
                else {

                    selectRadio = (RadioButton) findViewById(rgOpinion
                            .getCheckedRadioButtonId());
                    opinion = selectRadio.getText().toString();

                    radioIndex = rgOpinion.indexOfChild(selectRadio);


                    // Retrieve the object by id
                    query.getInBackground(questionID, new GetCallback<Question>() {
                        public void done(Question question, com.parse.ParseException e) {
                            if (e == null) {
                                if ((opinion != "") || opinion != null) {
                                    //updates the array in our Question ParseObject with a new response
                                    question.incrementResponse(radioIndex);
                                    question.incrementUser(userID);
                                    question.addUserChoice(radioIndex);
                                    question.saveInBackground();

                                    ParseQuery<Group> groupQuery = Group.getQuery();
                                    try {
                                        Group group = groupQuery.get(groupID);
                                        group.add("questions", question.getObjectId());
                                    } catch (ParseException groupE)
                                    {

                                    }

                                    //inform user they submitted a response
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "You have submitted your response: " + opinion,
                                            Toast.LENGTH_LONG).show();


                                    Bundle b=new Bundle();
                                    b.putString("groupID", groupID);

                                    //redirect to current questions
                                    Intent intent = new Intent(NormalSingleItemView.this,
                                            NormalCurrentQuestionActivity.class);

                                    intent.putExtras(b);

                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Please select a response.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        Bundle b=new Bundle();
        b.putString("groupID", groupID);

        Intent intent = new Intent(NormalSingleItemView.this,
                NormalCurrentQuestionActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}