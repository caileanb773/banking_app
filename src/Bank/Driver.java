package Bank;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import User.User;
import java.awt.Toolkit;

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

public class Driver {

	static Map<String, User> userList = new HashMap<>();
	static User usr = new User();
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		User currentUser = null;
		int menuChoice = 0;
		int currentMenu = 1;
		boolean isLoggedIn = false;
		boolean isProgramRunning = true;

		while (isProgramRunning) {
			if (!isLoggedIn) {
				mainMenu();
			} else {
				userMenu();
			}

			menuChoice = getMenuChoice(input);
			switch (menuChoice) {
			case 1:
				if (!isLoggedIn) {
					User newUser = usr.registerUser(input);
					userList.put(newUser.getStrUserID(), newUser);
				} else {
					// Deposit money
				}	
				break;
			case 2:
				if (!isLoggedIn) {
					usr.printUsers(userList);
				} else {
					// Withdraw Money
				}
				break;
			case 3:
				if (!isLoggedIn) {
					User loggedInUser = usr.loginUser(input, userList);
					if (loggedInUser != null) {
						System.out.printf("Logging in %s...\n", loggedInUser.toString());
						isLoggedIn = true;
					} else {
						System.out.println("User could not be logged in.");
					}
				} 

				// If there isn't a user logged in
				else {
					if (usr.registerNewAccount(input)) {
						System.out.println("Account created successfully.");
					} else {
						System.out.println("Account could not be created.");
					}
				}
				break;
			case 4:
				if (!isLoggedIn) {
					System.out.println("Exiting...");
					input.close();
					isProgramRunning = false;
				} else {
					// Close account
				}
				break;
			case 5:
				// check balances
				break;
			case 6:
				if (!isLoggedIn) {
					System.out.println("Invalid menu selection.");
				} else {
					System.out.println("Logging out...");
					isLoggedIn = false;
				}
				break;
			default:
				System.out.println("That menu option doesn't exist.");
				Toolkit.getDefaultToolkit().beep();
				break;
			}
		}

		// End of main
	}

	public static void mainMenu() {
		System.out.print("--- Main Menu ---\n"
				+ "1. Register new user\n"
				+ "2. User list\n"
				+ "3. Log-in existing user\n"
				+ "4. Exit\n> ");
	}

	public static void userMenu() {
		System.out.print("--- User Features ---\n"
				+ "1. Deposit money\n"
				+ "2. Withdraw money\n"
				+ "3. Open new account\n"
				+ "4. Close account\n"
				+ "5. Check balances\n"
				+ "6. Logout\n> ");
	}

	public static int getMenuChoice(Scanner in) {
		int menuChoice = 0;
		try {
			menuChoice = in.nextInt();
			in.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Numerical characters only.");
			in.nextLine();
		}
		return menuChoice;
	}

}