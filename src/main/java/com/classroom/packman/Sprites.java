/*
 * Sprites is the objects that are actual characters- like the players
 * Packman and the enemy ghosts.
 */
package com.classroom.packman;

import javafx.scene.image.ImageView;

/**
 *
 * @author Michael & Cato
 */
public class Sprites {
    private double posX, posY, speedX, speedY;
    private String id;
    // Setting up the array for the four directions of each characters.
    private ImageView baseGraphic[];
    private double size = 10.0;
    
    Sprites(){
        this.posX = 0;
        this.posY = 0;
        this.id = "NA";
        this.speedX = 1;
        this.speedY = 0;
        
    }
    
    Sprites(int height, int width, String id){
        this.posX = Math.floor(width /2);
        this.posY = Math.floor(height /2);
        this.id = id;
    }
    
    Sprites(ImageView[] iv, int height, int width, String id){
        this(height, width, id);
        this.baseGraphic = iv;
    }
    
    Sprites(ImageView[] iv, double posX, double posY, String id){
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.baseGraphic = iv;
    }
    
    public double getPosX(){
        return this.posX;
    }
    
    public double getPosY(){
        return this.posY;
    }
    
    public void setSpeedX(double speed){
        this.speedX = speed;
        this.speedY = 0.0;
    }
    
    public void setSpeedY(double speed){
        this.speedY = speed;
        this.speedX = 0.0;
    }
    
    public void updateSprite(){
        // handeling change of speed with position.
        this.posX = this.posX + this.speedX;
        this.posY = this.posY + this.speedY;
    }
    
    public ImageView getGraphics(){
        // all images views have four in the array.
        if ( this.baseGraphic.length == 4){
            // now find what to send based on speed/direction.
            // 0 is up, 1 is right, 2 is down and 3 is left.
            if (this.speedX > 0){
                // moving right
                return this.baseGraphic[1];
            } else if (this.speedX < 0){
                // moving left
                return this.baseGraphic[3];
            } else if (this.speedY > 0){
                // moving down
                return this.baseGraphic[2];
            } else if (this.speedY < 0){
                // moving up
                return this.baseGraphic[0];
            }
            
            
        } else {
            // throw error
        }
        // if no speed sat then return is default up.
        return this.baseGraphic[0];
    }
    
}
