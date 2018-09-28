package tenam.learning.functionalreactivestream.application;

import tenam.learning.functionalreactivestream.Flowie;
import tenam.learning.imaginarymodel.FakeData;
import tenam.learning.imaginarymodel.User;

import java.util.Collections;
import java.util.List;

public class UserRepository {
    public Flowie<User> get(String userId) {
        List<User> user = FakeData.users.containsKey(userId) ?
                Collections.singletonList(FakeData.users.get(userId)) :
                Collections.emptyList();

        return Flowie.fromIterable(user);
    }
}
