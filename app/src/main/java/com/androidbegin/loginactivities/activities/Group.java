package com.androidbegin.loginactivities.activities;

import com.androidbegin.loginactivities.activities.Question;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sara on 10/17/2014.
 */
@ParseClassName("Group")
public class Group extends ParseObject{

    //private List<Question> myQuestions = new ArrayList<Question>();

    public void initialize(String name){
        put("admin", ParseUser.getCurrentUser().getObjectId());
        put("name", name);
        ArrayList<ParseUser> users = new ArrayList<ParseUser>();
        put("users", users);
        addUser(ParseUser.getCurrentUser().getObjectId());
        ArrayList<Question> questions = new ArrayList<Question>();
        put("questions", questions);

        saveInBackground();
    }

    public void addUser(String user){
        add("users", user);
    }

    public void addQuestion(Question question){
        add("questions", question);
    }

    public ParseUser getAdmin(){
        return getParseUser("admin");
    }

    public List<ParseUser> getUsers(){
        return getList("users");
    }

    public String getName(){
        return getString("name");
    }

    public String getCurrentGroupID(){
        return getObjectId();
    }

    public static ParseQuery<Group> getQuery() {
        return ParseQuery.getQuery(Group.class);
    }

    /*public List<Question> getQuestions(){
        return getList("questions");
    }*/

}
