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
 * @author ylvar
 */
public class Player extends Sprites {

    private int points, lives, activeDirection;
    String name;
    private ImageView[] baseGraphic = new ImageView[4];
    double width, height, posX, posY;
    boolean powered = false;
    

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

    }
    
    public int getLives(){
        return this.lives;
    }
    
    public void removeLife(){
        if (this.lives <= 1){
            //game over
        } else {
            this.lives--;
        }
    }
    
    public void updatePoints(){
        this.points++;
    }
    
    public int getPoints(){
        return this.points;
    }
    
    public void powerup(){
        this.powered = true;
    }
    
    public boolean checkPower(){
        return this.powered;
    }
    
    public ImageView getGraphics(){
        this.baseGraphic[activeDirection].setFitHeight(this.height);
        this.baseGraphic[activeDirection].setFitWidth(this.width);
        return this.baseGraphic[activeDirection];
    }
    
    public void setDirection(int i){
        this.activeDirection = i;
    }
    
    public void setGraphics(int direction, ImageView iv) {
        try {
            this.baseGraphic[direction] = iv;
        } catch (IndexOutOfBoundsException e){
            System.out.println("index out of bound");
        }
        
    }
    
    public void movePacman(double newPosX, double newPosY){
        ImageView pacman = this.getGraphics();
        pacman.setTranslateX(newPosX);
        pacman.setTranslateY(newPosY);
        this.setGraphics(activeDirection, pacman);
    }

}
