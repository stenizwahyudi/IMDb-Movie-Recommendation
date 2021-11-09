package app.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import app.model.Recommendation;
import app.model.UserRating;
import app.model.Users;

public class UserRatingDao {
    protected ConnectionManager connectionManager;
    private static UserRatingDao instance = null;
    protected  UserRatingDao() { connectionManager = new ConnectionManager(); }

    public static  UserRatingDao getInstance() {
        if (instance == null) {
            instance = new UserRatingDao();
        }
        return instance;
    }

    public UserRating create (UserRating userRating) throws SQLException {
        String insertUserRating =
                "INSERT INTO UserRating(UserRatingId, Username, RecommendationId, Rating) " +
                "VALUE(?,?,?,?) ";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertUserRating,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, userRating.getUser().getUserName());
            insertStmt.setTimestamp(2, new Timestamp(userRating.getTimeStamp().getTime()));
            insertStmt.setString(3, userRating.getRecommendation().getRecommendationId());
            insertStmt.setDouble(4, userRating.getRating());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            Double userRatingId = -1.0;
            if(resultKey.next()) {
                userRatingId = resultKey.getDouble(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            userRating.setRating(userRatingId);
            return userRating;
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

    public UserRating delete (UserRating userRating) throws  SQLException {
            String deleteUserRating =
                    "DELETE FROM UserRating" +
                    "WHERE UserRatingId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUserRating);
            deleteStmt.setInt(1, userRating.getUserRatingId());
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

    public UserRating getUserRatingById (int userRatingId) throws SQLException {
        String selectUserRating =
                "SELECT UserRatingId, Username, Date, RecommendationId, Rating" +
                "FROM UserRating" +
                "UserRatingId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUserRating);
            selectStmt.setInt(1, userRatingId);
            results = selectStmt.executeQuery();

            UsersDao userDao = UsersDao.getInstance();
            RecommendationDao recommendationDao = RecommendationDao.getInstance();
            if (results.next()) {
                int resultUserRatingId = results.getInt("UserRatingId");
                String username = results.getString("Username");
                Date date = new Date(results.getTimestamp("Date").getTime());
                String recommendationId = results.getString("RecommendationId");
                Double rating = results.getDouble("Rating");

                Users user = userDao.getUserByUserName(username);
                Recommendation recommendation = recommendationDao.getRecommendationByRecommendationId(recommendationId);
                UserRating userRating = new UserRating(resultUserRatingId, user, date, recommendation, rating);
                return userRating;
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

    public UserRating updateUserRating (UserRating userRating, Double newRating) throws SQLException {
        String updateUserRating =
                "UPDATE UserRating " +
                "SET Rating=? " +
                "WHERE UserRatingId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateUserRating);
            updateStmt.setDouble(1, newRating);
            updateStmt.setInt(2, userRating.getUserRatingId());
            updateStmt.executeUpdate();

            userRating.setRating(newRating);
            return userRating;
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
