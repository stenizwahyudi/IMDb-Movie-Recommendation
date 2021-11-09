package app.dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.SeasonEpisodeInfo;

public class SeasonEpisodeInfoDao {
    protected app.dal.ConnectionManager connectionManager;

    private static SeasonEpisodeInfoDao instance = null;

    protected SeasonEpisodeInfoDao() {
        connectionManager = new app.dal.ConnectionManager();
    }

    public static SeasonEpisodeInfoDao getInstance() {
        if (instance == null) {
            instance = new SeasonEpisodeInfoDao();
        }
        return instance;
    }

    public SeasonEpisodeInfo create(SeasonEpisodeInfo seasonEpisodeInfo) throws SQLException {
        String insertSeasonEpisodeInfo =
            "INSERT INTO SeasonEpisodeInfo(SeriesTitleId, SeasonNumber, NoOfEpisode) " +
                "VALUES(?,?,?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertSeasonEpisodeInfo);
            insertStmt.setString(1, seasonEpisodeInfo.getSeriesTitleId());
            insertStmt.setInt(2, seasonEpisodeInfo.getSeasonNumber());
            insertStmt.setInt(3, seasonEpisodeInfo.getNoOfEpisode());
            insertStmt.executeUpdate();
            return seasonEpisodeInfo;
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
        }
    }

    public SeasonEpisodeInfo getSeasonEpisodeInfoBySeriesTitleId(String seriesTitleId) throws SQLException {
        String selectSeasonEpisodeInfo =
            "SELECT *" +
                "FROM SeasonEpisodeInfo " +
                "WHERE SeriesTitleId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectSeasonEpisodeInfo);
            selectStmt.setString(1, seriesTitleId);
            results = selectStmt.executeQuery();
            if(results.next()) {
                String resultSeriesTitleId = results.getString("SeriesTitleId");
                int seasonNumber = results.getInt("SeasonNumber");
                int noOfEpisode = results.getInt("NoOfEpisode");

                SeasonEpisodeInfo seasonEpisodeInfo = new SeasonEpisodeInfo(resultSeriesTitleId,
                        seasonNumber, noOfEpisode);
                return seasonEpisodeInfo;
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
     * Update the content of the SeasonEpisodeInfo instance.
     * This runs a UPDATE statement.
     */
    public SeasonEpisodeInfo updateSeasonNumber(SeasonEpisodeInfo seasonEpisodeInfo, int newSeasonNumber) throws SQLException {
        String updateSeasonNumber = "UPDATE SeasonEpisodeInfo SET SeasonNumber=? WHERE SeriesTitleId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateSeasonNumber);
            updateStmt.setInt(1, newSeasonNumber);
            updateStmt.setString(2, seasonEpisodeInfo.getSeriesTitleId());
            updateStmt.executeUpdate();

            // Update the seasonEpisodeInfo param before returning to the caller.
            seasonEpisodeInfo.setSeasonNumber(newSeasonNumber);
            return seasonEpisodeInfo;
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
     * Delete the SeasonEpisodeInfo instance.
     * This runs a DELETE statement.
     */
    public SeasonEpisodeInfo delete(SeasonEpisodeInfo seasonEpisodeInfo) throws SQLException {
        String deleteSeasonEpisodeInfo = "DELETE FROM SeasonEpisodeInfo WHERE SeriesTitleId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteSeasonEpisodeInfo);
            deleteStmt.setString(1, seasonEpisodeInfo.getSeriesTitleId());
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
}
