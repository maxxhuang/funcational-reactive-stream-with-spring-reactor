package tenam.learning.functionaljava.future;

import tenam.learning.imaginarymodel.AccountInfo;

import java.util.concurrent.CompletableFuture;

public class FunctionalAccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public FunctionalAccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public CompletableFuture<AccountInfo> getAccountInfo(String accountNumber) {
        return this.accountRepository.get(accountNumber).thenCompose(account ->
                this.userRepository.get(account.getUserId()).thenApply(user ->
                        AccountInfo.create(user, account)));

    }
}
