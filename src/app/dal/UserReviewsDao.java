package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.model.UserReviews;
import app.model.Users;

public class UserReviewsDao {
	protected ConnectionManager connectionManager;
	
	private static UserReviewsDao instance = null;
	protected UserReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static UserReviewsDao getInstance() {
		if(instance == null) {
			instance = new UserReviewsDao();
		}
		return instance;
	}
	
	/**
	 * Create a UserReview
	 * @param userReview to create
	 * @return a new userReview in db
	 * @throws SQLException
	 */
	public UserReviews create(UserReviews userReview) throws SQLException {
		String insertUserReview =
			"INSERT INTO UserReviews(UserName,Summary,Date) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertUserReview,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, userReview.getUser().getUserName());
			insertStmt.setString(2, userReview.getSummary());
			insertStmt.setTimestamp(3, new Timestamp(userReview.getDate().getTime()));
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			userReview.setReviewId(reviewId);
			return userReview;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	/**
	 * Update the summary of the UserReviews instance.
	 * This runs a UPDATE statement.
	 */
	public UserReviews updateSummary(UserReviews userReview, String newSummary) throws SQLException {
		String updateUserReview = "UPDATE UserReviews SET Summary=?,Date=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUserReview);
			updateStmt.setString(1, newSummary);
			// Sets the Created timestamp to the current time.
			Date newCreatedTimestamp = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setInt(3, userReview.getReviewId());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			userReview.setSummary(newSummary);
			userReview.setDate(newCreatedTimestamp);
			return userReview;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
	
	/**
	 * Delete the UserReviews instance.
	 * This runs a DELETE statement.
	 */
	public UserReviews delete(UserReviews userReview) throws SQLException {
		String deleteUserReview = "DELETE FROM UserReviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUserReview);
			deleteStmt.setInt(1, userReview.getReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogPosts instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	/**
	 * Get the BlogPosts record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single BlogPosts instance.
	 * Note that we use BlogUsersDao to retrieve the referenced BlogUsers instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the BlogPosts, BlogUsers tables and then build each object.
	 */
	public UserReviews getUserReviewById(int reviewId) throws SQLException {
		String selectUserReview =
			"SELECT ReviewId,UserName,Summary,Date " +
			"FROM UserReviews " +
			"WHERE reviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUserReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			
			UsersDao userDao = UsersDao.getInstance();
			if(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				String summary = results.getString("Summary");
				Date created =  new Date(results.getTimestamp("Date").getTime());
				String userName = results.getString("UserName");
				
				Users user = userDao.getUserByUserName(userName);
				
				UserReviews userReview = new UserReviews(resultReviewId, user, summary, created);
				return userReview;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	/**
	 * Get the all the UserReviews for a user.
	 */
	public List<UserReviews> getUserReviewsForUser(String userName) throws SQLException {
		List<UserReviews> userReviews = new ArrayList<UserReviews>();
		String selectUserReviews =
			"SELECT ReviewId,UserName,Summary,Date " +
			"FROM UserReviews " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUserReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			Users user = usersDao.getUserByUserName(userName);
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				String summary = results.getString("Summary");
				Date created =  new Date(results.getTimestamp("Date").getTime());
				UserReviews userReview = new UserReviews(reviewId, user, summary, created);
				userReviews.add(userReview);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return userReviews;
	}
}
