package control;

import data.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.concurrent.ExecutorService;

public class TrumpGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrumpGameController.class);

    private ExecutorService executorService;
    private JPanel panel;
    private GameData gameData;

    public TrumpGameController(ExecutorService executorService, JPanel panel, GameData gameData) {
        this.executorService = executorService;
        this.panel = panel;
        this.gameData = gameData;
        init();
    }

    private void init() {

    }
}
