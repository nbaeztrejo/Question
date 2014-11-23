package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


public class ResponseViewActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.responseview);

        ListView responses = (ListView) findViewById(R.id.responseList);

    }

}
