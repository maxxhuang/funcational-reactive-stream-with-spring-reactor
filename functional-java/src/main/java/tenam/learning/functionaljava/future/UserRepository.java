package tenam.learning.functionaljava.future;

import tenam.learning.functionaljava.model.FakeData;
import tenam.learning.functionaljava.model.User;

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
