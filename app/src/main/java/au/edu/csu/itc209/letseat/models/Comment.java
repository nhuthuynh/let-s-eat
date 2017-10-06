package au.edu.csu.itc209.letseat.models;

import java.sql.Timestamp;


public class Comment {
    private int ID;
    private String content;
    private User user;
    private Review review;
    private Timestamp dateCreated;

    public Comment(String content, User user, Review review, Timestamp date) {
        this.content = content;
        this.user = user;
        this.review = review;
        this.dateCreated = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp date) {
        this.dateCreated = date;
    }
}
