package tenam.learning.functionaljava.optional;

import tenam.learning.imaginarymodel.FakeData;
import tenam.learning.imaginarymodel.User;

import java.util.Optional;

public class UserRepository {
    public Optional<User> get(String userId) {
        return Optional.ofNullable(FakeData.users.get(userId));
    }
}
