package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.RecommendationCastInfo;


public class RecommendationCastInfoDao {
    protected ConnectionManager connectionManager;

    private static RecommendationCastInfoDao instance = null;

    protected RecommendationCastInfoDao() {
        connectionManager = new ConnectionManager();
    }

    public static RecommendationCastInfoDao getInstance() {
        if (instance == null) {
            instance = new RecommendationCastInfoDao();
        }
        return instance;
    }
    
	public RecommendationCastInfo create(RecommendationCastInfo recommendationCastInfo) throws SQLException {
		String insertRecommendationCastInfo = "INSERT INTO RecommendationCastInfo(ActorId,Category,"
				+ "RecommendationId,Ordering) "
				+ "VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendationCastInfo);
			insertStmt.setString(1, recommendationCastInfo.getActorId());
			insertStmt.setString(2, recommendationCastInfo.getCategory());
			insertStmt.setString(3, recommendationCastInfo.getRecommendationId());
			insertStmt.setInt(4, recommendationCastInfo.getOrdering());

			insertStmt.executeUpdate();
			return recommendationCastInfo;
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
	
	public List<RecommendationCastInfo> getRecommendationCastInfoByActorId(RecommendationCastInfo recommendation, String actorId) throws SQLException {
		List<RecommendationCastInfo> recommendations = new ArrayList<RecommendationCastInfo>();
		String selectRecommendationCastInfo = 
				"SELECT ActorId,Cateogry,RecommendationId,Ordering "
				+ "FROM RecommendationCastInfo"
				+ "WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendationCastInfo);
			selectStmt.setString(1, recommendation.getRecommendationId());
			selectStmt.setString(2, actorId);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultRecommendationId = results.getString("RecommendationId");
				String resultActorId = results.getString("ActorId");
				String cateogry = results.getString("Cateogry");
				int ordering = results.getInt("Ordering");
				 
				RecommendationCastInfo recommendationCastInfo = new RecommendationCastInfo(resultActorId, cateogry,
						resultRecommendationId, ordering);
				recommendations.add(recommendationCastInfo);
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
		return recommendations;
	}
	
	public List<RecommendationCastInfo> getRecommendationCastInfoByCategory(RecommendationCastInfo recommendation, String cateogry) throws SQLException {
		List<RecommendationCastInfo> recommendations = new ArrayList<RecommendationCastInfo>();
		String selectRecommendationCastInfo = 
				"SELECT ActorId,Cateogry,RecommendationId,Ordering "
				+ "FROM RecommendationCastInfo "
				+ "WHERE RecommendationId=? AND Category=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendationCastInfo);
			selectStmt.setString(1, recommendation.getRecommendationId());
			selectStmt.setString(2, cateogry);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultRecommendationId = results.getString("RecommendationId");
				String actorId = results.getString("ActorId");
				String resultCateogry = results.getString("Cateogry");
				int ordering = results.getInt("Ordering");
				 
				RecommendationCastInfo recommendationCastInfo = new RecommendationCastInfo(actorId, resultCateogry,
						resultRecommendationId, ordering);
				recommendations.add(recommendationCastInfo);
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
		return recommendations;
	}
	
	public List<RecommendationCastInfo> getRecommendationCastInfoByRecommendationId(String recommendationId) throws SQLException {
		List<RecommendationCastInfo> recommendations = new ArrayList<RecommendationCastInfo>(); 
		String selectRecommendationCastInfo = 
				"SELECT ActorId,Cateogry,RecommendationId,Ordering "
				+ "FROM RecommendationCastInfo "
				+ "WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendationCastInfo);
			selectStmt.setString(1, recommendationId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String actorId = results.getString("ActorId");
				String cateogry = results.getString("Cateogry");
				String resultRecommendationId = results.getString("RecommendationId");
				int ordering = results.getInt("Ordering");
				 
				RecommendationCastInfo recommendationCastInfo = new RecommendationCastInfo(actorId, cateogry,
						resultRecommendationId, ordering);
				recommendations.add(recommendationCastInfo);
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
		return recommendations;
	}
	
	
	/*
	 * Update the RecommendationCastInfo order
	 */
	public RecommendationCastInfo updateRecommendationCastInfo(RecommendationCastInfo recommendationCastInfo, int newOrdering) throws SQLException {
		String updateCastInfo = "UPDATE RecommendationCastInfo SET Ordering=? WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCastInfo);
			updateStmt.setInt(1, newOrdering);
			updateStmt.setString(2, recommendationCastInfo.getRecommendationId());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			recommendationCastInfo.setOrdering(newOrdering);
			return recommendationCastInfo;
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
	 * Delete the RecommendationCastInfo instance.
	 * This runs a DELETE statement.
	 */
	public RecommendationCastInfo delete(RecommendationCastInfo recommendationCastInfo) throws SQLException {
		String deleteCastInfo = "DELETE FROM RecommendationCastInfo WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCastInfo);
			deleteStmt.setString(1, recommendationCastInfo.getRecommendationId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
}
