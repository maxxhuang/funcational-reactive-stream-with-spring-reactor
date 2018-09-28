package tenam.learning.functionalreactivestream.application;

import tenam.learning.functionalreactivestream.Flowie;
import tenam.learning.imaginarymodel.AccountInfo;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionalAccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public FunctionalAccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public Flowie<AccountInfo> getAccountInfo(String accountNumber) {
        return this.accountRepository.get(accountNumber).flatMap(account ->
                this.userRepository.get(account.getUserId()).map(user ->
                        new AccountInfo(
                                user.getId(), user.getName(),
                                account.getAccountNumber(), account.getBalance())));

    }
}
