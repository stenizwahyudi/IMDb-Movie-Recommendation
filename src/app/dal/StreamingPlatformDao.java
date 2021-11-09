package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.model.Platforms;
import app.model.Recommendation;
import app.model.StreamingPlatform;


public class StreamingPlatformDao {
    protected ConnectionManager connectionManager;

    private static StreamingPlatformDao instance = null;
    protected StreamingPlatformDao() {
        connectionManager = new ConnectionManager();
    }
    public static StreamingPlatformDao getInstance() {
        if(instance == null) {
            instance = new StreamingPlatformDao();
        }
        return instance;
    }

    public StreamingPlatform create(StreamingPlatform streamingPlatform) throws SQLException {
        String insertStreamingPlatform =
                "INSERT INTO StreamingPlatform(RecommendationId, PlatformId) " +
                        "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            // StreamingPlatform has an auto-generated key. So we want to retrieve that key.
            insertStmt = connection.prepareStatement(insertStreamingPlatform,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, streamingPlatform.getRecommendation().getRecommendationId());
            insertStmt.setInt(2, streamingPlatform.getPlatform().getPlatformId());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            // For more details, see:
            // http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
            resultKey = insertStmt.getGeneratedKeys();
            int streamingId = -1;
            if(resultKey.next()) {
                streamingId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            streamingPlatform.setStreamingId(streamingId);
            return streamingPlatform;
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
     * Delete the StreamingPlatform instance.
     * This runs a DELETE statement.
     */
    public StreamingPlatform delete(StreamingPlatform streamingPlatform) throws SQLException {
        String deleteStreamingPlatform = "DELETE FROM StreamingPlatform WHERE StreamingId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteStreamingPlatform);
            deleteStmt.setInt(1, streamingPlatform.getStreamingId());
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
     * Get the StreamingPlatform record by fetching it from your MySQL instance.
     * This runs a SELECT statement and returns a single StreamingPlatform instance.
     * Note that we use BlogUsersDao to retrieve the referenced BlogUsers instance.
     * One alternative (possibly more efficient) is using a single SELECT statement
     * to join the StreamingPlatform, BlogUsers tables and then build each object.
     */
    public StreamingPlatform getStreamingPlatformById(int streamingId) throws SQLException {
        String selectStreamingPlatform =
                "SELECT RecommendationId,PlatformId " +
                        "FROM StreamingPlatform " +
                        "WHERE StreamingId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectStreamingPlatform);
            selectStmt.setInt(1, streamingId);
            results = selectStmt.executeQuery();
            PlatformsDao platformsDao = PlatformsDao.getInstance();
            RecommendationDao recommendationDao = RecommendationDao.getInstance();
            if(results.next()) {
                int resultStreamingId = results.getInt("StreamingId");
                int platformId = results.getInt("PlatformId");
                String recommendationId = results.getString("RecommendationId");

                Platforms platform = platformsDao.getPlatformById(platformId);
                Recommendation recommendation = recommendationDao.getRecommendationByRecommendationId(recommendationId) ;
                StreamingPlatform streamingPlatform = new StreamingPlatform(resultStreamingId, recommendation, platform);
                return streamingPlatform;
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
}
