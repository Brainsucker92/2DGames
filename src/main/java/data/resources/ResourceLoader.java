package data.resources;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ResourceLoader {

    private static ResourceLoader instance;
    private ExecutorService executorService;

    private ResourceLoader() {
        // private constructor
    }

    public static ResourceLoader getInstance() {
        if (instance == null) {
            instance = new ResourceLoader();
        }
        return instance;
    }

    public Future<?> loadResources(List<Resource<?>> resources) {
        if (executorService == null) {
            throw new IllegalStateException("No ExecutorService available to load resources");
        }

        return executorService.submit(() -> resources.forEach(Resource::load));
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
