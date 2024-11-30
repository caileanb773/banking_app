package user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import accountmanagement.Account;
import accountmanagement.Chequing;
import accountmanagement.Saving;
import bank.Utility;

public class User {

	ArrayList<Account> accounts = new ArrayList<Account>();
	private String strUserID = null;
	private String password = null;
	private String name = null;
	private final boolean WITHDRAW = false;
	private final boolean DEPOSIT = true;
	private final boolean NOT_FOUND = false;
	private final BigDecimal MAX_REALISTIC_AMOUNT = new BigDecimal(1000000000000.00);
	private final int INVALID = -1;
	private final int CHEQUING = 1;
	private final int SAVINGS = 2;
	private final int EMPTY = 0;
	private final int EXIT = 3;
	private int intUserID = 0;

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
			System.out.println("Creating account with ID [" + accToAdd.getAccountNum() + "] ...");
			addAccount(accToAdd);
			return true;
		} else {
			return false;
		}
	}

	public Account createAccount(Scanner in) {
		int accountChoice = 0;
		Account newAccount = new Account();
		String accountNum = null;

		// Ask user which account would they would like to create
		while (true) {
			accountChoice = Utility.userIntInput(in);
			switch (accountChoice) {
			case CHEQUING:
				accountNum = newAccount.generateAccountId();
				newAccount = new Chequing(accountNum, BigDecimal.ZERO, "Chequing");
				return newAccount;
			case SAVINGS:
				accountNum = newAccount.generateAccountId();
				newAccount = new Saving(accountNum, BigDecimal.ZERO, "Saving");
				return newAccount;
			case EXIT:
				System.out.println("Returning...");
				return null;
			default:
				System.out.println("Invalid option; please choose 1 for chequing, 2 for savings, or 3 to return.");
				break;
			}
		}
	}

	public void checkAccountBalances() {
		System.out.println("--- Account Information ---");
		if (accounts.size() != EMPTY) {
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

	public User login(Scanner in, Map<String, User> users) {
		String idToLogIn = null;
		String userPass = null;
		while (true) {
			idToLogIn = inputUserID(in);

			if (checkIfUserExists(users, idToLogIn) == NOT_FOUND) {
				return null;
			}
			User userToLogin = users.get(idToLogIn);

			while (true) {
				userPass = inputPassword(in);
				if (verifyPassword(userPass, userToLogin) == true) {
					return userToLogin;
				} else {
					return null;
				}
			}
		}
	}
	
	private boolean verifyPassword(String attemptedPassword, User user) {
		String passwordToMatch = user.getPassword();
		if (attemptedPassword.equals(passwordToMatch)) {
			System.out.println("Password verified.");
			return true;
		} else {
			System.out.println("Incorrect password.");
			return false;
		}
	}

	private String inputPassword(Scanner in) {
		System.out.print("Enter password:\n> ");
		String userPass = in.nextLine();
		if (userPass.trim().isEmpty()) {
			System.out.println("Field left blank. Returning...");
			return null;
		}
		return userPass;
	}

	public String inputUserID(Scanner in) {
		String id = null;

		while (true) {
			System.out.print("Enter the 4-digit ID of the user to log in as:\n> ");
			id = in.nextLine();

			if (id.trim().isEmpty()) {
				System.out.println("Must enter an ID to log-in as a user. Try again");
				continue;
			}
			break;
		}
		return id;
	}

	public boolean checkIfUserExists(Map<String, User> userMap, String idToFind) {
		for (Map.Entry<String, User> entry : userMap.entrySet()) {
			if (entry.getKey().equals(idToFind)) {
				System.out.println("User exists in database.");
				return true;
			}
		}
		System.out.println("A user with the provided ID was not found in the database.");
		return false;
	}

	public void deposit(Scanner in) {
		Account accountToUpdate = null;
		String accountID = null;
		int index = 0;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal currentAccountBalance = BigDecimal.ZERO;

		if (accounts.size() == 0) {
			System.out.println("User has no active accounts.");
			return;
		}
		while (true) {
			System.out.printf("What is the account number that you would like to deposit to?\n> ");
			accountID = in.nextLine();
			if (accountID.trim().isEmpty()) {
				System.out.println("You left the account ID field blank. Returning...");
				return;

			} else {
				index = checkIfAccountExists(accountID);

				// checkifAccountExists returns -1 if account not found
				if (index != -1) {
					accountToUpdate = accounts.get(index);
					currentAccountBalance = accountToUpdate.getBalance();
					System.out.printf("Account verified. Current balance: $%.2f\n", currentAccountBalance);
					amount = getDepositAmt(in);
					accountToUpdate.updateBalance(amount, DEPOSIT);
					System.out.println("Transaction successful.");
					return;
				} else {
					System.out.println("Account was not found.");
					return;
				}
			}
		}
	}

	public void withdraw(Scanner in) {
		Account accountToUpdate = null;
		String accountID = null;
		int index = 0;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal currentAccountBalance = BigDecimal.ZERO;

		if (accounts.size() == 0) {
			System.out.println("User has no active accounts.");
			return;
		}

		// Get account ID from user
		while (true) {
			System.out.printf("What is the account number that you would like to withdraw from?\n> ");
			accountID = in.nextLine();

			// Check that accountID is not blank
			if (accountID.trim().isEmpty()) {
				System.out.println("You left the account ID field blank. Returning...");
				return;

			} else {
				index = checkIfAccountExists(accountID);

				if (index != INVALID) {
					accountToUpdate = accounts.get(index);
					currentAccountBalance = accountToUpdate.getBalance();
					System.out.printf("Account verified. Current balance: $%.2f\n", currentAccountBalance);

					if (currentAccountBalance.compareTo(BigDecimal.ZERO) == 0) {
						System.out.println("Account balance is $0, nothing to withdraw.\nReturning to menu...");
						return;
					} else {
						amount = getWithdrawAmt(in, currentAccountBalance);
					}
					accountToUpdate.updateBalance(amount, WITHDRAW);
					System.out.println("Transaction successful.");
					return;
				} else {
					System.out.println("Account was not found.");
					return;
				}
			}
		}
	}

	public BigDecimal getDepositAmt(Scanner in) {
		BigDecimal amount = BigDecimal.ZERO;

		while (true) {
			amount = Utility.userAmountInput(in);

			// Handle if the user enters 0
			if (amount.compareTo(BigDecimal.ZERO) == 0) {
				System.out.println("Deposit must be greater than zero dollars.");
				continue;
			}

			if (amount.compareTo(MAX_REALISTIC_AMOUNT) == 1) {
				System.out.println("You are trying to deposit an unrealistic amount of money. Please try with a smaller amount.");
				continue;
			}
			
			System.out.printf("Depositing $%.2f...\n", amount);
			return amount;
		}
	}

	public BigDecimal getWithdrawAmt(Scanner in, BigDecimal currentBal) {
		BigDecimal amount = BigDecimal.ZERO;

		while (true) {
			System.out.print("Enter a dollar value:\n> ");
			amount = Utility.userAmountInput(in);

			if (amount.compareTo(BigDecimal.ZERO) == -1) {
				System.out.println("Enter a dollar value greater than 0.");
				continue;
			}

			if (amount.compareTo(MAX_REALISTIC_AMOUNT) == 1) {
				System.out.println("You are trying to deposit an unrealistic amount of money. Please try with a smaller amount.");
				continue;
			}

			if ((currentBal.subtract(amount)).compareTo(BigDecimal.ZERO) == -1) {
				System.out.println("Withdrawing that amount would put account in negative balance.");
				continue;
			}
						
			System.out.printf("Withdrawing %.2f...\n", amount);
			return amount;
			
		}
	}

	public boolean deleteAccount(Scanner in) {
		String accNum = null;
		int index = 0;

		if (accounts.size() == EMPTY) {
			System.out.println("User has no active accounts.");
			return false;
		}

		while (true) {
			System.out.print("Enter the number for the account you would like to delete:\n> ");
			accNum = in.nextLine();
			if (!(accNum.trim().isEmpty())) {
				index = checkIfAccountExists(accNum);
				break;
			} else {
				System.out.println("The ID to search field was left blank.\nReturning...");
				return false;
			}
		}

		if (index == INVALID) {
			System.out.println("The account with that ID was not found.\nReturning...");
			return false;
		}

		if (isAccountEmpty(index) == false) {
			System.out.println("Cannot remove an account carrying a balance, withdraw all funds first.");
			return false;
		}
		accounts.remove(index);
		return true;
	}

	public boolean isAccountEmpty(int accountIndex) {
		Account acc = accounts.get(accountIndex);
		return acc.getBalance().compareTo(BigDecimal.ZERO) == 0;
	
	}

	public int checkIfAccountExists(String accountID) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).getAccountNum().equals(accountID)) {
				return i;
			}
		}
		return INVALID;
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
