package com.androidbegin.loginactivities.activities;

/**
 * Created by Sara Shi on 11/22/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NormalSingleItemViewPast extends Activity {

    private ArrayList<String> displayArray;
    private String groupID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_singleitemview_past);

        Bundle b = this.getIntent().getExtras();
        String[] ansArray = b.getStringArray("ansArray");
        ArrayList<Integer> responseCollect = b.getIntegerArrayList("responseCollect");
        groupID = b.getString("groupID");

        displayArray = new ArrayList<String>();

        for (int i=0;i<ansArray.length;i++) {
            displayArray.add(responseCollect.get(i)+": "+ansArray[i]);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listview_item, displayArray);

        ListView listView = (ListView) findViewById(R.id.ans_list);
        listView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {

        Bundle b=new Bundle();
        b.putString("groupID", groupID);

        Intent intent = new Intent(NormalSingleItemViewPast.this,
                NormalQuestionListingActivity.class);

        intent.putExtras(b);

        startActivity(intent);
        finish();
    }
}