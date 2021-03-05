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
    // hold onto the last five moves.
    private int[][] lastMoves = new int[5][2];
    private int levelPosX, levelPosY;
    private String id;
    // Setting up the array for the four directions of each characters.
    private ImageView baseGraphic;
    private double size = 10.0;
    // for ghost to wait before leaving 'safezone'
    private int waitTicker = 0;
    
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
    
    public int[][] getLastMoves() {
        return this.lastMoves;
    }
    
    public void waitTicker() {
        this.waitTicker++;
    }
    
    public boolean waitedLongEnough(){
        if (this.waitTicker >= 10){
            this.waitTicker = 0;
            return true;
        }
        return false;
    }
    
    public void setLastMove(int x, int y){
        // update the lastMoves array.
        int[][] temp = new int[5][2];
        for (int i = 0; i < 4; i++){
            temp[i][0] = this.lastMoves[i+1][0];
            temp[i][1] = this.lastMoves[i+1][1];
        }
            temp[4][0] = x;
            temp[4][1] = y;
            
            this.lastMoves = temp;
    }
    
    public void setLevelPosition(int x, int y){
        // Here we handle where the Sprite is on the level char[][]
            this.levelPosX = x;
            this.levelPosY = y;
    }
    
    public int getLevelPositionX(){
        return this.levelPosX;
    }
    
    public int getLevelPositionY(){
        return this.levelPosY;
    }
    
    public void setPosition(double x, double y){
        // Here we handle the graphical position of the gif.
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
