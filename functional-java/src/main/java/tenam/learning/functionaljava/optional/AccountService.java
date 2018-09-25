package tenam.learning.functionaljava.optional;

import tenam.learning.functionaljava.model.Account;
import tenam.learning.functionaljava.model.AccountInfo;
import tenam.learning.functionaljava.model.User;

import java.util.Optional;

public class AccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public Optional<AccountInfo> getAccountInfo(String accountNumber) {

        Optional<Account> optAccount = this.accountRepository.get(accountNumber);

        if (!optAccount.isPresent()) {
            return Optional.empty();
        }

        Account account = optAccount.get();

        Optional<User> optUser = this.userRepository.get(account.getUserId());

        if (!optUser.isPresent()) {
            return Optional.empty();
        }

        User user = optUser.get();

        return Optional.of(AccountInfo.create(user, account));

    }
}
