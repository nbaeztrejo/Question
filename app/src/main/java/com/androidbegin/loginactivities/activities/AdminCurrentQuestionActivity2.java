package com.androidbegin.loginactivities.activities;

/**
 * Created by Brenda Garcia on 10/28/2014.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

public class AdminCurrentQuestionActivity2 extends Activity {
    // Declare Variables
    private ListView listview;
    private ParseObject group;
    private List<String> groupQuestionIDs;
    private List<ParseObject> groupQuestionObjects;
    private ArrayList<String> groupQuestionStrings;
    private ProgressDialog mProgressDialog;
    private ParseUser user;
    private JSONArray responseList;
    private JSONArray responses;
    private JSONArray userList;
    private String[] ansText;
    private String[] userText;
    private String groupID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from generic_listview.xmlestion_activity.xml
        setContentView(R.layout.generic_listview);

        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");


        // Locate the listview in generic_listview.xmlestion_activity.xml
        listview = (ListView) findViewById(R.id.listview);

        groupQuestionObjects = new ArrayList<ParseObject>();
        groupQuestionStrings = new ArrayList<String>();

        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(AdminCurrentQuestionActivity2.this);
            // Set progressdialog title
            mProgressDialog.setTitle("View Current Questions");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                    "Question");
            query.whereEqualTo("group", groupID);
            //query.whereEqualTo("isOpen", true);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> questionObjects, ParseException e) {
                    if (e == null) {
                        groupQuestionObjects = questionObjects;
                        for (ParseObject question : questionObjects) {
                            groupQuestionStrings.add(question.getString("questionText"));
                            groupQuestionStrings.add("Question Objects Size: " + questionObjects.size());
                        }
                    } else {
                        groupQuestionStrings.add("failed");
                    }
                }
            });

            //groupQuestionStrings.add("done");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            mProgressDialog.dismiss();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminCurrentQuestionActivity2.this,
                    R.layout.listview_item, groupQuestionStrings);

            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            // Capture button clicks on ListView items

            listview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // Send single item click data to AdminSingleItemView Class

                    user = ParseUser.getCurrentUser();
                    //String askerID = ob.get((int) id).getString("asker");
                    String userID = user.getObjectId();

                    String questionID = groupQuestionObjects.get((int) id).getObjectId();
                    responseList = groupQuestionObjects.get((int) id).getJSONArray("responseTextList");
                    responses = groupQuestionObjects.get((int) id).getJSONArray("responseCollection");

                    userList = groupQuestionObjects.get((int) id).getJSONArray("userResponses");


                    List<String> answerList = new ArrayList<String>();
                    for (int i=0; i<responseList.length(); i++) {

                        try {
                            answerList.add( responseList.getString(i) );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ansText = answerList.toArray(new String[answerList.size()]);

                    ArrayList<String> userCollect = new ArrayList<String>();
                    for (int i=0; i<userList.length(); i++) {

                        try {
                            userCollect.add(userList.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //userText = userCollect.toArray(new String[userCollect.size()]);

                    ArrayList<Integer> responseCollect = new ArrayList<Integer>();
                    for (int i=0; i < responses.length(); i++){
                        try {
                            responseCollect.add(responses.getInt(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Bundle b=new Bundle();
                    //b.putString("askerID", askerID);
                    b.putString("groupID", groupID);
                    b.putString("questionID", questionID);
                    b.putString("userID", userID);
                    b.putIntegerArrayList("responseCollect", responseCollect);
                    b.putStringArrayList("userCollect", userCollect);
                    b.putStringArray("ansArray", ansText);

                    Intent intent=new Intent(AdminCurrentQuestionActivity2.this,
                            //RadioButtonTester.class);
                            AdminSingleItemView.class);
                    intent.putExtras(b);
                    // Open AdminSingleItemView.java Activity
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Bundle b=new Bundle();
        b.putString("groupID", groupID);
        Intent intent = new Intent(AdminCurrentQuestionActivity2.this,
                AdminQuestionListingActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }


}