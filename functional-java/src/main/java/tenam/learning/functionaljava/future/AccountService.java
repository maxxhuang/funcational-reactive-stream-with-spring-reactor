package tenam.learning.functionaljava.future;

import tenam.learning.functionaljava.model.Account;
import tenam.learning.functionaljava.model.AccountInfo;
import tenam.learning.functionaljava.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public CompletableFuture<AccountInfo> getAccountInfo(String accountNumber) {

        CompletableFuture<AccountInfo> result = new CompletableFuture<>();

        try {
            CompletableFuture<Account> accountFuture = this.accountRepository.get(accountNumber);

            Account account = accountFuture.get();

            if (account == null) {
                result.complete(null);
            }

            CompletableFuture<User> userFuture = this.userRepository.get(account.getUserId());

            User user = userFuture.get();

            if (user == null) {
                result.complete(null);
            } else {
                result.complete(AccountInfo.create(user, account));
            }

        } catch (InterruptedException e) {
            result.completeExceptionally(e);
        } catch (ExecutionException e) {
            result.completeExceptionally(e);
        }

        return result;
    }
}
