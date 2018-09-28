package tenam.learning.functionaljava.future;

import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.FakeData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AccountRepository extends BaseRepository {


    public AccountRepository() {
        super(defaultExecutorService());
    }

    public CompletableFuture<Account> get(String accountNumber) {
        return CompletableFuture.supplyAsync(
                () -> {
                    List<Account> accounts = FakeData.accounts.get(accountNumber);
                    return accounts.isEmpty() ? null : accounts.get(0);
                },
                this.executorService);
    }
}
