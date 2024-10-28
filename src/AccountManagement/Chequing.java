package AccountManagement;

public class Chequing extends Account{

	public Chequing() {
		super();
	}

	public Chequing(String accNum, float bal, String accType) {
		this.accountNum = accNum;
		this.balance = bal;
		this.accountType = "Chequing";
	}

	@Override
	public String toString() {
		String accInfo = String.format("Account #: [%s]\nAccount Type: %s\nAccount Balance: $%.2f", accountNum, accountType, balance);
		return accInfo;
	}

}
