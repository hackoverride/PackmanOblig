/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classroom.packman;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
        3 ghost Pinky
        2 ghost Inky 
        4 ghost Clyde
        1 ghost Blinky
    */
    
    /**
     *
     */
    public void writeFirstLevel(){
        // we just used this to create the first template.
        try {
            File firstLevel = new File("first.txt");
            PrintWriter print = new PrintWriter(firstLevel);
            for (int i = 0; i < 31; i++){
                for (int j = 0; j < 28; j++){
                    if (j == 27){
                        print.println('X');
                    } else {
                        print.print('X');
                    }
                    
                }
            }
            print.close();
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
    /**
     *
     * @return returns a char array of the first level
     * 
     */
    public char[][] getFirstLevel(){
        char[][] level = new char[31][28];
        int pos = 0;
        try {
            File firstLevel = new File("first.txt");
            if (firstLevel.exists()){
                System.out.println("File ok");
            }
            Scanner les = new Scanner(firstLevel);
            while(les.hasNextLine()){
                String line = les.nextLine();
                level[pos++] = line.toCharArray();
            }
            les.close();
        } catch (Exception e){
            System.out.println(e);
        }
        
        /*
        for (int i = 0; i < level.length; i++){
            for (int j = 0; j < level[i].length; j++){
                System.out.print(level[i][j]);
            }
            System.out.println("");
        }
        */
        
        return level;
    }
    
    /**
     *
     * @return returns a char array of the second level
     */
    public char[][] getSecondLevel(){
        char[][] level = new char[31][28];
        int pos = 0;
        try {
            File secondLevel = new File("second.txt");
            if (secondLevel.exists()){
                System.out.println("File ok");
            }
            Scanner les = new Scanner(secondLevel);
            while(les.hasNextLine()){
                String line = les.nextLine();
                level[pos++] = line.toCharArray();
            }
            les.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return level;
    }
    
}
