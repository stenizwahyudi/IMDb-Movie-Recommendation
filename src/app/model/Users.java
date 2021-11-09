package app.model;

public class Users {
	/**
	 * Username, Password, Email
	 */
	protected String userName;
	protected String password;
	protected String email;
	public Users(String userName, String password, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
	}
	public Users(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
