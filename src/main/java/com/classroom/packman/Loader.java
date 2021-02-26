/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classroom.packman;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Michael og Cato
 */
public class Loader {
    // Laster txt filer for levels.
    
    private char[] levelBuilder = {'X', 'O', 'P', '-', 'E', 'S', '1', '2', '3', '4'};
    /*
    *   Eksempel level:
        X vegg
        O blank
        P prikk
        - powerup
        E exit
        S packman start
        1 ghost Pinky
        2 ghost Inky 
        3 ghost Clyde
        4 ghost Blinky
    */
    
    public void writeFirstLevel(){
        try {
            File firstLevel = new File("first.txt");
            PrintWriter print = new PrintWriter(firstLevel);
            print.print("Hello!");
            print.close();
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
