package app.model;

public class CastInfo {
    public String actorId;
    public String name;

    public CastInfo(String actorId, String name) {
        this.actorId = actorId;
        this.name = name;
    }
    
    public CastInfo(String actorId) {
        this.actorId = actorId;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
