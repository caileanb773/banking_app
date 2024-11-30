package accountmanagement;

import java.math.BigDecimal;

public class Account {

	protected String accountType = null;
	protected String accountNum = null;
	protected final boolean WITHDRAW = false;
	protected final boolean DEPOSIT = true;
	protected static int intAccountNum = 1;
	protected BigDecimal balance = BigDecimal.ZERO;

	public Account() {

	}

	public String generateAccountId() {
		return String.format("%04d", intAccountNum++);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public void updateBalance(BigDecimal amount, boolean updateType) {
		if (updateType == DEPOSIT) {
			this.balance = this.balance.add(amount);
		} else if (updateType == WITHDRAW){
			this.balance = this.balance.subtract(amount);
		}
	}

}