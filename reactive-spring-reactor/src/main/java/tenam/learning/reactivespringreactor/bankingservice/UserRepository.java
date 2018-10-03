package tenam.learning.reactivespringreactor.bankingservice;

import reactor.core.publisher.Mono;
import tenam.learning.imaginarymodel.FakeData;
import tenam.learning.imaginarymodel.User;

public class UserRepository {
    public Mono<User> get(String userId) {
        return Mono.justOrEmpty(FakeData.users.get(userId));
    }
}
