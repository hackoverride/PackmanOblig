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
    private int ticker = 0;
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
                        blinky.setLevelPosition(j, i);
                        break;
                    case 'S':
                        pacman = new Player("Player1", sectionHigh, sectionWide, (j * sectionWide), (i * sectionHigh));
                        pacman.setLevelPosition(j, i);
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
        this.getChildren().addAll(pacman.getGraphics());
    }

    public Player getPlayer() {
        return this.pacman;
    }

    public void ghostLogic() {
        // Blinky!
        this.ticker++;
        if (ticker > 4) {
            this.ticker = 0;
            removeCharFromLevel('1', 'O');
            int pacX = pacman.getLevelPositionX();
            int pacY = pacman.getLevelPositionY();
            int tempX = blinky.getLevelPositionX();
            int tempY = blinky.getLevelPositionY();

            //checkin the last five moves
            int[][] lastMoves = blinky.getLastMoves();
            blinky.setLastMove(tempX, tempY);

            // blinky needs to move towards pacman but default would be up first.
            if (tempX == 12 && tempY == 11) {
                // were at start. move up!
                tempY--;
            } else if (tempX <= 13 && tempY == 12 || tempX >= 11 && tempY == 12) {
                // ghost is inside the safezone.
                // add a wait?
                if (blinky.waitedLongEnough()) {
                    tempX = 12;
                    tempY = 11;
                } else {
                    blinky.waitTicker();
                }
            } else {
                // main logic.
                if (pacY > tempY) {
                    // pacman is down
                    if (currentLevel[tempY + 1][tempX] != 'X') {
                        if (lastMoves[4][0] != tempY + 1 && lastMoves[4][1] != tempX) {
                            // dont want to go back where we were?
                            tempY++;
                        } else {
                            if (currentLevel[tempY][tempX - 1] != 'X') {
                                if (currentLevel[tempY][tempX - 1] != 'E') {
                                    tempX--;
                                } else {
                                    // no wall but exit - move other direction.
                                    tempX++;
                                }
                            }
                        }
                    } else {
                        // Pacman is downwards but wall is down aswell..
                        if (pacX > tempX) {
                            if (currentLevel[tempY][tempX + 1] != 'X') {
                                if ((currentLevel[tempY][tempX + 1] != 'E')) {
                                    tempX++;
                                }
                            } else {
                                // pacman is up, but the wall is there and the wall is to the right.
                                if (currentLevel[tempY][tempX - 1] != 'X') {
                                    if ((currentLevel[tempY][tempX - 1] != 'E')) {
                                        tempX--;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // pacman would be the same Y level or lower down on board.
                    if (pacY == tempY) {
                        if (pacX > tempX) {
                            //same Y axis but to the right.
                            if (currentLevel[tempY][tempX + 1] != 'X') {
                                if (currentLevel[tempY][tempX + 1] != 'E') {
                                    tempX++;
                                } else {
                                    tempX = lastMoves[4][1];
                                }
                            } else {
                                // pacman is on the same Y axis and towards the right
                                // but there is a wall to the right.
                                if (currentLevel[tempY + 1][tempX] != 'X') {
                                    tempY++;
                                } else if (currentLevel[tempY - 1][tempX] != 'X') {
                                    tempY--;
                                }
                            }
                        }
                    } else {
                        // the pac is lower!
                        if (currentLevel[tempY + 1][tempX] != 'X') {
                            if (lastMoves[4][0] != tempY + 1 && lastMoves[4][1] != tempX){
                                tempY++;
                            } else {
                                if (pacX < tempX){
                                    if (currentLevel[tempY][tempX -1] != 'X'){
                                        if (currentLevel[tempY][tempX -1] != 'E'){
                                        tempX--;
                                    } else {
                                            tempX++;
                                        }
                                    } else {
                                        if (currentLevel[tempY][tempX + 1] != 'X'){
                                            if (currentLevel[tempY][tempX + 1] != 'E'){
                                        tempX++;
                                    }
                                        
                                    }
                                    }
                                }
                            }
                            
                        } else {
                            //pacman is lower on the board but wall is down
                            if (pacX > tempX) {
                                if (currentLevel[tempY][tempX + 1] != 'X') {
                                    if (currentLevel[tempY][tempX + 1] != 'E') {
                                        tempX++;

                                    }
                                }

                            } else {
                                if (currentLevel[tempY][tempX - 1] != 'X') {
                                    if (currentLevel[tempY][tempX - 1] != 'E') {
                                        tempX--;
                                    }
                                }

                            }

                        }
                    }
                }
            }

            System.out.println(tempX);
            System.out.println(tempY);

            blinky.setLevelPosition(tempX, tempY);
            currentLevel[tempY][tempX] = '1';
            //movePlayer(pacman.getActiveDirection());

        }
    }

    public void movePlayer(int direction) {
        // 0 = left, 1 = right, 2 = up, 3 = down
        // player = 'S'.
        removeCharFromLevel('S', 'O');
        int tempX = pacman.getLevelPositionX();
        int tempY = pacman.getLevelPositionY();

        switch (direction) {
            case 0:
                // moving left
                if (currentLevel[tempY][tempX - 1] != 'X') {
                    tempX--;
                }
                pacman.setDirection(0);
                break;
            case 1:
                // moving right
                if (currentLevel[tempY][tempX + 1] != 'X') {
                    tempX++;
                }
                pacman.setDirection(1);
                break;
            case 2:
                // moving up
                if (currentLevel[tempY - 1][tempX] != 'X') {
                    tempY--;
                }
                pacman.setDirection(2);
                break;
            case 3:
                // moving down
                if (currentLevel[tempY + 1][tempX] != 'X') {
                    tempY++;
                }
                pacman.setDirection(3);
                break;
            default:
                break;
        }
        char currentValue = currentLevel[tempY][tempX];
        if (currentValue == 'P') {
            pacman.updatePoints();
        }
        if (currentValue == '-') {
            pacman.powerup();
        }
        currentLevel[tempY][tempX] = 'S';
        pacman.setLevelPosition(tempX, tempY);
    }

    public void removeCharFromLevel(char remove, char add) {
        for (int i = 0; i < currentLevel.length; i++) {
            for (int j = 0; j < currentLevel[i].length; j++) {
                if (currentLevel[i][j] == remove) {
                    // blank out
                    currentLevel[i][j] = add;
                }
            }
        }
    }

    public void render() {

        // adding ghosts, points, powerups and pacman
        // Walls and spaces are not going anywere
        points.clear();
        powerups.clear();
        for (int i = 0; i < currentLevel.length; i++) {

            for (int j = 0; j < currentLevel[i].length; j++) {

                double posx = (sectionWide / 2) + (sectionWide * j);

                double posy = (sectionHigh / 2) + (sectionHigh * i);

                switch (currentLevel[i][j]) {
                    case 'P':
                        points.add(new Circle(posx, posy, 2));
                        points.get(points.size() - 1).setFill(Color.WHITE);
                        break;
                    case '-':
                        powerups.add(new Circle(posx, posy, 5));
                        powerups.get(powerups.size() - 1).setFill(Color.TEAL);
                        break;
                    case '1':
                        blinky.moveBlinky((j * sectionWide), (i * sectionHigh));
                        //blinky.setLevelPosition(j, i);
                        break;
                    case 'S':
                        pacman.movePacman((j * sectionWide), (i * sectionHigh));
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
        this.getChildren().addAll(pacman.getGraphics());
    }
}
