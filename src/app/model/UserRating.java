package app.model;

import java.util.Date;

public class UserRating {
    protected int userRatingId;
    protected Users user;
    protected Date timeStamp;
    protected Recommendation recommendation;
    protected Double rating;

    public UserRating (int userRatingId, Users user, Date timeStamp, Recommendation recommendation, Double rating) {
        this.userRatingId = userRatingId;
        this.user = user;
        this.timeStamp = timeStamp;
        this.recommendation = recommendation;
        this.rating = rating;
    }

    public UserRating (Users user, Date timeStamp, Recommendation recommendation, Double rating) {
        this.user = user;
        this.timeStamp = timeStamp;
        this.recommendation = recommendation;
        this.rating = rating;
    }

    public UserRating (int userRatingId) {
        this.userRatingId = userRatingId;
    }

    public int getUserRatingId() { return userRatingId; }

    public void setUserRatingId(int userRatingId) { this.userRatingId = userRatingId; }

    public Users getUser() { return user; }

    public void setUser(Users user) { this.user = user; }

    public Date getTimeStamp() { return timeStamp; }

    public void setTimeStamp(Date timeStamp) { this.timeStamp = timeStamp; }

    public Recommendation getRecommendation() { return recommendation; }

    public void setRecommendation(Recommendation recommendation) { this.recommendation = recommendation; }

    public Double getRating() { return rating; }

    public void setRating(Double rating) { this.rating = rating; }
}
