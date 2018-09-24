package tenam.learning.functionaljava.model;

public class AccountInfo {

    private String userId;
    private String userName;
    private String accountNumber;
    private double balance;


    public AccountInfo(String userId, String userName, String accountNumber, double balance) {
        this.userId = userId;
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }


    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

}
