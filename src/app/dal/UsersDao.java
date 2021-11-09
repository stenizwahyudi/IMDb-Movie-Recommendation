package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.Users;

public class UsersDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static UsersDao instance = null;
	protected UsersDao() {
		 connectionManager = new ConnectionManager();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}
	
	public Users create(Users users) throws SQLException {
		String insertUser = "INSERT INTO Users(UserName,Password,Email) "
				+ "VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, users.getUserName());
			insertStmt.setString(2, users.getPassword());
			insertStmt.setString(3, users.getEmail());

			insertStmt.executeUpdate();
			return users;
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
	 * Delete the Users instance.
	 * This runs a DELETE statement.
	 */
	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
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
	
	public Users getUserByUserName(String userName) throws SQLException {
		String selectUser = "SELECT UserName,Password,Email FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String email = results.getString("Email");
				Users user = new Users(resultUserName, password, email);
				return user;
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
	 * Update the User's password
	 */
	public Users updatePassword(Users user, String newPassword) throws SQLException {
		String updateUser = "UPDATE Users SET Password=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newPassword);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();
		
			user.setPassword(newPassword);
			return user;
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

	public Users checkLogin(String email, String password) throws SQLException,
			ClassNotFoundException {
		Connection connection = connectionManager.getConnection();
		String sql = "SELECT * FROM Users WHERE Email = ? and Password = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, password);

		ResultSet result = statement.executeQuery();

		Users users = null;

		if (result.next()) {
			String UserName = result.getString("username");
			String resEmail = result.getString("email");
			String resPassword = result.getString("password");
			users = new Users(UserName, resPassword, resEmail);
		}

		connection.close();

		return users;
	}
	
}
