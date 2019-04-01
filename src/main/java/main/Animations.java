package main;

import control.Coin;
import control.Flappy;
import control.Trump;
import data.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.GameComponent;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Animations {

    private static final Logger LOGGER = LoggerFactory.getLogger(Animations.class);

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        ResourceLoader resourceLoader = ResourceLoader.getInstance();
        resourceLoader.setExecutorService(executorService);

        JFrame frame = new JFrame();
        frame.setTitle("Animations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        Coin c1 = new Coin();
        c1.setCurrentAnimation(c1.getAnimations().getCoinRotateAnimation());
        Coin c2 = new Coin();
        c2.setCurrentAnimation(c2.getAnimations().getCoinShineAnimation());
        Trump t1 = new Trump();
        Trump t2 = new Trump();
        Trump t3 = new Trump();
        Trump t4 = new Trump();

        Flappy f1 = new Flappy();

        GameComponent f1GameComponent = f1.getGameComponent();
        GameComponent c1GameComponent = c1.getGameComponent();
        GameComponent c2GameComponent = c2.getGameComponent();
        GameComponent t1GameComponent = t1.getGameComponent();
        GameComponent t2GameComponent = t2.getGameComponent();
        GameComponent t3GameComponent = t3.getGameComponent();
        GameComponent t4GameComponent = t4.getGameComponent();

        t2.setCurrentAnimation(t2.getAnimations().getWalkNorthAnimation());
        t3.setCurrentAnimation(t3.getAnimations().getWalkSouthAnimation());
        t3.setCurrentAnimation(t3.getAnimations().getWalkWestAnimation());


        f1GameComponent.setSize(50, 50);
        c1GameComponent.setSize(100, 100);
        c2GameComponent.setSize(100, 100);
        t1GameComponent.setSize(100, 100);
        t2GameComponent.setSize(100, 100);
        t3GameComponent.setSize(100, 100);
        t4GameComponent.setSize(100, 100);

        f1GameComponent.setLocation(0, 0);
        t1GameComponent.setLocation(150, 50);
        t2GameComponent.setLocation(150, 150);
        t3GameComponent.setLocation(150, 250);
        t4GameComponent.setLocation(150, 350);
        c1GameComponent.setLocation(0, 100);
        c2GameComponent.setLocation(0, 200);

        // panel.setLayout(null);
        // panel.setLayout(new FlowLayout());
        panel.setBounds(0, 0, 600, 600);
        // panel.setSize(600, 600);

        panel.add(c1GameComponent);
        panel.add(c2GameComponent);
        panel.add(t1GameComponent);
        panel.add(f1GameComponent);
        panel.add(t2GameComponent);
        panel.add(t3GameComponent);
        panel.add(t4GameComponent);

        panel.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                executorService.shutdown();
            }
        });

        frame.getContentPane().add(panel);
        // frame.setLayout(null);
        frame.setSize(400, 400);
        frame.pack();
        frame.setVisible(true);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                c1.updateAnimation();
                c2.updateAnimation();
                t1.updateAnimation();
                t2.updateAnimation();
                t3.updateAnimation();
                t4.updateAnimation();
                f1.updateAnimation();
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 300);
    }
}
