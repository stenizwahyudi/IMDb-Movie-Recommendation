package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.model.Platforms;
import app.model.Recommendation;
import app.model.StreamingPlatform;
import app.model.UserSubscriptions;

public class PlatformsDao {
    protected app.dal.ConnectionManager connectionManager;

    private static PlatformsDao instance = null;

    protected PlatformsDao() {
        connectionManager = new app.dal.ConnectionManager();
    }

    public static PlatformsDao getInstance() {
        if (instance == null) {
            instance = new PlatformsDao();
        }
        return instance;
    }

    public Platforms create(Platforms platforms) throws SQLException {
        String insertPlatforms =
                "INSERT INTO Platforms(PlatformName) " +
                        "VALUES(?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPlatforms,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, platforms.getPlatformName());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int platformId = -1;
            if (resultKey.next()) {
                platformId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            platforms.setPlatformId(platformId);
            return platforms;
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
     * Update the content of the Platforms instance.
     * This runs a UPDATE statement.
     *
     * I just realized that all the non-PK's here are FK's. So does that mean I can't even UPDATE?? If so, delete this entire function.
     */
    public Platforms updatePlatformName(Platforms platforms, String newPlatformName) throws SQLException {
        String updatePlatformName = "UPDATE Platform SET PlatformName=? WHERE PlatformId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatePlatformName);
            updateStmt.setString(1, newPlatformName);
            updateStmt.setInt(2, platforms.getPlatformId());
            updateStmt.executeUpdate();

            // Update the platform param before returning to the caller.
            platforms.setPlatformName(newPlatformName);
            return platforms;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    /**
     * Delete the Platforms instance.
     * This runs a DELETE statement.
     */
    public Platforms delete(Platforms platforms) throws SQLException {
        String deletePlatform = "DELETE FROM Platforms WHERE PlatformId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePlatform);
            deleteStmt.setInt(1, platforms.getPlatformId());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Platforms instance.
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
     * Get the BlogComments record by fetching it from your MySQL instance.
     * This runs a SELECT statement and returns a single BlogComments instance.
     * Note that we use BlogPostsDao and BlogUsersDao to retrieve the referenced
     * BlogPosts and BlogUsers instances.
     * One alternative (possibly more efficient) is using a single SELECT statement
     * to join the BlogComments, BlogPosts, BlogUsers tables and then build each object.
     */
    public Platforms getPlatformById(int platformId) throws SQLException {
        String selectPlatform =
                "SELECT PlatformId,PlatformName " +
                        "FROM Platforms " +
                        "WHERE PlatformId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPlatform);
            selectStmt.setInt(1, platformId);
            results = selectStmt.executeQuery();

            if(results.next()) {
                int resultPlatformId = results.getInt("PlatformId");
                String platformName = results.getString("PlatformName");
                
                Platforms platforms = new Platforms(resultPlatformId, platformName);
                return platforms;
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

     * Get the all the Platforms for a streaming platform.
     */
    public List<Platforms> getPlatformsForStreamingPlatform(StreamingPlatform streamingPlatform) throws SQLException {

        List<Platforms> platforms = new ArrayList<Platforms>();
        String selectPlatforms =
                "SELECT PlatformName " +
                        "FROM Platforms " +
                        "WHERE PlatformId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPlatforms);
            selectStmt.setInt(1, streamingPlatform.getPlatform().getPlatformId());
            results = selectStmt.executeQuery();

            while(results.next()) {
                int platformId = results.getInt("PlatformId");
                String platformName = results.getString("PlatformName");
                Platforms platform = new Platforms(platformId, platformName);
                platforms.add(platform);
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
        return platforms;
    }

    /**
     * Get the all the Platforms for a user subscription.
     */
    public List<Platforms> getPlatformsForUserSubscription(UserSubscriptions userSubscriptions) throws SQLException {
        List<Platforms> platforms = new ArrayList<Platforms>();
        String selectUserSubscriptions =
                "SELECT PlatformId,UserName " +
                        "FROM Platforms " +
                        "WHERE PlatformId=?;";

        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();

            selectStmt = connection.prepareStatement(selectUserSubscriptions);
            selectStmt.setInt(1, userSubscriptions.getPlatform().getPlatformId());
            results = selectStmt.executeQuery();
            
            while(results.next()) {
                int platformId = results.getInt("PlatformId");
                String platformName = results.getString("PlatformName");

                Platforms platform = new Platforms(platformId, platformName);
                platforms.add(platform);
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
        return platforms;
    }

    /**
     * Get the all the Platforms for a user subscription.
     */
    public List<Platforms> getPlatformsForRecommendation(Recommendation recommendation) throws SQLException {
        List<Platforms> platforms = new ArrayList<Platforms>();
        String selectUserSubscriptions =
                "SELECT platforms.PlatformId, platformName " +
                        "FROM Platforms " +
                        "INNER JOIN StreamingPlatform " +
                        "ON StreamingPlatform.platformid = platforms.platformid " +
                        "WHERE recommendationid=?;";

        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();

            selectStmt = connection.prepareStatement(selectUserSubscriptions);
            selectStmt.setString(1, recommendation.getRecommendationId());
            results = selectStmt.executeQuery();

            while(results.next()) {
                int platformId = results.getInt("PlatformId");
                String platformName = results.getString("PlatformName");

                Platforms platform = new Platforms(platformId, platformName);
                platforms.add(platform);
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
        return platforms;
    }
}
