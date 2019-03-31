package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class GameData {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameData.class);

    private ExecutorService executorService;

    private List<Resource> resourceList = new ArrayList<>();

    private Future<?> resourcesLoaded;

    public GameData(ExecutorService executorService) {
        this.executorService = executorService;
        init();
    }

    private void init() {
        for (Resource resource : resourceList) {
            LOGGER.debug("Using resource: " + resource.getPath().toString());
        }
        resourcesLoaded = loadResources();

    }

    /**
     * Waits until all resources have been loaded.
     */
    public void waitResourcesLoaded() {
        if (!resourcesLoaded.isDone()) {
            LOGGER.debug("Waiting to complete resource loading");
            try {
                resourcesLoaded.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private Future<?> loadResources() {
        return executorService.submit(() -> {
            for (Resource resource : resourceList) {
                if (!resource.isLoaded()) {
                    LOGGER.debug("Loading resource: " + resource.getPath());
                    resource.load();
                }
            }
        });
    }

    public Future<?> reloadResource(Resource<?> resource) {
        return executorService.submit(() -> resource.load(true));
    }
}
