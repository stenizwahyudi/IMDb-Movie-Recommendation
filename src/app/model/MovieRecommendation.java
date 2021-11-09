package app.model;

public class MovieRecommendation extends Recommendation {
    protected String director;
    protected String spokenLanguages;
    protected int duration;

    public MovieRecommendation (String recommendationId, String title, int year, String genre, int runtimeMinutes, String country, String language, String director, String spokenLanguages, int duration) {
        super(recommendationId, title, year, genre, runtimeMinutes, country, language);
        this.director = director;
        this.spokenLanguages = spokenLanguages;
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
