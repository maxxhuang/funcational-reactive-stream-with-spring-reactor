package tenam.learning.functionaljava.java8stream;

import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.FakeData;

import java.util.List;

public class AccountRepository {
    public List<Account> get(String accountNumber) {
        return FakeData.accounts.get(accountNumber);
    }
}
