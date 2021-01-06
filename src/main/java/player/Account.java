package player;

public class Account {
    private int balance;

    /**
     *
     * @param balance initial balance
     */
    public Account(int balance) {
        this.balance = Math.max(0, balance);
    }

    /**
     *
     */
    public Account() {
        this.balance = 0;
    }

    /**
     * Withdraws the requested amount or the remaining amount if the account has insufficient funds
     * @param amount the requested amount
     * @return the amount successfully withdrawn
     */
    public int withdraw(int amount) {
        if (amount < 0) //can't withdraw a negative amount
        {
            return 0;
        }
        else if (amount < balance) //withdraw the requested amount
        {
            balance = balance - amount;
            return amount;
        }
        else //withdraw the remaining balance
        {
            amount = balance;
            balance = 0;
            return amount;
        }
    }

    /**
     * Deposit an amount into the account
     * @param amount the amount to be deposited into the account
     * @return transaction success/fail
     */
    public boolean deposit(int amount) {
        if (amount < 0) //can't deposit a negative amount
        {
            return false;
        }
        else
        {
            balance = balance + amount;
            return true;
        }
    }

    @Override
    public String toString() {
        return "$" + balance;
    }

    public int getBalance() {
        return balance;
    }

}
