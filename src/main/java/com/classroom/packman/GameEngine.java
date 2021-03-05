/*
 * The little engine that ticks...
 */
package com.classroom.packman;

import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author Michael & Cato
 */
public class GameEngine {
    
    int ticker = 1;
    boolean running = false;
    private Render playBoard;
    final int HEIGHT = 900;
    final int WIDTH = HEIGHT;
    
    /**
     *
     */
    public GameEngine(Render playBoard){
        this.playBoard = playBoard;
    }
        
    /**
     *
     */
    public void stop(){
        this.running = false;
    }
    
    public void run(){
        // starting up the gameloop...
        this.running = true;
        //playBoard.draw(this.HEIGHT - 20, this.WIDTH);
        Timeline timeline = new Timeline();
        KeyFrame keys = new KeyFrame(Duration.millis(100), e -> {
            playBoard.ghostLogic();
            playBoard.render();
        });
        timeline.getKeyFrames().add(keys);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    public boolean isRunning(){
        return this.running;
    }
    
    
    
    
    
    
}
