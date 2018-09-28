package tenam.learning.imaginarymodel;

import com.google.common.collect.*;

import java.util.Map;

public class FakeData {

    public static Map<String, User> users = ImmutableMap.<String, User>builder()
            .put("1", new User("1", "user1"))
            .put("2", new User("2", "user2"))
            .put("3", new User("3", "user3"))
            .build();


    public static ListMultimap<String, Account> accounts = ImmutableListMultimap.<String, Account>builder()
            .put("1", new Account("00001", "1", 1000.0))
            .put("2", new Account("00002", "2", 2000.0))
            .put("3", new Account("00003", "3", 3000.0))
            .put("3", new Account("00004", "3", 5000.0))
            .build();

}
