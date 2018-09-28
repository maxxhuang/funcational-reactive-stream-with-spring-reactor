package tenam.learning.functionalreactivestream.application;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import tenam.learning.functionalreactivestream.Flowie;
import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.AccountInfo;
import tenam.learning.imaginarymodel.User;

import java.util.ArrayList;
import java.util.List;

public class NonFunctionalAccountService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public NonFunctionalAccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    public Flowie<AccountInfo> getAccountInfo(String accountNumber) {

        final List<AccountInfo> result = new ArrayList<>();

        this.accountRepository.get(accountNumber).subscribe(new Subscriber<Account>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Account account) {
                userRepository.get(account.getUserId()).subscribe(new Subscriber<User>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(User user) {
                        result.add(AccountInfo.create(user, account));
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        return Flowie.fromIterable(result);

    }
}
