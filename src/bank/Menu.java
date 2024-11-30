package bank;

public class Menu {
	
	private final int ADMIN = 1;
	private final int USER = 0;
	private String menuContents;
	
	public Menu() {
		
	}
	
	public void setMenu(int menuType) {
		if (menuType == ADMIN) {
			menuContents = "--- Main Menu ---\n"
					+ "1. Register new user\n"
					+ "2. User list\n"
					+ "3. Log-in existing user\n"
					+ "4. Exit\n> ";
		} else if (menuType == USER) {
			menuContents = "--- User Features ---\n"
					+ "1. Deposit money\n"
					+ "2. Withdraw money\n"
					+ "3. Open new account\n"
					+ "4. Close account\n"
					+ "5. Check balances\n"
					+ "6. Logout\n> ";
		} else {
			menuContents = null;
		}
	}
	
	public void displayMenu() {
		System.out.printf(menuContents);
	}

}
