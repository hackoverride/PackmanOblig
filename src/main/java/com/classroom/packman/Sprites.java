/*
 * Sprites is the objects that are actual characters- like the players
 * Packman and the enemy ghosts.
 */
package com.classroom.packman;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Michael & Cato
 */
public class Sprites{
    private double posX, posY, speedX, speedY;
    private String id;
    // Setting up the array for the four directions of each characters.
    private ImageView baseGraphic;
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
    
    Sprites(ImageView iv, int height, int width, String id){
        this(height, width, id);
        this.baseGraphic = iv;
    }
    
    Sprites(ImageView iv, double posX, double posY, String id){
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.baseGraphic = iv;
    }
    
    /**
     *
     * @return
     */
    public double getPosX(){
        return this.posX;
    }
    
    public void setPosition(double x, double y){
        this.posX = x;
        this.posY = y;
    }
    
    /**
     *
     * @return
     */
    public double getPosY(){
        return this.posY;
    }
    
    /**
     *
     * @param speed
     */
    public void setSpeedX(double speed){
        this.speedX = speed;
        this.speedY = 0.0;
    }
    
    /**
     *
     * @param speed
     */
    public void setSpeedY(double speed){
        this.speedY = speed;
        this.speedX = 0.0;
    }
    
    /**
     *
     */
    public void updateSprite(){
        // handeling change of speed with position.
        this.posX = this.posX + this.speedX;
        this.posY = this.posY + this.speedY;
    }
    
    /**
     *
     * @return
     */
    public ImageView getGraphics(){
        return this.baseGraphic;
    }
    
    public void setGraphics(ImageView image){
        this.baseGraphic = image;
    }
    
}
