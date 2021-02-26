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
               super.setGraphics(redImageView);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        
    }
}
