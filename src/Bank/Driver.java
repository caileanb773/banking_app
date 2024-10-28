package Bank;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import User.User;
import java.awt.Toolkit;

public class Driver {

	static Map<String, User> userList = new HashMap<>();
	static User defaultUser = new User();
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		User activeUser = null;
		int menuChoice = 0;
		boolean isUser = false;
		boolean isProgramRunning = true;

		while (isProgramRunning) {
			if (!isUser) {
				mainMenu();
			} else {
				userMenu();
			}

			menuChoice = getMenuChoice(input);
			switch (menuChoice) {

			case 1:
				
				// Admin mode: register new user
				// User mode: deposit money
				if (!isUser) {
					User newUser = defaultUser.registerUser(input);
					userList.put(newUser.getStrUserID(), newUser);
				} else {
					activeUser.depositOrWithdraw(input, true);
				}	
				break;

			case 2:
				
				// Admin mode: display users
				// User mode: withdraw money
				if (!isUser) {
					defaultUser.printUsers(userList);
				} else {
					activeUser.depositOrWithdraw(input, false);
				}
				break;

			case 3:
				// Admin mode: login as user
				// User mode: create new account

				// If there is no user logged in
				if (!isUser) {
					activeUser = defaultUser.loginUser(input, userList);
					if (activeUser != null) {
						System.out.printf("Logging in %s...\n", activeUser.toString());
						isUser = true;
					} else {
						System.out.println("User could not be logged in.");
					}
				} 

				// If there is a user logged in
				else {
					if (activeUser.registerNewAccount(input)) {
						System.out.println("Account created successfully.");
					} else {
						System.out.println("Account could not be created.");
					}
				}
				break;

			case 4:

				// Admin mode: exit
				// User mode: close account
				if (!isUser) {
					System.out.println("Exiting...");
					input.close();
					isProgramRunning = false;
				} else {
					activeUser.deleteAccount(input);
				}
				break;

			case 5:

				// Print accounts and balances
				if (!isUser) {
					System.out.println("Invalid menu selection.");
				} else {
					activeUser.checkAccountBalances();
				}
				break;

			case 6:
				// Logout user
				if (!isUser) {
					System.out.println("Invalid menu selection.");
				} else {
					System.out.println("Logging out...");
					isUser = false;
				}
				break;
			default:
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