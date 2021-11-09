package app.model;

public class StreamingPlatform {
    protected int streamingId;
    protected Recommendation recommendation;
    protected Platforms platform;
    
	public StreamingPlatform(int streamingId, Recommendation recommendation, Platforms platform) {
	
		this.streamingId = streamingId;
		this.recommendation = recommendation;
		this.platform = platform;
	}
	
	public StreamingPlatform(Recommendation recommendation, Platforms platform) {
		
		this.recommendation = recommendation;
		this.platform = platform;
	}

	public int getStreamingId() {
		return streamingId;
	}

	public void setStreamingId(int streamingId) {
		this.streamingId = streamingId;
	}

	public Recommendation getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}

	public Platforms getPlatform() {
		return platform;
	}

	public void setPlatform(Platforms platform) {
		this.platform = platform;
	}

    
}
