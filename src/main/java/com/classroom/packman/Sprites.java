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
    private int[] startPosition = new int[2];
    private int levelPosX, levelPosY;
    private String id;
    // Setting up the array for the four directions of each characters.
    private ImageView baseGraphic;
    // for ghost to wait before leaving 'safezone'
    private int waitTicker = 0;
    private char lastChar = 'O';
    
    Sprites(){
        this.posX = 0;
        this.posY = 0;
        this.id = "NA";
        this.speedX = 1;
        this.speedY = 0;
        
    }
    
     /**
     *
     * @param height
     * @param width
     * @param id
     */
    
    Sprites(int height, int width, String id){
        this.posX = Math.floor(width /2);
        this.posY = Math.floor(height /2);
        this.id = id;
    }
    
    /**
     *
     * @param iv
     * @param height
     * @param width
     * @param id
     */
    
    
    Sprites(ImageView iv, int height, int width, String id){
        this(height, width, id);
        this.baseGraphic = iv;
    }
    /**
     *
     * @param iv
     * @param posX
     * @param posY
     * @param id
     */
    
    
    Sprites(ImageView iv, double posX, double posY, String id){
        this.posX = posX;
        this.posY = posY;
        this.id = id;
        this.baseGraphic = iv;
    }
    
    /**
     *
     * @return graphic pos of player or ghost
     */
    public double getPosX(){
        return this.posX;
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public void setStartPosition(int x, int y){
        this.startPosition[0] = y;
        this.startPosition[1] = x;
    }
    
    /**
     *
     * @return returns an int array containing x and y start coordinates
     */
    public int[] getStartPosition() {
        return this.startPosition;
    }
    
    /**
     *
     * @param c 
     */
    public void setLastChar(char c){
        this.lastChar = c;
    }
    
    /**
     *
     * @return returns the last char the object passed over
     */
    public char getLastChar(){
        return this.lastChar;
    }
    
    /**
     *
     */
    public void waitTicker() {
        this.waitTicker++;
    }
    
    /**
     *
     * @return returns a true if wait time is over, else false
     */
    public boolean waitedLongEnough(){
        if (this.waitTicker >= 14){
            this.waitTicker = 0;
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public void setLevelPosition(int x, int y){
        // Here we handle where the Sprite is on the level char[][]
            this.levelPosX = x;
            this.levelPosY = y;
    }
    
    /**
     *
     * @return returns int value of current level x position of sprite
     */
    public int getLevelPositionX(){
        return this.levelPosX;
    }
    
    /**
     *
     * @return returns int value of current level y position of sprite
     */
    public int getLevelPositionY(){
        return this.levelPosY;
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public void setPosition(double x, double y){
        // Here we handle the graphical position of the gif.
        this.posX = x;
        this.posY = y;
    }
    
    /**
     *
     * @return returns double grahpical y pos
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
     * @return returns image as ImageView 
     */
    public ImageView getGraphics(){
        return this.baseGraphic;
    }
    
    /**
     *
     * @param image
     */
    public void setGraphics(ImageView image){
        this.baseGraphic = image;
    }
    
}
