package tenam.learning.reactivespringreactor.bankingservice;

import reactor.core.publisher.Flux;
import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.FakeData;

public class AccountRepository {
    public Flux<Account> get(String accountNumber) {
        return Flux.fromIterable(
                FakeData.accounts.get(accountNumber)
        );
    }
}
