/*
 * The little engine that ticks...
 */
package com.classroom.packman;

import java.util.TimerTask;

/**
 *
 * @author Michael & Cato
 */
public class GameEngine extends TimerTask {
    
    int ticker = 1;
    boolean running = false;
    
    
    public GameEngine(){
        
    }
    
    public void stop(){
        this.running = false;
    }
    
    public void run(){
        // starting up the gameloop...
        this.running = true;
        System.out.println("tick: " + ticker++);
    }
    
    
    
    
}
