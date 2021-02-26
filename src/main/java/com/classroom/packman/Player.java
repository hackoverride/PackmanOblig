/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classroom.packman;

/**
 *
 * @author ylvar
 */
public class Player extends Sprites{
    private int points, lives;
    String name;
    
    public Player(String name) {
        super();
        this.name = name;
        this.points = 0;
        this.lives = 3;
    }
    
}
