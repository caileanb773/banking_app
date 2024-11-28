package Bank;

import java.util.HashMap;
import User.User;

public class UserDatabase {

	private static final HashMap<String, User> userDatabase = new HashMap<String, User>();

	private UserDatabase() {

	}

	public static HashMap<String, User> initUserDatabase(){
		return userDatabase;
	}

}
