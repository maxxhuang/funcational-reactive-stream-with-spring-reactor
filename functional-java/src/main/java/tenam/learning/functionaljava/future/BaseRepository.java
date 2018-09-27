package tenam.learning.functionaljava.future;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BaseRepository {

    public static ExecutorService defaultExecutorService() {
        return Executors.newCachedThreadPool(
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
        );
    }


    protected ExecutorService executorService;


    public BaseRepository(ExecutorService executorService) {
        this.executorService = Objects.requireNonNull(executorService);
    }


    final public void shutdownExecutors() {
        if (this.executorService == null) {
            return;
        }

        try {
            if (!this.executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                this.executorService.shutdown();
            }
        } catch (InterruptedException e) {
            this.executorService.shutdown();
        }
    }
}
