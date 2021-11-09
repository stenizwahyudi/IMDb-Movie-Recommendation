package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Recommendation;
import app.model.TVShowRecommendation;

/**
 * Data access object (DAO) class to interact with the underlying Administrators table in your
 * MySQL instance. This is used to store {@link TVShowRecommendation} into your MySQL instance and
 * retrieve {@link TVShowRecommendation} from MySQL instance.
 */
public class TVShowRecommendationDao extends RecommendationDao {
    // Single pattern: instantiation is limited to one object.
    private static TVShowRecommendationDao instance = null;
    protected TVShowRecommendationDao() {
        super();
    }
    public static TVShowRecommendationDao getInstance() {
        if(instance == null) {
            instance = new TVShowRecommendationDao();
        }
        return instance;
    }

    public TVShowRecommendation create(TVShowRecommendation tvShowRecommendation) throws SQLException {
        // Insert into the superclass table first.
        create(new Recommendation(tvShowRecommendation.getRecommendationId(), tvShowRecommendation.getTitle(),
                tvShowRecommendation.getYear(), tvShowRecommendation.getGenre(), tvShowRecommendation.getRuntimeMinutes(),
                tvShowRecommendation.getCountry(), tvShowRecommendation.getLanguage()));

        String insertTVShowRecommendation = "INSERT INTO TVShowRecommendation(RecommendationId,NumberOfSeasons,NumberOfEpisodes) " +
                "VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertTVShowRecommendation);
            insertStmt.setString(1, tvShowRecommendation.getRecommendationId());
            insertStmt.setInt(2, tvShowRecommendation.getNumberOfSeasons());
            insertStmt.setInt(3, tvShowRecommendation.getNumberOfEpisodes());
            insertStmt.executeUpdate();
            return tvShowRecommendation;
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


    /**
     * Update the numberOfSeasons of the TVShowRecommendation instance.
     * This runs a UPDATE statement.
     */
    public TVShowRecommendation updateNumberOfSeasons(TVShowRecommendation tvShowRecommendation, String newNumberOfSeasons)
            throws SQLException {
        updateNumberOfSeasons(tvShowRecommendation, newNumberOfSeasons);
        return tvShowRecommendation;
    }

    /**
     * Delete the TVShowRecommendation instance.
     * This runs a DELETE statement.
     */
    public TVShowRecommendation delete(TVShowRecommendation tvShowRecommendation) throws SQLException {
        String deleteTVShowRecommendation = "DELETE FROM TVShowRecommendation WHERE RecommendationId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteTVShowRecommendation);
            deleteStmt.setString(1, tvShowRecommendation.getRecommendationId());
            deleteStmt.executeUpdate();

            // Then also delete from the superclass.
            // Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
            // super.delete() without even needing to delete from TVShowRecommendation first.
            super.delete(tvShowRecommendation);

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

    public TVShowRecommendation getTVShowRecommendationFromRecommendationId(String recommendationId) throws SQLException {
        // To build an TVShowRecommendation object, we need the Recommendation record, too.
        String selectTVShowRecommendation =
                "SELECT TVShowRecommendation.RecommendationId AS RecommendationId, Title, Year , Genre, RuntimeMinutes, " +
                        "Country, Language, NumberOfSeasons, NumberOfEpisodes " +
                        "FROM TVShowRecommendation INNER JOIN Recommendation " +
                        "  ON TVShowRecommendation.RecommendationId = Recommendation.UserName " +
                        "WHERE TVShowRecommendation.RecommendationId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTVShowRecommendation);
            selectStmt.setString(1, recommendationId);
            results = selectStmt.executeQuery();
            if(results.next()) {
                String resultRecommendationId = results.getString("RecommendationId");
                String title = results.getString("Title");
                int year = results.getInt("Year");
                String genre = results.getString("Genre");
                int runtimeMinutes = results.getInt("RuntimeMinutes");
                String country = results.getString("Country");
                String language = results.getString("Language");
                int numberOfSeasons = results.getInt("NumberOfSeasons");
                int numberOfEpisodes = results.getInt("NumberOfEpisodes");

                TVShowRecommendation tvShowRecommendation = new TVShowRecommendation(resultRecommendationId, title,
                        year, genre, runtimeMinutes, country, language, numberOfSeasons, numberOfEpisodes);
                return tvShowRecommendation;
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

    public List<TVShowRecommendation> getTVShowRecommendationFromTitle(String title)
            throws SQLException {
        List<TVShowRecommendation> tvShowRecommendations = new ArrayList<TVShowRecommendation>();
        String selectTVShowRecommendation =
                "SELECT TVShowRecommendation.RecommendationId AS RecommendationId, Title, Year , Genre, RuntimeMinutes, " +
                        "Country, Language, NumberOfSeasons, NumberOfEpisodes " +
                        "FROM TVShowRecommendation INNER JOIN Recommendation " +
                        "  ON TVShowRecommendation.RecommendationId = Recommendation.RecommendationId " +
                        "WHERE Recommendation.Title=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTVShowRecommendation);
            selectStmt.setString(1, title);
            results = selectStmt.executeQuery();
            while(results.next()) {
                String resultRecommendationId = results.getString("RecommendationId");
                int year = results.getInt("Year");
                String genre = results.getString("Genre");
                int runtimeMinutes = results.getInt("RuntimeMinutes");
                String country = results.getString("Country");
                String language = results.getString("Language");
                int numberOfSeasons = results.getInt("NumberOfSeasons");
                int numberOfEpisodes = results.getInt("NumberOfEpisodes");
                TVShowRecommendation tvShowRecommendation = new TVShowRecommendation(resultRecommendationId, title,
                        year, genre, runtimeMinutes, country, language, numberOfSeasons, numberOfEpisodes);
                tvShowRecommendations.add(tvShowRecommendation);
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
        return tvShowRecommendations;
    }
}