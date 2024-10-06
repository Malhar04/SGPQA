package com.sgp.qa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Question_id")
    private int id;

    @Column(name = "Likes")
    private int likes = 0 ;

    @Column(name = "Dislikes")
    private int dislikes = 0;

    @Column(name = "User_id")
    private String users;

    @Column(name = "Title")
    private String Title;

    @Column(name = "Main_content")
    private String Description;

    @Column(name = "Tags")
    private String Tags = "";

    public Question() {

    }

    public Question(int id, int likes, int dislikes, String users, String title, String description, String tags) {
        this.id = id;
        this.likes = likes;
        this.dislikes = dislikes;
        this.users = users;
        Title = title;
        Description = description;
        Tags = tags;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
