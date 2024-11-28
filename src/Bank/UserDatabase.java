package Bank;

import java.util.HashMap;
import User.User;

public class UserDatabase {

	private static UserDatabase instance;
	
	private HashMap<String, User> userDatabase;

	private UserDatabase() {
		userDatabase = new HashMap<>();
	}

	public static synchronized UserDatabase getInstance() {
		if (instance == null) {
			instance = new UserDatabase();
		}
		return instance;
	}
	
	public HashMap<String, User> getUserDatabase() {
		return userDatabase;
	}

}
