package tenam.learning.imaginarymodel;

public class AccountInfo {

    public static AccountInfo create(User user, Account account) {
        return new AccountInfo(user.getId(), user.getName(),
                account.getAccountNumber(), account.getBalance());
    }


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
