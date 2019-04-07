package main;

import data.resources.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FlappyBird {

    public static final Logger LOGGER = LoggerFactory.getLogger(FlappyBird.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        resourceLoader.setExecutorService(executorService);


        executorService.shutdown();
    }
}
