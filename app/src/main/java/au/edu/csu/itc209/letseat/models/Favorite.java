package au.edu.csu.itc209.letseat.models;

import java.sql.Timestamp;

/**
 * Created by NhutHuynh on 9/4/17.
 */

public class Favorite {
    private int ID;
    private User user;
    private Review review;
    private Timestamp dateFavorited;

    public Favorite(User user, Review review, Timestamp dateFavorited) {
        this.user = user;
        this.review = review;
        this.dateFavorited = dateFavorited;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public Timestamp getDateFavorited() {
        return dateFavorited;
    }

    public void setDateFavorited(Timestamp dateFavorited) {
        this.dateFavorited = dateFavorited;
    }
}
