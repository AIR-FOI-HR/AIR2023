package com.example.database;

public class Review {
    private String reviewID;
    private String userID;
    private String rating;
    private String comment;

    public Review(String userID, String rating, String comment){
        this.userID = userID;
        this.rating = rating;
        this.comment = comment;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
