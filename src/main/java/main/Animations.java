package main;

import control.Coin;
import control.Flappy;
import control.Trump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.GameComponent;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Animations {

    private static final Logger LOGGER = LoggerFactory.getLogger(Animations.class);

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Animations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        Coin c1 = new Coin();
        c1.setCurrentAnimation(c1.getAnimations().getCoinRotateAnimation());
        Coin c2 = new Coin();
        c2.setCurrentAnimation(c2.getAnimations().getCoinShineAnimation());
        Trump t1 = new Trump();

        Flappy f1 = new Flappy();

        GameComponent f1GameComponent = f1.getGameComponent();
        GameComponent c1GameComponent = c1.getGameComponent();
        GameComponent c2GameComponent = c2.getGameComponent();
        GameComponent t1GameComponent = t1.getGameComponent();

        f1GameComponent.setBounds(0, 0, 20, 20);
        c1GameComponent.setBounds(20, 20, 50, 50);
        c2GameComponent.setBounds(70, 70, 50, 50);
        t1GameComponent.setBounds(150, 150, 100, 100);

//        f1GameComponent.setSize(50, 50);
//        c1GameComponent.setSize(100, 100);
//        c2GameComponent.setSize(100, 100);
//        t1GameComponent.setSize(100, 100);
//
//        f1GameComponent.setLocation(0, 0);
//        t1GameComponent.setLocation(0, 100);
//        c1GameComponent.setLocation(0, 100);
        // c2GameComponent.setLocation(0, 200);

        panel.setLayout(null);
        // panel.setLayout(new FlowLayout());
        panel.setBounds(0, 0, 600, 600);
        // panel.setSize(600, 600);

        panel.add(c1GameComponent);
        panel.add(c2GameComponent);
        panel.add(t1GameComponent);
        panel.add(f1GameComponent);

        panel.setVisible(true);
//        JButton button = new JButton();
//        JButton button2 = new JButton();

//        panel.add(button);
//        panel.add(button2);

        frame.getContentPane().add(panel);
        frame.setLayout(null);
        frame.setSize(400, 400);
        frame.pack();
        frame.setVisible(true);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                c1.updateAnimation();
                c2.updateAnimation();
                t1.updateAnimation();
                f1.updateAnimation();
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 300);
    }
}
