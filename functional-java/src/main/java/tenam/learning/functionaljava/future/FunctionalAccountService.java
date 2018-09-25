package tenam.learning.functionaljava.future;

import tenam.learning.functionaljava.model.AccountInfo;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionalAccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public FunctionalAccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public List<AccountInfo> getAccountInfo(String accountNumber) {
        return this.accountRepository.get(accountNumber).stream().flatMap(account ->
                this.userRepository.get(account.getUserId()).stream().map(user ->
                        new AccountInfo(
                                user.getId(), user.getName(),
                                account.getAccountNumber(), account.getBalance())))
                .collect(Collectors.toList());

    }
}
