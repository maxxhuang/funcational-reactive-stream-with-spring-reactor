package tenam.learning.functionaljava.optional;

import tenam.learning.functionaljava.model.FakeData;
import tenam.learning.functionaljava.model.User;

import java.util.Optional;

public class UserRepository {
    public Optional<User> get(String userId) {
        return Optional.ofNullable(FakeData.users.get(userId));
    }
}
