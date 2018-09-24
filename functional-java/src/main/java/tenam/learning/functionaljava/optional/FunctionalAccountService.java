package tenam.learning.functionaljava.optional;

import tenam.learning.functionaljava.model.Account;
import tenam.learning.functionaljava.model.AccountInfo;
import tenam.learning.functionaljava.model.User;

import java.util.Optional;

public class FunctionalAccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public FunctionalAccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public Optional<AccountInfo> getAccountInfo(String accountNumber) {
        return this.accountRepository.get(accountNumber).flatMap(account ->
                this.userRepository.get(account.getUserId()).map(user ->
                        new AccountInfo(
                                user.getId(), user.getName(),
                                account.getAccountNumber(), account.getBalance())));
    }
}
