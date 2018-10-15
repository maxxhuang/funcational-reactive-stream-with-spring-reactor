package tenam.learning.functionalreactivestream.bankingservice;

import tenam.learning.functionalreactivestream.Flowie;
import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.FakeData;

public class AccountRepository {
    public Flowie<Account> get(String accountNumber) {
        return Flowie.fromIterable(
                FakeData.accounts.get(accountNumber)
        );
    }
}
