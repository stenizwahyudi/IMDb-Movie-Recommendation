package app.model;

public class ImdbRating {
	
	protected String recommendationId;
	protected double rating;
	
	
	public ImdbRating(String recommendationId, double rating) {
		this.recommendationId = recommendationId;
		this.rating = rating;
	}
	public ImdbRating(String recommendationId) {
		this.recommendationId = recommendationId;
		
	}
	public String getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(String recommendationId) {
		this.recommendationId = recommendationId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
}
