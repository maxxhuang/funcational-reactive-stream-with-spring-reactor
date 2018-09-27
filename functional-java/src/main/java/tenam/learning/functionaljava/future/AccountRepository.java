package tenam.learning.functionaljava.future;

import tenam.learning.functionaljava.model.Account;
import tenam.learning.functionaljava.model.FakeData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

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
