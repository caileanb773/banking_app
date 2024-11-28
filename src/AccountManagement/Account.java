package AccountManagement;

public class Account {

	protected String accountType = null;
	protected String accountNum = null;
	protected final boolean WITHDRAW = false;
	protected final boolean DEPOSIT = true;
	protected static int intAccountNum = 1;
	protected float balance = 0.0f;

	public Account() {

	}

	public String generateAccountId() {
		return String.format("%04d", intAccountNum++);
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

	public void updateBalance(float amount, boolean updateType) {
		if (updateType == DEPOSIT) {
			this.balance += amount;
		} else if (updateType == WITHDRAW){
			this.balance -= amount;
		}
	}

}