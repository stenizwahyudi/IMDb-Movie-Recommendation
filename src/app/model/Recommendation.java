package app.model;

public class Recommendation {

    protected String recommendationId;
    protected String title;
    protected int year;
    protected String genre;
    protected int runtimeMinutes;
    protected String country;
    protected String language;

    public Recommendation(String recommendationId, String title, int year, String genre, int runtimeMinutes, String country, String language) {
        this.recommendationId = recommendationId;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.runtimeMinutes = runtimeMinutes;
        this.country = country;
        this.language = language;
    }

    public Recommendation (String title, int year, String genre, int runtimeMinutes, String country, String language) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.runtimeMinutes = runtimeMinutes;
        this.country = country;
        this.language = language;
    }

    public Recommendation (String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public String getRecommendationId() { return recommendationId; }

    public void setRecommendationId(String recommendationId) { this.recommendationId = recommendationId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }

    public void setYear(int year) {this.year = year;}

    public String getGenre() {return genre;}

    public void setGenre(String genre) {this.genre = genre; }

    public int getRuntimeMinutes() { return runtimeMinutes; }

    public void setRuntimeMinutes(int runtimeMinutes) {this.runtimeMinutes = runtimeMinutes;}

    public String getCountry() {return country; }

    public void setCountry(String country) { this.country = country;}

    public String getLanguage() { return language;}

    public void setLanguage(String language) {this.language = language; }
}

