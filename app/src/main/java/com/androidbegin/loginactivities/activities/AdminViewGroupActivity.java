package com.androidbegin.loginactivities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class AdminViewGroupActivity extends Activity {
    ListView listView;
    List<String> groupIDs;
    List<ParseObject> groupObjects;
    List<String> groupNames;

    ParseUser currentUser;

    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from welcome.xml
        setContentView(R.layout.admin_view_groups_activity);

        //Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Retrieve current user from Parse.com
        currentUser = ParseUser.getCurrentUser();
        groupIDs = currentUser.getList("groups");
        groupObjects = new ArrayList<ParseObject>();
        groupNames = new ArrayList<String>();

        new RemoteDataTask().execute();

    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AdminViewGroupActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("View Groups");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (String objectID : groupIDs) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Group");
                try {
                    ParseObject toBeAdded = query.get(objectID);
                    String toBeAddedAdmin = toBeAdded.getString("admin");
                    String currentUserID = currentUser.getObjectId();
                    if (toBeAddedAdmin.equals(currentUserID)) {
                        groupObjects.add(toBeAdded);
                        groupNames.add(toBeAdded.getString("name"));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            mProgressDialog.dismiss();

            ArrayAdapter<String> adatper;
            adatper = new ArrayAdapter<String>(AdminViewGroupActivity.this,
                    R.layout.listview_item,groupNames);
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

                    //get stuff from previous page using Bundle
                    Bundle b = new Bundle();

                    b.putString("groupID", groupIDs.get(itemPosition));

                    Intent i = new Intent(AdminViewGroupActivity.this,
                            AdminQuestionListingActivity.class);
                    i.putExtras(b);

                    // Open AdminQuestionListingActivity.java Activity
                    startActivity(i);
                    finish();
                }

            });
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminViewGroupActivity.this,
                GroupDirectActivity.class);
        startActivity(intent);
        finish();
    }

}
