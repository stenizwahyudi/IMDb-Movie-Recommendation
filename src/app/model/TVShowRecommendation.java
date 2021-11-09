package app.model;

public class TVShowRecommendation extends Recommendation {
    protected int numberOfSeasons;
    protected int numberOfEpisodes;

    public TVShowRecommendation(String recommendationId, String title, int year, String genre, int runtimeMinutes, String country, String language, int numberOfSeasons, int numberOfEpisodes) {
        super(recommendationId, title, year, genre, runtimeMinutes, country, language);
        this.numberOfSeasons = numberOfSeasons;
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }
}
