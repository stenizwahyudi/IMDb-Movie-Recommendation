package app.model;

public class RecommendationCastInfo {
    protected String actorId;
    protected String category;
    protected String recommendationId;
    protected int ordering;

    public RecommendationCastInfo(String actorId, String category, String recommendationId, int ordering) {
        this.actorId = actorId;
        this.category = category;
        this.recommendationId = recommendationId;
        this.ordering = ordering;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }
}
