package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.model.Platforms;
import app.model.UserSubscriptions;
import app.model.Users;



public class UserSubscriptionsDao {
    protected ConnectionManager connectionManager;

    private static UserSubscriptionsDao instance = null;

    protected UserSubscriptionsDao() {
        connectionManager = new ConnectionManager();
    }

    public static UserSubscriptionsDao getInstance() {
        if (instance == null) {
            instance = new UserSubscriptionsDao();
        }
        return instance;
    }

    public UserSubscriptions create(UserSubscriptions userSubscription) throws SQLException {
        String insertUserSubscription =
                "INSERT INTO UserSubscriptions(UserName,PlatformId) " +
                        "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            // UserSubscriptions has an auto-generated key. So we want to retrieve that key.
            insertStmt = connection.prepareStatement(insertUserSubscription,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, userSubscription.getUsers().getUserName());
            insertStmt.setInt(2, userSubscription.getPlatform().getPlatformId());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            // For more details, see:
            // http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
            
            resultKey = insertStmt.getGeneratedKeys();
            int subscriptionId = -1;
            if (resultKey.next()) {
                subscriptionId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            userSubscription.setSubscriptionId(subscriptionId);
            return userSubscription;
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

    /**
     * Delete the UserSubscriptions instance.
     * This runs a DELETE statement.
     */
    public UserSubscriptions delete(UserSubscriptions userSubscription) throws SQLException {
        String deleteUserSubscription = "DELETE FROM UserSubscriptions WHERE SubscriptionId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUserSubscription);
            deleteStmt.setInt(1, userSubscription.getSubscriptionId());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the UserSubscriptions instance.
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }

    /**
     * Get the UserSubscriptions record by fetching it from your MySQL instance.
     * This runs a SELECT statement and returns a single UserSubscriptions instance.
     * Note that we use BlogUsersDao to retrieve the referenced BlogUsers instance.
     * One alternative (possibly more efficient) is using a single SELECT statement
     * to join the UserSubscriptions, BlogUsers tables and then build each object.
     */
    public UserSubscriptions getUserSubscriptionsById(int subscriptionId) throws SQLException {
        String selectUserSubscription =
                "SELECT SubscriptionId,UserName,PlatformId " +
                        "FROM UserSubscriptions " +
                        "WHERE SubscriptionId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUserSubscription);
            selectStmt.setInt(1, subscriptionId);
            results = selectStmt.executeQuery();
            UsersDao usersDao  = UsersDao.getInstance();
            PlatformsDao platformsDao = PlatformsDao.getInstance();
            if (results.next()) {
                int resultSubscriptionId = results.getInt("SubscriptionId");
                String userName = results.getString("UserName");
                int platformId = results.getInt("PlatformId");

                Platforms platform = platformsDao.getPlatformById(platformId);
                Users user = usersDao.getUserByUserName(userName);
                UserSubscriptions userSubscription = new UserSubscriptions(resultSubscriptionId, user, platform);
                return userSubscription;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }

    /**
     * Get the UserSubscriptions record by fetching it from your MySQL instance.
     * This runs a SELECT statement and returns a single UserSubscriptions instance.
     * Note that we use BlogPostsDao and BlogUsersDao to retrieve the referenced
     * BlogPosts and BlogUsers instances.
     * One alternative (possibly more efficient) is using a single SELECT statement
     * to join the UserSubscriptions, BlogPosts, BlogUsers tables and then build each object.
     */
    public List<UserSubscriptions> getUserSubscriptionByUserName(String userName) throws SQLException {
    	List<UserSubscriptions> userSubscriptions = new ArrayList<UserSubscriptions>();
        String selectUserSubscription =
                "SELECT SubscriptionId,UserName,PlatformId " +
                        "FROM UserSubscriptions " +
                        "WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUserSubscription);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            PlatformsDao platformsDao = PlatformsDao.getInstance();
            Users user = usersDao.getUserByUserName(userName);
            while (results.next()) {
                int subscriptionId = results.getInt("SubscriptionId");
                int platformId = results.getInt("PlatformId");

                Platforms platform = platformsDao.getPlatformById(platformId);
                UserSubscriptions userSubscription = new UserSubscriptions(subscriptionId, user, platform);
                userSubscriptions.add(userSubscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return userSubscriptions;
    }
}
