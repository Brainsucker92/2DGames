package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class GameData {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameData.class);

    private ExecutorService executorService;

    public GameData(ExecutorService executorService) {
        this.executorService = executorService;
        init();
    }

    private void init() {
    }
}
