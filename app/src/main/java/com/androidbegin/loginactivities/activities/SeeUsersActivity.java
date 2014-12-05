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
    private ParseObject group;

    //to save for bundle
    private String groupID;
    private boolean isAdmin;
    private String groupName;

    private List<String> usernames;
    private List<String> userIDs;

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from welcome.xml
        setContentView(R.layout.admin_view_groups_activity);

        //Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Retrieve current user from Parse.com
        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");
        isAdmin = b.getBoolean("isAdmin");
        groupName = b.getString("groupName");
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
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Group");
            try {
                //userIDs = query.get(groupID).getList("users");
                group = query.get(groupID);
                userIDs = group.getList("users");

                ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                for(String userID : userIDs) {
                    ParseUser toBeAdded = userQuery.get(userID);
                    String username = toBeAdded.getString("username");
                    usernames.add(username);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                usernames.add("failure");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            mProgressDialog.dismiss();

            if (usernames.size() == 0) {
                usernames.add("Sorry, no users are in this group");
            }
            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(SeeUsersActivity.this,
                    R.layout.listview_item, usernames);

            //Assign adapter to ListView
            listView.setAdapter(adapter);

        }
    }

    @Override
    public void onBackPressed() {
        Bundle b = new Bundle();
        b.putString("groupID", groupID);
        b.putBoolean("isAdmin", isAdmin);
        b.putString("groupName", groupName);

        Intent intent = new Intent(SeeUsersActivity.this,
                GroupViewActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

}
