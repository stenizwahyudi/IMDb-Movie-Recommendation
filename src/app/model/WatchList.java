package app.model;

public class WatchList {
    protected int watchListId;
    protected Users user;
    protected Recommendation recommendation;
    protected int hasWatched;

    public WatchList(int watchListId, Users user, Recommendation recommendation, int hasWatched) {
        this.watchListId = watchListId;
        this.user = user;
        this.recommendation = recommendation;
        this.hasWatched = hasWatched;
    }
    
    public WatchList(Users user, Recommendation recommendation, int hasWatched) {
       
        this.user = user;
        this.recommendation = recommendation;
        this.hasWatched = hasWatched;
    }

    public WatchList (Users user, Recommendation recommendation) {
        this.user = user;
        this.recommendation = recommendation;
    }

    public int getWatchListId() {
        return watchListId;
    }

    public void setWatchListId(int watchListId) {
        this.watchListId = watchListId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public int getHasWatched() {
        return hasWatched;
    }

    public void setHasWatched(int hasWatched) {
        this.hasWatched = hasWatched;
    }
}
