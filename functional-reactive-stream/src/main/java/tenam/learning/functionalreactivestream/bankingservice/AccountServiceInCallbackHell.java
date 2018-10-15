package tenam.learning.functionalreactivestream.bankingservice;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import tenam.learning.functionalreactivestream.Flowie;
import tenam.learning.imaginarymodel.Account;
import tenam.learning.imaginarymodel.AccountInfo;
import tenam.learning.imaginarymodel.User;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceInCallbackHell {

    private UserRepository userRepository;

    private AccountRepository accountRepository;


    public AccountServiceInCallbackHell(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    /**
     * NOTE: This only works in a single-thread execution context.
     * The implementation does not catch the timing of "completion" in onComplete() or onError().
     * In a muti-thread environment, the elements (User, Account) might not be ready when this method returns.
     */
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
