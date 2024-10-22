package AccountManagement;
import java.util.InputMismatchException;
import java.util.Scanner;

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

public abstract class Account {
	
	protected String accountType = null;
	protected float balance = 0.0f;
	protected String accountNum = null;
	
	public Account() {
		
	}
	
	public void deposit (Scanner in) {
		float amount = 0;
		
		while (true) {
			
			// prompt user
			System.out.print("How much would you like to deposit? You may deposit dollars and cents.\n>$");
			try {
				amount = in.nextFloat();
				in.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("You did not enter a valid dollar amount. Try again.");
				in.nextLine();
				continue;
			}
			
			if (!(amount <= 0)) {
				System.out.printf("Deposited $%.2f into %s account.", amount, this.accountType);
				updateBalance(amount, true);
				break;
			} else {
				System.out.println("You entered a negative number. Enter a positive dollar amount.");
				continue;
			}
			
		}

	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	
	public void updateBalance(float amount, boolean depositOrWithdraw) {
		if (depositOrWithdraw) {
			this.balance += amount;
		} else {
			this.balance -= amount;
		}
	}

}