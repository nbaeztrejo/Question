package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NormalQuestionListingActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normalquestionlist);


    /*
       Arbitrary buttons only for the sake of linking activities
    */

    Button pastQuestion = (Button) findViewById(R.id.pastQuestion);

    // Listener
    pastQuestion.setOnClickListener(new View.OnClickListener(){

        public void onClick (View arg0){
        Intent intent = new Intent(NormalQuestionListingActivity.this,
                PastQuestionActivity.class);
        startActivity(intent);
        finish();
        }
    });


    Button currentQuestion = (Button) findViewById(R.id.currentQuestion);

    // Listener
    currentQuestion.setOnClickListener(new View.OnClickListener(){

        public void onClick (View arg0){
        Intent intent = new Intent(NormalQuestionListingActivity.this,
            CurrentQuestionActivity.class);
        startActivity(intent);
        finish();

        }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NormalQuestionListingActivity.this,
                ViewGroupActivity.class);
        startActivity(intent);
        finish();
    }

}
