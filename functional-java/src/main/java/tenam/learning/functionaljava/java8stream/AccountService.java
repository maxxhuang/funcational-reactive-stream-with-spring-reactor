package tenam.learning.functionaljava.java8stream;

import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.AccountInfo;
import tenam.learning.imaginarymodel.User;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public List<AccountInfo> getAccountInfo(String accountNumber) {

        List<AccountInfo> infos = new ArrayList<>();

        for (Account account : this.accountRepository.get(accountNumber)) {
            for (User user : this.userRepository.get(account.getUserId())) {
                infos.add(AccountInfo.create(user, account));
            }
        }

        return infos;
    }
}
