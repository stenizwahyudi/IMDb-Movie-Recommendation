package app.model;

public class Platforms {
    protected int platformId;
    protected String platformName;

//        NETFLIX("Netflix", 1),
//        HULU("Hulu", 2),
//        HBO("HBO", 3),
//        PRIMEVIDEO("Prime Video", 4),
//        DISNEYPLUS("Disney+", 5);

    public Platforms(int platformId, String platformName) {
        this.platformId = platformId;
        this.platformName = platformName;
    }
    public Platforms(String platformName) {
        this.platformName = platformName;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}
