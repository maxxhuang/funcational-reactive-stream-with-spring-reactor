package tenam.learning.functionaljava.optional;

import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.FakeData;

import java.util.Optional;

public class AccountRepository {
    public Optional<Account> get(String accountNumber) {
        return FakeData.accounts.get(accountNumber).stream().findFirst();
    }
}
