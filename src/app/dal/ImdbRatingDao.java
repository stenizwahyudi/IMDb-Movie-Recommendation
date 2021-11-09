package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.ImdbRating;

public class ImdbRatingDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static ImdbRatingDao instance = null;
	protected ImdbRatingDao() {
		super();
	}
	public static ImdbRatingDao getInstance() {
		if(instance == null) {
			instance = new ImdbRatingDao();
		}
		return instance;
	}
	
	public ImdbRating create(ImdbRating rating) throws SQLException {
		String insertRating = "INSERT INTO IMDBRating(RecommendationId,Rating)"
				+ "VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRating);
			insertStmt.setString(1, rating.getRecommendationId());
			insertStmt.setDouble(2, rating.getRating());
			
			insertStmt.executeUpdate();
			return rating;
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
		}
	}
	
	/**
	 * Delete the rating instance.
	 * This runs a DELETE statement.
	 */
	public ImdbRating delete(ImdbRating rating) throws SQLException {
		String deleteRating = "DELETE FROM ImdbRating WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRating);
			deleteStmt.setString(1, rating.getRecommendationId());
			deleteStmt.executeUpdate();

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
	
	public ImdbRating getRatingByRecommendationId(String recommendationId) throws SQLException {
		String selectRating = "SELECT RecommendationId, Rating FROM ImdbRating WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRating);
			selectStmt.setString(1, recommendationId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultRecommendationId = results.getString("RecommendationId");
				double rating = results.getDouble("Rating");
				
				ImdbRating imdbRating = new ImdbRating(resultRecommendationId, rating);
				return imdbRating;
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
	
	/*
	 * Update the Recommendation rating
	 */
	public ImdbRating updateRating(ImdbRating rating, double newRating) throws SQLException {
		String updateRating = "UPDATE ImdbRating SET Rating=? WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateRating);
			updateStmt.setDouble(1, newRating);
			updateStmt.setString(2, rating.getRecommendationId());
			updateStmt.executeUpdate();
			
			rating.setRating(newRating);
			return rating;
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
	
}
