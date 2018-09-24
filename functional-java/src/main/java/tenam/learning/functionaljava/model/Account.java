package tenam.learning.functionaljava.model;

public class Account {

    private String accountNumber;
    private String userId;
    private double balance;


    public Account(String accountNumber, String userId, double balance) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.balance = balance;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }
}
