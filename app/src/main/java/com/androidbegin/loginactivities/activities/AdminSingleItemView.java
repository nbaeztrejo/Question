package com.androidbegin.loginactivities.activities;

/**
 * Created by Brenda Garcia on 10/28/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class AdminSingleItemView extends Activity {

    private Button closeQuestion;
    private Button submit;
    private RadioGroup rgOpinion;
    private RadioButton selectRadio;
    private int responseChoice = -1;
    private String opinion;
    private int radioIndex;
    private String groupID;
    private boolean isAdmin;
    private String groupName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_singleitemview);

        Bundle b = this.getIntent().getExtras();
        String[] array = b.getStringArray("ansArray");
        groupID = b.getString("groupID");
        isAdmin = b.getBoolean("isAdmin");
        groupName = b.getString("groupName");
        final String askerID = b.getString("askerID");
        final String questionID = b.getString("questionID");
        final String userID = b.getString("userID");
        final ArrayList<String> userCollect = b.getStringArrayList("userCollect");
        final ArrayList<Integer> responseCollect = b.getIntegerArrayList("responseCollect");
        final ArrayList<Integer> userChoiceCollect = b.getIntegerArrayList("userChoicesCollect");


        // Init Widget GUI
        rgOpinion = (RadioGroup) findViewById(R.id.radio0);

        // Update the text for each radio button
        for (int i = 0; i < array.length; i++) {
            ((RadioButton) rgOpinion.getChildAt(i)).setText(array[i].toString());
        }

        // Get closeQuestion button
        closeQuestion = (Button) findViewById(R.id.closeQuestion);

        //Close question if closeQuestion button clicked and redirect to pastQuestions page
        closeQuestion.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                ParseQuery<Question> query = ParseQuery.getQuery("Question");

                try {
                    Question question = query.get(questionID);
                    question.put("isOpen", false);
                    question.saveInBackground();
                } catch (ParseException e) {

                }

                // Redirect to past questions
                Bundle b=new Bundle();
                b.putString("groupID", groupID);
                b.putBoolean("isAdmin", isAdmin);
                b.putString("groupName", groupName);
                Intent intent = new Intent(AdminSingleItemView.this,
                        AdminQuestionListingActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        Bundle b=new Bundle();
        b.putString("groupID", groupID);
        b.putBoolean("isAdmin", isAdmin);
        b.putString("groupName", groupName);
        Intent intent = new Intent(AdminSingleItemView.this,
                AdminCurrentQuestionActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}