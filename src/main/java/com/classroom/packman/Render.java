package com.classroom.packman;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Michael og Cato
 */
public class Render extends Pane {
    // Handle all rendering here.

    Loader load = new Loader();
    private char[][] currentLevel = load.getFirstLevel();
    private ArrayList<Circle> points = new ArrayList<>();
    private ArrayList<Rectangle> walls = new ArrayList<>();
    private ArrayList<Circle> powerups = new ArrayList<>();
    private Blinky blinky;
    private Player pacman;
    /* The under are not private as we use them as globals */
    double sectionWide, sectionHigh;
    double HEIGHT, WIDTH;

    public void draw(int height, int width) {
        // drawing the map and sprites
        /* 
        *   Load char levels
        *   Use Circles for points and rectangular shapes for walls and ghosts and pacmans.
         */
        this.HEIGHT = (double) height;
        this.WIDTH = (double) width;
        double sectionWide = (WIDTH / currentLevel[0].length);
        this.sectionWide = sectionWide;
        double sectionHigh = (HEIGHT / currentLevel.length);
        this.sectionHigh = sectionHigh;
        for (int i = 0; i < currentLevel.length; i++) {
            
            for (int j = 0; j < currentLevel[i].length; j++) {

                double posx = (sectionWide / 2) + (sectionWide * j);

                double posy = (sectionHigh / 2) + (sectionHigh * i);

                switch (currentLevel[i][j]) {
                    case 'X':
                        walls.add(new Rectangle((j * sectionWide), (i * sectionHigh), sectionWide, sectionHigh));
                        walls.get(walls.size() - 1).setFill(Color.GREEN);
                        break;
                    case 'O':
                        // blanks
                        break;
                    case 'P':
                        points.add(new Circle(posx, posy, 2));
                        points.get(points.size() - 1).setFill(Color.WHITE);
                        break;
                    case '-':
                        powerups.add(new Circle(posx, posy, 5));
                        powerups.get(powerups.size() - 1).setFill(Color.TEAL);
                        break;
                    case '1':
                        blinky = new Blinky(sectionHigh, sectionWide, (j * sectionWide), (i * sectionHigh));
                        //blinky.setLevelPosition(j, i);
                        break;
                    case 'S':
                        pacman = new Player("Player1", sectionHigh, sectionWide, (j * sectionWide), (i * sectionHigh));
                        //pacman.setLevelPosition(j, i);
                    default:
                        break;
                }
            }
        }
        this.getChildren().clear();
        this.getChildren().addAll(walls);
        this.getChildren().addAll(points);
        this.getChildren().addAll(powerups);
        this.getChildren().addAll(blinky.getGraphics());
        this.getChildren().addAll(pacman.getGraphics(0));
    }
    
    public Player getPlayer(){
        return this.pacman;
    }
    
    public void render(){
        this.getChildren().clear();
    }
}
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
