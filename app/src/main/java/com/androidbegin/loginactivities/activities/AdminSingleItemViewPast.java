package com.androidbegin.loginactivities.activities;

/**
 * Created by Brenda Garcia on 10/28/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdminSingleItemViewPast extends Activity {

    private ArrayList<String> displayArray;
    private String groupID;
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

        for (int i=0;i<responseCollect.size();i++) {
            sum+=responseCollect.get(i);
        }

        displayArray = new ArrayList<String>();
        displayArray.add("Number of responses: " + (int) sum);

        if (sum == 0) {
            for (int i=0;i<ansArray.length;i++) {
                displayArray.add(0 + "%: " + ansArray[i]);
            }
        } else {
            for (int i=0;i<ansArray.length;i++) {
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

        Intent intent = new Intent(AdminSingleItemViewPast.this,
                AdminPastQuestionActivity.class);

        intent.putExtras(b);

        startActivity(intent);
        finish();
    }
}