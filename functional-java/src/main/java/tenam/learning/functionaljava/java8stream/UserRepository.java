package tenam.learning.functionaljava.java8stream;

import tenam.learning.imaginarymodel.FakeData;
import tenam.learning.imaginarymodel.User;

import java.util.Collections;
import java.util.List;

public class UserRepository {
    public List<User> get(String userId) {
        return FakeData.users.containsKey(userId) ?
                Collections.singletonList(FakeData.users.get(userId)) :
                Collections.emptyList();
    }
}
