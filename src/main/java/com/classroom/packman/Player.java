/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classroom.packman;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Michael og Cato
 */
public class Player extends Sprites {

    private int points, lives, activeDirection;
    private String name;
    private ImageView[] baseGraphic = new ImageView[4];
    private double width, height, posX, posY;
    private boolean powered = false;
    private int poweredTicker = 50;
    
    /**
     *
     * @param name
     * @param height
     * @param width
     * @param newPosX Initial x pos for pacman gif
     * @param newPosY Initial y pos for pacman gif
     */
    public Player(String name, double height, double width, double newPosX, double newPosY) {
        super();
        this.name = name;
        this.points = 0;
        this.lives = 3;
        this.width = width;
        this.height = height;
        this.posX = newPosX;
        this.posY = newPosY;
        this.activeDirection = 0; // Always start heading left.
        try {
            File packmanLeft = new File("pacmanLeft.gif");
            File packmanRight = new File("pacmanRight.gif");
            File packmanUp = new File("pacmanUp.gif");
            File packmanDown = new File("pacmanDown.gif");
            if (packmanLeft.exists()){
               Image packmanImageLeft = new Image(packmanLeft.toURI().toString());
               this.baseGraphic[0] = new ImageView(packmanImageLeft);
            }
            if (packmanRight.exists()){
               Image packmanImageRight = new Image(packmanRight.toURI().toString());
               this.baseGraphic[1] = new ImageView(packmanImageRight);
            }
            if (packmanUp.exists()){
               Image packmanImageUp = new Image(packmanUp.toURI().toString());
               this.baseGraphic[2] = new ImageView(packmanImageUp);
            }
            if (packmanDown.exists()){
               Image packmanImageDown = new Image(packmanDown.toURI().toString());
               this.baseGraphic[3] = new ImageView(packmanImageDown);
            }
        } catch (Exception E) {
            System.out.println(E);
        }
        
        movePacman(posX, posY);

    }
    
    /**
     *
     * @return returns number 0-3 indicating direction
     */
    public int getActiveDirection(){
        return this.activeDirection;
    }
    
    /**
     *
     * @return returns an int indicating remaining lives
     */
    public int getLives(){
        return this.lives;
    }
    
    
    public void removeLife(){
        this.lives--;
    }
    
    
    public void updatePoints(){
        this.points++;
    }
    
    /**
     *
     * @return returns current point value
     */
    public int getPoints(){
        return this.points;
    }
    
   
    public void powerup(){
        this.powered = true;
    }
    
    /**
     *
     * @return returns true if actice powerup else false
     */
    public boolean checkPower(){
        return this.powered;
    }
    
    /**
     *
     * @return returns a ImageView[] of direction.
     */
    public ImageView getGraphics(){
        this.baseGraphic[activeDirection].setFitHeight(this.height);
        this.baseGraphic[activeDirection].setFitWidth(this.width);
        return this.baseGraphic[activeDirection];
    }
    
    /**
     *
     * @param direction
     */
    public void setDirection(int direction){
        this.activeDirection = direction;
    }
    
    /**
     *
     * @param direction
     * @param imageView
     */
    public void setGraphics(int direction, ImageView imageView) {
        try {
            this.baseGraphic[direction] = imageView;
        } catch (IndexOutOfBoundsException e){
            System.out.println("index out of bound");
        }
        
    }
    
    /**
     *
     * @param newPosX New x posision for packman gif 
     * @param newPosY New y posision for packman gif
     */
    public void movePacman(double newPosX, double newPosY){
        ImageView pacman = this.getGraphics();
        pacman.setTranslateX(newPosX);
        pacman.setTranslateY(newPosY);
        this.setGraphics(activeDirection, pacman);
    }

}
