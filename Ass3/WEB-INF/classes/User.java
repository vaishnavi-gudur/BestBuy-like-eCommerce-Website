import java.io.*;
import java.util.*;


public class User implements Serializable {

	private static final long serialVersionUID = -55857686305273843L;

	private String username;
	 private String password;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String value = "UserName : " + username
				+ "\nPassword : " + password;
		return value;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}