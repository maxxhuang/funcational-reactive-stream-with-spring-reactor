package tenam.learning.functionaljava.optional;

import tenam.learning.functionaljava.model.Account;
import tenam.learning.functionaljava.model.FakeData;

import java.util.Optional;

public class AccountRepository {
    public Optional<Account> get(String accountNumber) {
        return FakeData.accounts.get(accountNumber).stream().findFirst();
    }
}
