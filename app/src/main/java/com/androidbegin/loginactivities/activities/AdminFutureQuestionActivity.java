package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AdminFutureQuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_future_question_activity);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminFutureQuestionActivity.this,
                AdminQuestionListingActivity.class);
        startActivity(intent);
        finish();
    }

}
