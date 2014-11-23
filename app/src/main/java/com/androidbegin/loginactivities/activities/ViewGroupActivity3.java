package com.androidbegin.loginactivities.activities;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.ParseUser;


public class ViewGroupActivity3 extends Activity {
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from welcome.xml
        setContentView(R.layout.viewgroups);

        //Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Retrieve current user from Parse.com
        final ParseUser currentUser = ParseUser.getCurrentUser();

        //Need to figure out how to get the item being pointed at in the Array....
        /*ParseQuery<ParseObject> UserQuery = ParseQuery.getQuery("_User");

        List<ParseObject> currentGroups = currentUser.getList("groups");
        int length = currentGroups.size();
        String[] listGroups = new String[length];
        for (int i = 0; i<length; i++)
        {
            ParseObject temp = currentGroups.get(i).getParseObject("objectId");
            listGroups[i]=temp.getString("groupName");
        }*/

        // This works kinda by showing the string fetched from groups
        /*JSONArray currentGroups = currentUser.getJSONArray("groups");
        int length = currentGroups.length();
        String[] listGroups = new String[length];
        for (int i = 0; i < length; i++) {
            try {
                listGroups[i]= String.valueOf(currentGroups.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

        //Generic test to check if listView works
        String[] listGroups = new String[1];
        listGroups[0]="a";

        // Generic test to see how JSONArray works/String.valueof
        //listGroups[0]= String.valueOf(currentUser.getJSONArray("groups"));

        ArrayAdapter<String> adatper = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listGroups);

        //Assign adapter to ListView
        listView.setAdapter(adatper);
        // ListView Item Click Listener

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });

        /*
            Arbitrary buttons only for the sake of linking activities
          */

        Button adminGroup = (Button) findViewById(R.id.adminGroup);
        Button regularGroup = (Button) findViewById(R.id.regularGroup);


        // adminGroup Button Click Listener
        adminGroup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(ViewGroupActivity3.this,
                        AdminQuestionListingActivity.class);
                startActivity(intent);
                finish();
            }
        });



        // Listener
        regularGroup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(ViewGroupActivity3.this,
                        NormalQuestionListingActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewGroupActivity3.this,
                Welcome.class);
        startActivity(intent);
        finish();
    }

}
