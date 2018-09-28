package tenam.learning.functionaljava.future;

import tenam.learning.imaginarymodel.FakeData;
import tenam.learning.imaginarymodel.User;

import java.util.concurrent.CompletableFuture;

public class UserRepository extends BaseRepository {

    public UserRepository() {
        super(defaultExecutorService());
    }

    public CompletableFuture<User> get(String userId) {
        return CompletableFuture.supplyAsync(
                () -> FakeData.users.get(userId),
                this.executorService
        );
    }
}
