package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.model.Recommendation;
import app.model.Users;
import app.model.WatchList;


public class WatchListDao {
    protected ConnectionManager connectionManager;
    private static WatchListDao instance = null;
    protected  WatchListDao() { connectionManager = new ConnectionManager(); }

    public static  WatchListDao getInstance() {
        if (instance == null) {
            instance = new WatchListDao();
        }
        return instance;
    }

    public WatchList create (WatchList watchList) throws  SQLException {
        String insertWatchList =
                "INSERT INTO WatchList(Username, RecommendationId, hasWatched) " +
                "VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertWatchList,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, watchList.getUser().getUserName());
            insertStmt.setString(2, watchList.getRecommendation().getRecommendationId());
            if(watchList.getHasWatched()==0)
            	insertStmt.setBoolean(3, false);
            else
            	insertStmt.setBoolean(3, true);
            insertStmt.executeUpdate();
            resultKey = insertStmt.getGeneratedKeys();
            int watchListId = -1;
            if(resultKey.next()) {
                watchListId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            watchList.setWatchListId(watchListId);
            return watchList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (resultKey != null) {
                resultKey.close();
            }
        }
    }

    public WatchList delete(WatchList watchList) throws SQLException {
        String deleteWatchList =
                "DELETE FROM WatchList " +
                "WHERE WatchListId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteWatchList);
            deleteStmt.setInt(1, watchList.getWatchListId());
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

    public WatchList getWatchListById(int watchListId) throws SQLException {
        String selectWatchList =
                "SELECT WatchListId, Username, RecommendationId, hasWatched " +
                "FROM WatchList " +
                "WHERE WatchListId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWatchList);
            selectStmt.setInt(1, watchListId);
            results = selectStmt.executeQuery();

            UsersDao userDao = UsersDao.getInstance();
            RecommendationDao recommendationDao = RecommendationDao.getInstance();
            if(results.next()) {
                int resultWatchListId = results.getInt("WatchListId");
                String username = results.getString("Username");
                String recommendationId = results.getString("RecommendationId");
                int hasWatched = results.getInt("hasWatched");

                Users user = userDao.getUserByUserName(username);
                Recommendation recommendation = recommendationDao.getRecommendationByRecommendationId(recommendationId);
                WatchList watchList = new WatchList(resultWatchListId, user, recommendation, hasWatched);
                return  watchList;
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

    public WatchList getWatchListByRecommendationId(String  recommendationId) throws SQLException {
        String selectWatchList =
                "SELECT WatchListId, Username, RecommendationId, hasWatched " +
                "FROM WatchList " +
                "WHERE RecommendationId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWatchList);
            selectStmt.setString(1, recommendationId);
            results = selectStmt.executeQuery();

            UsersDao userDao = UsersDao.getInstance();
            RecommendationDao recommendationDao = RecommendationDao.getInstance();
            if(results.next()) {
                int watchListId = results.getInt("WatchListId");
                String username = results.getString("Username");
                String resultRecommendationId = results.getString("RecommendationId");
                int hasWatched = results.getInt("hasWatched");

                Users user = userDao.getUserByUserName(username);
                Recommendation recommendation = recommendationDao.getRecommendationByRecommendationId(resultRecommendationId);
                WatchList watchList = new WatchList(watchListId, user, recommendation, hasWatched);
                return  watchList;
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
    
    public List<WatchList> getWatchListByUserName(String userName) throws SQLException {
    	
    	List<WatchList> userWatchList = new ArrayList<WatchList>();
        String selectWatchList =
                "SELECT WatchListId, Username, RecommendationId, hasWatched " +
                "FROM WatchList " +
                "WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWatchList);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();

            UsersDao userDao = UsersDao.getInstance();
            RecommendationDao recommendationDao = RecommendationDao.getInstance();
            while(results.next()) {
                int resultWatchListId = results.getInt("WatchListId");
                String username = results.getString("Username");
                String recommendationId = results.getString("RecommendationId");
                int hasWatched = results.getInt("hasWatched");

                Users user = userDao.getUserByUserName(username);
                Recommendation recommendation = recommendationDao.getRecommendationByRecommendationId(recommendationId);
                WatchList watchList = new WatchList(resultWatchListId, user, recommendation, hasWatched);
                userWatchList.add(watchList);
                
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
        return userWatchList;
    }
    
    
    public WatchList updateWatchList (WatchList watchList, int newHasWatched) throws SQLException {
        String updateWatchList =
                "UPDATE WatchList " +
                "SET hasWatched=? " +
                "WHERE WatchListId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateWatchList);
            updateStmt.setInt(1, newHasWatched);
            updateStmt.setInt(2, watchList.getWatchListId());
            updateStmt.executeUpdate();

            watchList.setHasWatched(newHasWatched);
            return watchList;
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
