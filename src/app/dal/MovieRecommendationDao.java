package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.MovieRecommendation;
import app.model.Recommendation;

public class MovieRecommendationDao extends RecommendationDao {
    private static MovieRecommendationDao instance = null;
    protected MovieRecommendationDao() {
        super();
    }
    public static MovieRecommendationDao getInstance() {
        if(instance == null) {
            instance = new MovieRecommendationDao();
        }
        return instance;
    }

    public MovieRecommendation create(MovieRecommendation movieRecommendation) throws SQLException {
        create(new Recommendation(movieRecommendation.getRecommendationId(), movieRecommendation.getTitle(),
                movieRecommendation.getYear(), movieRecommendation.getGenre(), movieRecommendation.getRuntimeMinutes(),
                movieRecommendation.getCountry(), movieRecommendation.getLanguage()));

        String insertMovieRecommendation =
                "INSERT INTO MovieRecommendation(RecommendationId, Director, SpokenLanguages, Duration) " +
                "VALUES(?,?,?,?)";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMovieRecommendation);
            insertStmt.setString(1, movieRecommendation.getRecommendationId());
            insertStmt.setString(2, movieRecommendation.getDirector());
            insertStmt.setString(3, movieRecommendation.getSpokenLanguages());
            insertStmt.setInt(4, movieRecommendation.getDuration());
            insertStmt.executeUpdate();
            return movieRecommendation;
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

    public MovieRecommendation delete(MovieRecommendation movieRecommendation) throws SQLException {
        String deleteMovieRecommendation =
                "DELETE FROM MovieRecommendation " +
                "WHERE RecommendationId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMovieRecommendation);
            deleteStmt.setString(1, movieRecommendation.getRecommendationId());
            deleteStmt.executeUpdate();
            super.delete(movieRecommendation);
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

    public MovieRecommendation getMovieRecommendationFromRecommendationId(String recommendationId) throws SQLException {
        String selectMovieRecommendation =
                "SELECT MovieRecommendation.RecommendationId AS RecommendationId, Title, Year , Genre, RuntimeMinutes, " +
                        "Country, Language, Director, SpokenLanguages, Duration " +
                        "FROM MovieRecommendation INNER JOIN Recommendation " +
                        "  ON MovieRecommendation.RecommendationId = Recommendation.UserName " +
                        "WHERE MovieRecommendation.RecommendationId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieRecommendation);
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
                String director = results.getString("Director");
                String spokenLanguage = results.getString("SpokenLanguages");
                int duration = results.getInt("Duration");
                MovieRecommendation movieRecommendation = new MovieRecommendation(resultRecommendationId, title, year,
                        genre, runtimeMinutes, country, language, director, spokenLanguage, duration);
                return movieRecommendation;
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

    public List<MovieRecommendation> getMovieRecommendationFromTitle(String title) throws SQLException {
        List<MovieRecommendation> movieRecommendations = new ArrayList<MovieRecommendation>();
        String selectMovieRecommendation =
                "SELECT MovieRecommendation.RecommendationId AS RecommendationId, Title, Year , Genre, RuntimeMinutes, " +
                        "Country, Language, NumberOfSeasons, NumberOfEpisodes " +
                        "FROM MovieRecommendation INNER JOIN Recommendation " +
                        "  ON MovieRecommendation.RecommendationId = Recommendation.RecommendationId " +
                        "WHERE Recommendation.Title=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMovieRecommendation);
            selectStmt.setString(1, title);
            results = selectStmt.executeQuery();
            while(results.next()) {
                String resultRecommendationId = results.getString("RecommendationId");
                int year = results.getInt("Year");
                String genre = results.getString("Genres");
                int runtimeMinutes = results.getInt("RuntimeMinutes");
                String country = results.getString("Country");
                String language = results.getString("Language");
                String director = results.getString("Directors");
                String spokenLanguage = results.getString("SpokenLanguages");
                int duration = results.getInt("Duration");
                MovieRecommendation movieRecommendation = new MovieRecommendation(resultRecommendationId, title, year,
                        genre, runtimeMinutes, country, language, director, spokenLanguage, duration);
                movieRecommendations.add(movieRecommendation);
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
        return movieRecommendations;
    }
}
