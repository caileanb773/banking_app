package User;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import AccountManagement.Account;
import AccountManagement.Chequing;
import AccountManagement.Saving;

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
			System.out.println("Account with ID [" + accToAdd.getAccountNum() + "] created.");
			addAccount(accToAdd);
			return true;
		} else {
			return false;
		}
	}

	public Account createAccount(Scanner in) {
		int accountChoice = 0;
		Account newAccount = new Account();

		// Ask user which account would they would like to create
		while (true) {
			System.out.print("What kind of account would you like to create?\n"
					+ "For chequing, press 1. For saving, press 2.\n> ");
			try {
				accountChoice = in.nextInt();
				in.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("An integer was not detected, try again.");
				in.nextLine();
				continue;
			}
			if (accountChoice == 1) {
				String accountNum = newAccount.generateAccountId();
				newAccount = new Chequing(accountNum, 0.0f, "Chequing");
				return newAccount;
			} else if (accountChoice == 2) {
				String accountNum = newAccount.generateAccountId();
				newAccount = new Saving(accountNum, 0.0f, "Saving");
				return newAccount;
			} else {
				System.out.println("The only valid menu options are 1 or 2.");
				continue;
			}
		}
	}

	public void checkAccountBalances() {
		System.out.println("--- Account Information ---");
		if (accounts.size() != 0) {
			for (Account acc : accounts) {
				System.out.println(acc + "\n");
			}
		} else {
			System.out.println("User has no active accounts.");
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
			System.out.print("Enter the 4-digit ID of the user to log in as:\n> ");
			idToLogIn = in.nextLine();

			// Check that userID is not left blank
			if (idToLogIn.trim().isEmpty()) {
				System.out.println("Must enter an ID to log-in as a user. Try again");
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

	public void depositOrWithdraw(Scanner in, boolean depositOrWithdraw) {
		Account accountToUpdate = null;
		String accountID = null;
		int index = 0;
		float amount = 0.0f;
		String fundsAdjust = null;
		float currentAccountBalance = 0.0f;

		if (depositOrWithdraw) {
			fundsAdjust = "deposit to";
		} else {
			fundsAdjust = "withdraw from";
		}

		if (accounts.size() == 0) {
			System.out.println("User has no active accounts.");
			return;
		}

		// Get account ID from user
		while (true) {
			System.out.printf("What is the account number that you would like to %s?\n> ", fundsAdjust);
			accountID = in.nextLine();

			// Check that accountID is not blank
			if (accountID.trim().isEmpty()) {
				System.out.println("You left the account ID field blank. Returning...");
				return;

			} else {

				// Check that the account exists in user
				index = checkIfAccountExists(accountID);

				// If the account exists (is not == -1)
				if (index != -1) {
					// Set the account to the index retrieved
					accountToUpdate = accounts.get(index);
					currentAccountBalance = accountToUpdate.getBalance();
					System.out.printf("Account verified. Current balance: $%.2f", currentAccountBalance);

					// Fetch the amount to update by from user
					if (depositOrWithdraw) {
						amount = getAmountToUpdate(in, true, 0.0f);
					} else {
						if (currentAccountBalance == 0) {
							System.out.println("Account balance is $0, nothing to withdraw.\nReturning to menu...");
							return;
						} else {
							amount = getAmountToUpdate(in, false, currentAccountBalance);
						}
					}

					// Update the balance in the account
					if (depositOrWithdraw) {
						accountToUpdate.updateBalance(amount, true);
					} else {
						accountToUpdate.updateBalance(amount, false);
					}

					// Final confirmation to user
					System.out.println("Transaction successful.");
					return;
				} else {
					System.out.println("Account was not found.");
					return;
				}
			}
		}
	}

	public float getAmountToUpdate(Scanner in, boolean depositOrWithdraw, float currentBal) {
		float amount = 0.0f;

		while (true) {
			System.out.print("Enter a dollar value:\n> ");
			try {
				amount = in.nextFloat();
				in.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("You did not enter a dollar amount.");
				in.nextLine();
				continue;
			}

			// Handle if the user enters 0
			if (amount <= 0) {
				System.out.println("Enter a dollar value greater than 0.");
				continue;
			}

			// Depositing logic
			if (depositOrWithdraw) {
				System.out.printf("Depositing $%.2f...\n", amount);
				return amount;
			} else {

				// Withdrawal logic
				if ((currentBal - amount) < 0) {
					System.out.println("Withdrawing that amount would put account in negative balance.");
					continue;
				} else {
					System.out.printf("Withdrawing %.2f...\n", amount);
					return amount;
				}
			}
		}
	}

	public void deleteAccount(Scanner in) {
		String accNum = null;
		int index = 0;

		while (true) {
			System.out.print("Enter the number for the account you would like to delete:\n> ");
			accNum = in.nextLine();
			if (!(accNum.trim().isEmpty())) {
				index = checkIfAccountExists(accNum);
				break;
			} else {
				System.out.println("The ID to search field was left blank.\nReturning...");
				return;
			}
		}
		
		if (index == -1) {
			System.out.println("The account with that ID was not found.\nReturning...");
			return;
		}
		System.out.println("The account has been removed.");
		accounts.remove(index);
	}

	public int checkIfAccountExists(String accountID) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountNum().equals(accountID)) {
				return i;
			}
		}
		return -1;
	}

	public void printUsers(Map<String, User> users) {
		System.out.println("--- Currently Registered Users ---");
		for (Map.Entry<String, User> entry : users.entrySet()) {
			User userToPrint = entry.getValue();
			System.out.println(userToPrint);
		}
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
		String formattedUser = String.format("User [%s] Name: [%s]", this.strUserID, name);
		return formattedUser;
	}

}
