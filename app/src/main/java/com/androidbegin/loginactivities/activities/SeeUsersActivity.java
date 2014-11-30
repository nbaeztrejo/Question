package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

/**
 * Created by Kevin on 11/29/2014.
 */
public class SeeUsersActivity extends Activity {

    private ListView listView;
    private Group group;
    private String groupID;
    private List<String> usernames;
    private List<ParseUser> users;
    private List<String> userIDs;

    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from welcome.xml
        setContentView(R.layout.admin_view_groups_activity);

        //Get ListView object from xml
        listView = (ListView) findViewById(R.id.listview);

        // Retrieve current user from Parse.com
        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");
        usernames = new ArrayList<String>();

        new RemoteDataTask().execute();

    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SeeUsersActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("View Users");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<Group> query = Group.getQuery();
            query.whereEqualTo("objectID", groupID);
            try {
                group = query.get(groupID);
                users = group.getUsers();
                userIDs = group.getList("users");
                for (int i = 0; i < userIDs.size(); i++) {
                    ParseObject toBeAdded = query.get(userIDs.get(i));
                    usernames.add(toBeAdded.getString("username"));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(SeeUsersActivity.this,
                    R.layout.listview_item, userIDs);

            //Assign adapter to ListView
            listView.setAdapter(adapter);

            mProgressDialog.dismiss();

            // ListView Item Click Listener
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition     = position;

                    // ListView Clicked item value
                    String  itemValue    = (String) listView.getItemAtPosition(position);

                    //get stuff from previous page using Bundle
                    //Bundle b = new Bundle();
                    //b.putString("groupID", groupIDs.get(itemPosition));
                    //b.putBoolean("isAdmin", true);

                    Intent i = new Intent(SeeUsersActivity.this,
                            GroupViewActivity.class);
                    //i.putExtras(b);

                    // Open AdminQuestionListingActivity.java Activity
                    startActivity(i);
                    finish();
                }

            });*/
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SeeUsersActivity.this,
                GroupViewActivity.class);
        startActivity(intent);
        finish();
    }

}
