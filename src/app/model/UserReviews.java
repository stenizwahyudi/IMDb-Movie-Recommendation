package app.model;

import java.util.Date;

public class UserReviews {

	/**
	 * ReviewId, Username,Summary,Date
	 */
	protected int reviewId;
	protected Users user;
	protected String summary;
	protected Date date;
	
	public UserReviews(int reviewId, Users user, String summary, Date date) {
		super();
		this.reviewId = reviewId;
		this.user = user;
		this.summary = summary;
		this.date = date;
	}
	public UserReviews(int reviewId) {
		super();
		this.reviewId = reviewId;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public Users getUser() {
		return user;
	}
	public void setUserName(Users user) {
		this.user = user;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
