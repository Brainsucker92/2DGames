package main;

import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import data.resources.MP3SoundResource;
import data.resources.Resource;
import data.resources.ResourceLoader;
import data.resources.Resources;

public class SoundBoard {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        resourceLoader.setExecutorService(executorService);

        List<Resource<?>> sounds = List.of(Resources.TRUMP_HEY, Resources.TRUMP_THATS_FINE,
                Resources.TRUMP_PHENOMENAL, Resources.TRUMP_UNITED_STATES_IS_RUN_BY_STUPID_PEOPLE,
                Resources.HITLER_NEIN_NEIN_NEIN, Resources.DIXIE_HORN);

        Future<?> loadResources = resourceLoader.loadResources(sounds);

        JFrame frame = new JFrame();
        frame.setTitle("Sound Board");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame.getContentPane().add(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                executorService.shutdown();
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadResources.get();

        for (Resource<?> resource : sounds) {
            JButton button = new JButton();
            MP3SoundResource sound = ((MP3SoundResource) resource);
            Clip clip = sound.getData();
            button.setText(sound.getPath().getFileName().toString());
            button.addActionListener(e -> {
                clip.setMicrosecondPosition(0);
                clip.start();
            });

            panel.add(button);
        }
        frame.pack();
        frame.setVisible(true);
    }
}
