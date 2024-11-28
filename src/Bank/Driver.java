package Bank;

import java.util.HashMap;
import java.util.Scanner;
import User.User;

public class Driver {

	public static void main(String[] args) {

		UserDatabase database = UserDatabase.getInstance();
		HashMap<String, User> userList = database.getUserDatabase();
		Scanner userInput = new Scanner(System.in);
		User defaultUser = new User();
		User activeUser = null;
		Menu menu = new Menu();
		boolean isRunning = true;
		boolean isAdmin = true;
		final int OPTION_1 = 1;
		final int OPTION_2 = 2;
		final int OPTION_3 = 3;
		final int OPTION_4 = 4;
		final int OPTION_5 = 5;
		final int OPTION_6 = 6;
		final int ADMIN = 1;
		final int USER = 0;
		int menuChoice = 0;

		/* ===== Program loop ===== */
		while (isRunning) {
			if (isAdmin) {
				menu.setMenu(ADMIN);
			} else {
				menu.setMenu(USER);
			}
			menu.displayMenu();
			menuChoice = Utility.userIntInput(userInput);

			switch (menuChoice) {

			/* ===== Admin: Register user =====//===== User: Deposit ===== */

			case OPTION_1:
				if (isAdmin) {
					User newUser = defaultUser.registerUser(userInput);
					userList.put(newUser.getStrUserID(), newUser);
				} else {
					activeUser.deposit(userInput);
				}	
				break;

				/* ===== Admin: Print users =====//===== User: Withdraw ===== */

			case OPTION_2:
				if (isAdmin) {
					defaultUser.printUsers(userList);
				} else {
					activeUser.withdraw(userInput);
				}
				break;

				/* ===== Admin: Login as user =====//===== User: Create account ===== */

			case OPTION_3:
				if (isAdmin) {
					activeUser = defaultUser.login(userInput, userList);
					if (activeUser != null) {
						System.out.printf("Logging in %s...\n", activeUser.toString());
						isAdmin = false;
					} else {
						System.out.println("User could not be logged in.");
					}
				} 
				else {
					System.out.printf("What kind of account would you like to create?\n"
							+ "For chequing, press 1. For saving, press 2.\n"
							+ "Press 3 to return.\n> ");
					if (activeUser.registerNewAccount(userInput)) {
						System.out.println("Account created successfully.");
					} else {
						System.out.println("Account could not be created.");
					}
				}
				break;

				/* ===== Admin: Exit program =====//===== User: Remove account ===== */

			case OPTION_4:
				if (isAdmin) {
					System.out.println("Exiting...");
					userInput.close();
					isRunning = false;
				} else {
					if (activeUser.deleteAccount(userInput) == true) {
						System.out.println("The account has been removed.");
					} else {

					}
				}
				break;

				/* ===== Admin: N/A =====//===== User: Check balances ===== */

			case OPTION_5:
				if (isAdmin) {
					System.out.println("Invalid menu selection.");
				} else {
					activeUser.checkAccountBalances();
				}
				break;

				/* ===== Admin: N/A =====//===== User: Log out ===== */

			case OPTION_6:
				if (isAdmin) {
					System.out.println("Invalid menu selection.");
				} else {
					System.out.println("Logging out...");
					isAdmin = true;
				}
				break;

			default:
				System.out.println("Invalid menu selection.");
				break;
			}
		}

	}	// End of main

}	// End of class