package app.model;

public class SeasonEpisodeInfo {
    protected String seriesTitleId;
    protected int seasonNumber;
    protected int noOfEpisode;

    public SeasonEpisodeInfo(String seriesTitle, int seasonNumber, int noOfEpisode) {
        this.seriesTitleId = seriesTitle;
        this.seasonNumber = seasonNumber;
        this.noOfEpisode = noOfEpisode;
    }

    public String getSeriesTitleId() {
        return seriesTitleId;
    }

    public void setSeriesTitleId(String seriesTitleId) {
        this.seriesTitleId = seriesTitleId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getNoOfEpisode() {
        return noOfEpisode;
    }

    public void setNoOfEpisode(int noOfEpisode) {
        this.noOfEpisode = noOfEpisode;
    }
}
