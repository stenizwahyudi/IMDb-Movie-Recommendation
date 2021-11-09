package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.model.Recommendation;

public class RecommendationDao {
    protected ConnectionManager connectionManager;

    private static RecommendationDao instance = null;
    protected RecommendationDao() {
        connectionManager = new ConnectionManager();
    }
    public static RecommendationDao getInstance() {
        if(instance == null) {
            instance = new RecommendationDao();
        }
        return instance;
    }

    public Recommendation create(Recommendation recommendation) throws SQLException {
        String insertRecommendation =
                "INSERT INTO Recommendation (RecommendationId, Title, Year, Genres, RuntimeMinutes, Country, Language) " +
                "VALUES(?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRecommendation);
            insertStmt.setString(1, recommendation.getRecommendationId());
            insertStmt.setString(2, recommendation.getTitle());
            insertStmt.setInt(3, recommendation.getYear());
            insertStmt.setString(4, recommendation.getGenre());
            insertStmt.setInt(5, recommendation.getRuntimeMinutes());
            insertStmt.setString(6, recommendation.getCountry());
            insertStmt.setString(7, recommendation.getLanguage());

            insertStmt.executeUpdate();
            return recommendation;
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

    public Recommendation delete(Recommendation recommendation) throws SQLException {
        String deleteRecommendation =
                "DELETE FROM Recommendation " +
                "WHERE RecommendationId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteRecommendation);
            deleteStmt.setString(1,recommendation.getRecommendationId());
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

    public Recommendation getRecommendationByRecommendationId (String recommendationId) throws SQLException {
        String selectRecommendation =
                "SELECT RecommendationId, Title, Year, Genres, RuntimeMinutes, Country, Language " +
                "FROM recommendation " +
                "WHERE recommendationId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecommendation);
            selectStmt.setString(1, recommendationId);
            results = selectStmt.executeQuery();
            if(results.next()) {
                String resultRecommendationId = results.getString("RecommendationId");
                String title = results.getString("Title");
                int year = results.getInt("Year");
                String genres = results.getString("Genres");
                int runtimeMinutes = results.getInt("RuntimeMinutes");
                String country = results.getString("Country");
                String language = results.getString("Language");
                Recommendation recommendation = new Recommendation(resultRecommendationId, title, year, genres, runtimeMinutes, country, language);
                return recommendation;
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

    public List<Recommendation> getRecommendationByRecommendationInfo (String title, String genres, String language, int year) throws SQLException {
        List<Recommendation> recommendations = new ArrayList<>();
        String selectRecommendation =
                "SELECT RecommendationId, Title, Year, Genres, RuntimeMinutes, Country, Language " +
                        "FROM recommendation " +
                        "WHERE LOCATE(?, Title) > 0 AND LOCATE(?, Genres) > 0 AND LOCATE(?, Language) > 0 AND LOCATE(?, Year) > 0 " +
                        "LIMIT 50;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecommendation);
            selectStmt.setString(1, title);
            selectStmt.setString(2, genres);
            selectStmt.setString(3, language);
            selectStmt.setInt(4, year);
            results = selectStmt.executeQuery();
            while(results.next()) {
                String resultRecommendationId = results.getString("RecommendationId");
                String resultTitle = results.getString("Title");
                int resultYear = results.getInt("Year");
                String resultsGenres = results.getString("Genres");
                int runtimeMinutes = results.getInt("RuntimeMinutes");
                String country = results.getString("Country");
                String resultLanguage = results.getString("Language");
                Recommendation recommendation = new Recommendation(resultRecommendationId, resultTitle, resultYear, resultsGenres, runtimeMinutes, country, resultLanguage);
                recommendations.add(recommendation);
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
        return recommendations;
    }


    public Recommendation updateLanguage(Recommendation recommendation, String newLanguage) throws  SQLException {
        String updateLanguage =
                "UPDATE Recommendation " +
                "SET Language=? " +
                "WHERE RecommendationId=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateLanguage);
            updateStmt.setString(1, newLanguage);
            updateStmt.setString(2, recommendation.getRecommendationId());
            updateStmt.executeUpdate();

            recommendation.setLanguage(newLanguage);
            return recommendation;
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

