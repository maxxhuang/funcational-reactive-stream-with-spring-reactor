package tenam.learning.functionaljava.java8stream;

import tenam.learning.functionaljava.model.Account;
import tenam.learning.functionaljava.model.FakeData;

import java.util.List;

public class AccountRepository {
    public List<Account> get(String accountNumber) {
        return FakeData.accounts.get(accountNumber);
    }
}
