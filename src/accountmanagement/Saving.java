package accountmanagement;

import java.math.BigDecimal;

public class Saving extends Account{

	public Saving() {
		super();
	}

	public Saving(String accNum, BigDecimal bal, String accType) {
		this.accountNum = accNum;
		this.balance = bal;
		this.accountType = "Saving";
	}

	@Override
	public String toString() {
		String accInfo = String.format("Account #: [%s]\nAccount Type: %s\nAccount Balance: $%.2f", accountNum, accountType, balance);
		return accInfo;
	}

}
