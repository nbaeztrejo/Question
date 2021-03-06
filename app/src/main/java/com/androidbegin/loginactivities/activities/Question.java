package com.androidbegin.loginactivities.activities;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sara on 10/25/14.
 */
@ParseClassName("Question")
public class Question extends ParseObject {

    public void initialize(String questionText, String GroupID, String currentUserID){
        put("questionText", questionText);
        put("group", GroupID);
        ArrayList<String> responseTextList = new ArrayList<String>();
        put("responseTextList",responseTextList);
        put("isOpen", true);
        put("asker", currentUserID);

        ArrayList<Integer> responseCollection = new ArrayList();
        for(int i = 0; i < 4; i++){
            responseCollection.add(0);
        }
        put("responseCollection", responseCollection);

        // Array containing users that have responded to the question
        // Also contains the question ASKER (2nd line, do not comment out)
        List<String> userResponses = new ArrayList();
        userResponses.add(currentUserID);
        put("userResponses", userResponses);

        // Array containing the response each user has selected
        // Also contains '-1' to represent the question ASKER (2nd line, do not comment out)
        ArrayList<Integer> userChoices = new ArrayList();
        userChoices.add(-1);
        put("userChoices", userChoices);

        saveInBackground();
    }

    public void addUserChoice (int i) {
        List<Integer> temp = new ArrayList();
        temp = getList("userChoices");
        temp.add(i);
        put("userChoices", temp);
    }

    public void setUserChoice (int i, int j) {
        List<Integer> temp = new ArrayList();
        temp = getList("userChoices");
        temp.set(i, j);
        put("userChoices", temp);
    }

    public void incrementResponse (int i) {
        List<Integer> temp = new ArrayList();
        temp = getList("responseCollection");
        temp.set(i, temp.get(i) + 1);
        put("responseCollection", temp);
    }

    public void decrementResponse (int i) {
        List<Integer> temp = new ArrayList();
        temp = getList("responseCollection");
        temp.set(i, temp.get(i) - 1);
        put("responseCollection", temp);
    }

    public void incrementUser (String id) {
        List<String> temp = new ArrayList();
        temp = getList("userResponses");
        temp.add(id);
        put("userResponses", temp);
    }

    public List getUserResponses() {
        return getList("userResponses");
    }

    public List getResponseCollection(){
        return getList("responseCollection");
    }

    public void addResponse(String responseText) {
        add("responseTextList",responseText);
    }

    public String getQuestionText(){
        return getString("questionText");
    }

    public Boolean getQuestionState() {
        return getBoolean("isOpen");
    }

    public List getResponseTextList() {
        return getList("responseTextList");
    }

    public void closeQuestion(){
        put("isOpen",false);
    }

    public static ParseQuery<Question> getQuery() {
        return ParseQuery.getQuery(Question.class);
    }

}
