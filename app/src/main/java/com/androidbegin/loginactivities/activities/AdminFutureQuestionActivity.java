package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AdminFutureQuestionActivity extends Activity {
    private String groupID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");
        setContentView(R.layout.admin_future_question_activity);
    }

    @Override
    public void onBackPressed() {
        Bundle b = new Bundle();
        b.putString("groupID", groupID);

        Intent intent = new Intent(AdminFutureQuestionActivity.this,
                AdminQuestionListingActivity.class);

        intent.putExtras(b);

        startActivity(intent);
        finish();
    }

}
