package app.model;

public class UserSubscriptions {
	public int subscriptionId;
	protected Users users;
	protected Platforms platform;
	
	public UserSubscriptions(int subscriptionId, Users users, Platforms platformId) {
		this.subscriptionId = subscriptionId;
		this.users = users;
		this.platform = platformId;
	}
	
	public UserSubscriptions(Users users, Platforms platformId) {
		this.users = users;
		this.platform = platformId;
	}

	public int getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Platforms getPlatform() {
		return platform;
	}

	public void setPlatform(Platforms platform) {
		this.platform = platform;
	}
}
