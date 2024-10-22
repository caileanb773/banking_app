package AccountManagement;

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

public class Saving extends Account{
	
	public Saving() {
		super();
	}
	
	public Saving(String accNum, float bal, String accType) {
		this.accountNum = accNum;
		this.balance = bal;
		this.accountType = "Saving";
	}
	
}
