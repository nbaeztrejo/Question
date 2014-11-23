package com.androidbegin.loginactivities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ViewGroupActivity extends Activity {
    ListView listview;
    List<String> listOfUsersGroupsObjectIDs;
    List<Group> listOfGroups;
    ProgressDialog mProgressDialog;
    ArrayAdapter<String> adapter;
    JSONArray userList;
    public String[] ansText;
    public List<Integer> responseCollection;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.viewgroups);

        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
                /*
            Arbitrary buttons only for the sake of linking activities
          */

        /*Button adminGroup = (Button) findViewById(R.id.adminGroup);
        Button regularGroup = (Button) findViewById(R.id.regularGroup);


        // adminGroup Button Click Listener
        adminGroup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(ViewGroupActivity.this,
                        AdminQuestionListingActivity.class);
                startActivity(intent);
                finish();
            }
        });



        // Listener
        regularGroup.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(ViewGroupActivity.this,
                        NormalQuestionListingActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }


    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ViewGroupActivity.this);
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
            // get the list of
            listOfUsersGroupsObjectIDs = ParseUser.getCurrentUser().getList("groups");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(ViewGroupActivity.this,
                    R.layout.listview_item);
            // Retrieve object "questionText" from Parse.com database
            for (String group : listOfUsersGroupsObjectIDs) {
                /*ParseQuery<Group> query = ;
                query.whereEqualTo("objectId", group);
                try {
                    //fetchedGroup = query.getFirst();
                } catch (ParseException e) {
                    //fetchedGroup = null;
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                //adapter.add((String) group.get("groupName"));*/
            }
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Capture button clicks on ListView items

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // Send single item click data to SingleItemView Class

                    //userList = listOfUsersGroups.get((int) id).getJSONArray("groupUsers");
                    //String objectID = ob.get((int) id).getObjectId();
                    //responses = ob.get((int) id).getJSONArray("responseCollection");

                    //get list of user objectIDs
                    List<String> newlist = new ArrayList<String>();
                    for (int i = 0; i < userList.length(); i++) {
                        try {
                            newlist.add(userList.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ansText = newlist.toArray(new String[newlist.size()]);

                    //get stuff from previous page using Bundle
                    Bundle b = new Bundle();
                    b.putStringArray("ansArray", ansText);

                    Intent i = new Intent(ViewGroupActivity.this,
                            AdminQuestionListingActivity.class);
                    i.putExtras(b);

                    // Open SingleItemView.java Activity
                    startActivity(i);

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewGroupActivity.this,
                Welcome.class);
        startActivity(intent);
        finish();
    }

}
