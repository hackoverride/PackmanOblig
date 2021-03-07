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
public class Blinky extends Sprites{
    
    boolean returnHome = false;
    
    /**
     *
     * @param height
     * @param width
     * @param newPosX Initial x pos for blinky gif
     * @param newPosY Initial y pos for blinky gif
     */
    public Blinky(double height, double width, double newPosX, double newPosY) {
        try{
            File redGhost = new File("redghost.gif");
            if (redGhost.exists()){
               Image redImage = new Image(redGhost.toURI().toString());
               ImageView redImageView = new ImageView(redImage);
               redImageView.setFitHeight(height);
               redImageView.setFitWidth(width);
               redImageView.setTranslateX(newPosX);
               redImageView.setTranslateY(newPosY);
               this.setGraphics(redImageView);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
   
    public void returnHome(){
        this.returnHome = true;
    }
    
   
    public void readyForAction(){
        this.returnHome = false;
    }
    
    /**
     *
     * @return true if returning home otherwise false
     */
    public boolean isReturningHome(){
        return this.returnHome;
    }
    
    /**
     *
     * @param newPosX New x posision for blinky gif
     * @param newPosY New y posision for blinky gif
     */
    public void moveBlinky(double newPosX, double newPosY){
        ImageView redImageView = this.getGraphics();
        redImageView.setTranslateX(newPosX);
        redImageView.setTranslateY(newPosY);
        this.setGraphics(redImageView);
    }
}
