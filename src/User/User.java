package User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import AccountManagement.Account;
import AccountManagement.Chequing;
import AccountManagement.Saving;

/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * Student Name: Cailean Bernard
 * Student Number: 041143947
 * Section #: 300-301
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi, Howard Rosenblum
 * Contents:
 * 
 */

public class User {

	private String name = null;
	private int intUserID = 0;
	private String strUserID = null;
	private String password = null;
	ArrayList<Account> accounts = new ArrayList<Account>();

	public User() {

	}

	public User(String n, String id, String pw) {
		this.name = n;
		this.strUserID = id;
		this.password = pw;
	}
	
	public boolean registerNewAccount(Scanner in) {
		Account accToAdd = createAccount(in);
		if (accToAdd != null) {
			addAccount(accToAdd);
			return true;
		} else {
			return false;
		}
	}
	
	public String generateAccountNumber() {
		Random r = new Random();
		int num = r.nextInt(1000000);
		return String.format("%06d", num);
	}
	
	public Account createAccount(Scanner in) {
		int accountChoice = 0;
		Account newAccount;
		
		System.out.println("What kind of account would you like to create?\n"
				+ "For chequing, press 1. For saving, press 2.\n> ");
		while (true) {
			try {
				accountChoice = in.nextInt();
				in.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid choice.");
				in.nextLine();
				continue;
			}
			if (accountChoice == 1) {
				String accountNum = generateAccountNumber();
				newAccount = new Chequing(accountNum, 0.0f, "Chequing");
				return newAccount;
			} else if (accountChoice == 2) {
				String accountNum = generateAccountNumber();
				newAccount = new Saving(accountNum, 0.0f, "Saving");
				return newAccount;
			} else {
				System.out.println("Invalid Choice.");
				in.nextLine();
				continue;
			}
		}
	}

	public User registerUser(Scanner in) {
		String name = null;
		String pass = null;
		String formattedString = null;

		// Set name
		while (true) {
			System.out.print("Enter a name for this user:\n> ");
			name = in.nextLine();
			if (name.trim().isEmpty()) {
				System.out.print("Name field cannot be left blank, try again.\n");
				continue;
			} else {
				break;
			}
		}

		// Set password
		while (true) {
			System.out.print("Enter a password for this user:\n> ");
			pass = in.nextLine();
			if (pass.trim().isEmpty()) {
				System.out.println("Password field cannot be left blank, try again.");
				continue;
			} else {
				break;
			}
		}

		this.intUserID++;
		this.strUserID = String.format("%04d", intUserID);
		formattedString = String.format("User [%s] was registered with ID [%S].", name, this.strUserID);
		System.out.println(formattedString);
		User newCust = new User(name, this.strUserID, pass);
		return newCust;
	}

	public User loginUser(Scanner in, Map<String, User> users) {
		String idToLogIn = null;
		String userPass = null;
		while (true) {
			System.out.print("Enter the 4-digit ID of the user you would like to log in as:\n> ");
			idToLogIn = in.nextLine();
			
			// Check that userID is not left blank
			if (idToLogIn.trim().isEmpty()) {
				System.out.println("Must enter an ID to log-in as a user. Try again.");
				continue;
			} else {
				
				// Check that the user is registered in the database
				if (checkIfUserExists(users, idToLogIn)) {
					System.out.println("User exists in database.");
					User userToLogIn = users.get(idToLogIn);
					
					// Password validation
					while (true) {
						System.out.print("Enter password:\n> ");
						userPass = in.nextLine();
						if (!(userPass.trim().isEmpty())) {
							
							// If the password equals the password of the user to log in
							if (userPass.equals(userToLogIn.getPassword())) {
								System.out.println("Password verified.");
								return userToLogIn;
							} else {
								System.out.println("Password incorrect.");
								return null;
							}
						}
					}
					
					// UserID not found in database, return
				} else {
					System.out.println("A user with the provided ID was not found in the database.");
					return null;
				}
				
			}
		}
	}
	
	public boolean checkIfUserExists(Map<String, User> userMap, String idToFind) {
		for (Map.Entry<String, User> entry : userMap.entrySet()) {
			if (entry.getKey().equals(idToFind)) {
				return true;
			}
		}
		return false;
	}

	public void addAccount(Account acc) {
		this.accounts.add(acc);
	}

	public void removeAccount(Account acc) {
		this.accounts.remove(acc);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void printUsers(Map<String, User> users) {
		System.out.println("--- Currently Registered Users ---");
		for (Map.Entry<String, User> entry : users.entrySet()) {
			String userID = entry.getKey();
			User userToPrint = entry.getValue();
			System.out.printf("User: [%s] ID: [%s]\n", userToPrint.getName(), userID);
		}
	}

	public int getIntUserID() {
		return intUserID;
	}

	public void setIntUserID(int intUserID) {
		this.intUserID = intUserID;
	}

	public String getStrUserID() {
		return strUserID;
	}

	public void setStrUserID(String strUserID) {
		this.strUserID = strUserID;
	}

	@Override
	public String toString() {
		String formattedUser = String.format("User [%s] ID [%s]", name, this.strUserID);
		return formattedUser;
	}

}
