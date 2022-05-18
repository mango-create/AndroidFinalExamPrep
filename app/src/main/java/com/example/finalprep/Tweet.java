package com.example.finalprep;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Tweet {
    String body, userName, userId, docId;
    @ServerTimestamp
    Date date;


    public Tweet(){}
    public Tweet(String body, String userName, String userId, Date date, String docId) {
        this.body = body;
        this.userName = userName;
        this.userId = userId;
        this.date = date;
        this.docId = docId;
    }

    public Tweet(String body, String userName, String userId, Date date) {
        this.body = body;
        this.userName = userName;
        this.userId = userId;
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getDocId() { return docId;}

    public void setDocId(String docId){ this.docId = docId;}
}
