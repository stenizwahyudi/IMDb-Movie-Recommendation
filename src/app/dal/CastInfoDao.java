package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.CastInfo;

public class CastInfoDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static CastInfoDao instance = null;
	protected CastInfoDao() {
		super();
	}
	public static CastInfoDao getInstance() {
		if(instance == null) {
			instance = new CastInfoDao();
		}
		return instance;
	}
	
	/**
	 * Create a CastInfo Instance
	 * @param castInfo
	 * @return
	 * @throws SQLException
	 */
	
	public CastInfo create(CastInfo castInfo) throws SQLException {
		String insertCastInfo = "INSERT INTO CastInfo(ActorId,Name) "
				+ "VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCastInfo);
			insertStmt.setString(1, castInfo.getActorId());
			insertStmt.setString(2, castInfo.getName());

			insertStmt.executeUpdate();
			return castInfo;
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
	
	public CastInfo getCastInfoByActorId(String actorId) throws SQLException {
		String selectCastInfo = "SELECT ActorId,Name FROM CastInfo WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCastInfo);
			selectStmt.setString(1, actorId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultActorId = results.getString("ActorId");
				String name = results.getString("Name");
				CastInfo castInfo = new CastInfo(resultActorId, name);
				return castInfo;
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
	
	
	public CastInfo getCastInfoByName(String name) throws SQLException {
		String selectCastInfo = "SELECT ActorId,Name FROM CastInfo WHERE Name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCastInfo);
			selectStmt.setString(1, name);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultName = results.getString("Name");
				String actorId = results.getString("ActorId");
				CastInfo castInfo = new CastInfo(actorId, resultName);
				return castInfo;
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
	 * Update the CastInfo name
	 */
	public CastInfo updateName(CastInfo castInfo, String newName) throws SQLException {
		String updateCastInfo = "UPDATE CastInfo SET Name=? WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCastInfo);
			updateStmt.setString(1, newName);
			updateStmt.setString(2, castInfo.getActorId());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			castInfo.setName(newName);
			return castInfo;
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
	 * Delete the CastInfo instance.
	 * This runs a DELETE statement.
	 */
	public CastInfo delete(CastInfo castInfo) throws SQLException {
		String deleteCastInfo = "DELETE FROM CastInfo WHERE ActorId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCastInfo);
			deleteStmt.setString(1, castInfo.getActorId());
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
