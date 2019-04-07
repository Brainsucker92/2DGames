package main;

import control.Coin;
import control.Flappy;
import control.Obama;
import control.Trump;
import control.entities.AnimationEntity;
import control.entities.GameEntity;
import data.resources.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.animations.AnimationObject;
import ui.components.GameComponent;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
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

        List<AnimationEntity<?>> animationEntities = new ArrayList<>();
        List<GameEntity> gameEntities = new ArrayList<>();

        Coin c1 = new Coin();
        AnimationObject<Coin.Animations> c1AnimationObject = c1.getAnimationObject();
        Coin.Animations coinAnimations = c1AnimationObject.getAnimations();

        c1AnimationObject.setCurrentAnimation(coinAnimations.getCoinRotateAnimation());
        Coin c2 = new Coin();
        AnimationObject<Coin.Animations> c2AnimationObject = c2.getAnimationObject();
        Coin.Animations c2Animations = c2AnimationObject.getAnimations();
        c2AnimationObject.setCurrentAnimation(c2Animations.getCoinShineAnimation());

        Coin c3 = new Coin();
        AnimationObject<Coin.Animations> c3AnimationObject = c3.getAnimationObject();
        Coin.Animations c3Animations = c3AnimationObject.getAnimations();
        c3AnimationObject.setCurrentAnimation(c3Animations.getCoinBlinkAnimation());

        Trump t1 = new Trump();
        Trump t2 = new Trump();
        Trump t3 = new Trump();
        Trump t4 = new Trump();
        Obama o1 = new Obama();

        Flappy f1 = new Flappy();

        AnimationObject<Trump.Animations> t2AnimationObject = t2.getAnimationObject();
        Trump.Animations t2Animations = t2AnimationObject.getAnimations();

        AnimationObject<Trump.Animations> t3AnimationObject = t3.getAnimationObject();
        Trump.Animations t3Animations = t3AnimationObject.getAnimations();

        AnimationObject<Trump.Animations> t4AnimationObject = t4.getAnimationObject();
        Trump.Animations t4Animations = t4AnimationObject.getAnimations();

        t2AnimationObject.setCurrentAnimation(t2Animations.getWalkNorthAnimation());
        t3AnimationObject.setCurrentAnimation(t3Animations.getWalkSouthAnimation());
        t4AnimationObject.setCurrentAnimation(t4Animations.getWalkWestAnimation());

        animationEntities.addAll(List.of(c1, c2, c3, t1, t2, t3, t4, o1, f1));
        gameEntities.addAll(List.of(c1, c2, c3, t1, t2, t3, t4, o1, f1));

        // panel.setLayout(null);
        // panel.setLayout(new FlowLayout());
        panel.setBounds(0, 0, 600, 600);
        // panel.setSize(600, 600);

        for (GameEntity entity : gameEntities) {
            GameComponent gameComponent = entity.getGameComponent();
            panel.add(gameComponent);
        }

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
                for (AnimationEntity<?> animationEntity : animationEntities) {
                    AnimationObject<?> entityAnimationObject = animationEntity.getAnimationObject();
                    entityAnimationObject.updateAnimation();
                }
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 300);
    }
}
