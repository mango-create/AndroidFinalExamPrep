package com.example.finalprep;

import java.util.ArrayList;

public class JSONClass {
    String status;
    ArrayList<Post> posts;

    @Override
    public String toString() {
        return "JSONClass{" +
                "status='" + status + '\'' +
                ", postList=" + posts +
                '}';
    }

    public JSONClass(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Post> getPostList() {
        return posts;
    }

    public void setPostList(ArrayList<Post> postList) {
        this.posts = postList;
    }
}

//"status":"ok","posts":[{"created_by_name":"cat","post_id":"80","created_by_uid":"22","post_text":"hi","created_at":"2022-04-29 13:39:35"}
class Post{
    String created_by_name, post_id, created_by_uid, post_text, created_at;

    @Override
    public String toString() {
        return "Post{" +
                "created_by_name='" + created_by_name + '\'' +
                ", post_id='" + post_id + '\'' +
                ", created_by_uid='" + created_by_uid + '\'' +
                ", post_text='" + post_text + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public Post(String created_by_name, String post_id, String created_by_uid, String post_text, String created_at) {
        this.created_by_name = created_by_name;
        this.post_id = post_id;
        this.created_by_uid = created_by_uid;
        this.post_text = post_text;
        this.created_at = created_at;
    }

    public String getCreated_by_name() {
        return created_by_name;
    }

    public void setCreated_by_name(String created_by_name) {
        this.created_by_name = created_by_name;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCreated_by_uid() {
        return created_by_uid;
    }

    public void setCreated_by_uid(String created_by_uid) {
        this.created_by_uid = created_by_uid;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
