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
 * @author Michael and Cato
 */
public class Clyde extends Sprites{
    
    private boolean returnHome = false;
    
    /**
     *
     * @param height
     * @param width
     * @param newPosX initial x pos for Clyde gif
     * @param newPosY initial y pos for Clyde gif
     */
    public Clyde(double height, double width, double newPosX, double newPosY) {
        try{
            File clydeFile = new File("ghost2.gif");
            if (clydeFile.exists()){
               Image clydeImage = new Image(clydeFile.toURI().toString());
               ImageView clydeImageView = new ImageView(clydeImage);
               clydeImageView.setFitHeight(height);
               clydeImageView.setFitWidth(width);
               clydeImageView.setTranslateX(newPosX);
               clydeImageView.setTranslateY(newPosY);
               this.setGraphics(clydeImageView);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
   
    public void returnHome(){
        this.returnHome = true;
    }
    
    /**
     *
     * @return is true if return home else false
     */
    public boolean isReturningHome(){
        return this.returnHome;
    }
    
    
    public void readyForAction(){
        this.returnHome = false;
    }
    
    /**
     *
     * @param newPosX New x posision for clyde gif
     * @param newPosY New y posision for clyde gif
     */
    public void moveClyde(double newPosX, double newPosY){
        ImageView clydeImageView = this.getGraphics();
        clydeImageView.setTranslateX(newPosX);
        clydeImageView.setTranslateY(newPosY);
        this.setGraphics(clydeImageView);
    }
}
