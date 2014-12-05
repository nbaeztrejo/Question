package com.androidbegin.loginactivities.activities;

/**
 * Created by Sara Shi on 11/22/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NormalSingleItemViewPast extends Activity {

    private ArrayList<String> displayArray;
    private String groupID;
    private String groupName;
    private double sum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sum = 0;
        setContentView(R.layout.admin_singleitemview_past);

        Bundle b = this.getIntent().getExtras();
        String[] ansArray = b.getStringArray("ansArray");
        ArrayList<Integer> responseCollect = b.getIntegerArrayList("responseCollect");
        groupID = b.getString("groupID");
        groupName = b.getString("groupName");

        for (int i = 0; i < responseCollect.size(); i++) {
            sum += responseCollect.get(i);
        }

        displayArray = new ArrayList<String>();
        displayArray.add("Number of responses: " + (int) sum);

        if (sum == 0) {
            for (int i = 0; i < ansArray.length; i++) {
                displayArray.add(0 + "%: " + ansArray[i]);
            }
        } else {
            for (int i = 0; i < ansArray.length; i++) {
                DecimalFormat df = new DecimalFormat("##.#");
                double temp = responseCollect.get(i) / sum * 100;
                displayArray.add(df.format(temp) + "%: " + ansArray[i]);
            }
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
        b.putString("groupName", groupName);

        Intent intent = new Intent(NormalSingleItemViewPast.this,
                NormalPastQuestionActivity.class);

        intent.putExtras(b);

        startActivity(intent);
        finish();
    }
}