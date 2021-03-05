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
    private Clyde clyde;
    /* The under are not private as we use them as globals */
    double sectionWide, sectionHigh;
    double HEIGHT, WIDTH;
    private int currentLevelMaxPoints = 0;

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
        int maxPoints = 0;
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
                        maxPoints++;
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
                        break;
                    //pacman.setLevelPosition(j, i);
                    case '4':
                        clyde = new Clyde(sectionHigh, sectionWide, (j * sectionWide), (i * sectionHigh));
                        clyde.setLevelPosition(j, i);
                    default:
                        break;
                }
            }
        }
        this.currentLevelMaxPoints = maxPoints;
        this.getChildren().clear();
        this.getChildren().addAll(walls);
        this.getChildren().addAll(points);
        this.getChildren().addAll(powerups);
        this.getChildren().addAll(blinky.getGraphics(), clyde.getGraphics());
        this.getChildren().addAll(pacman.getGraphics());
    }

    public Player getPlayer() {
        return this.pacman;
    }

    public void ghostLogic() {
        // Blinky!
        this.ticker++;
        // dont move ghosts too quickly, but dont lag the render either.
        if (ticker > 3) {
            this.ticker = 0;
            // we always move player first.
            movePlayer(pacman.getActiveDirection());
            removeCharFromLevel('1', blinky.getLastChar());
            int tempX = blinky.getLevelPositionX();
            int tempY = blinky.getLevelPositionY();

            // blinky needs to move towards pacman but default would be up first.
            int[] bestPath = getBestMove(blinky);
            tempX = bestPath[1];
            tempY = bestPath[0];
            blinky.setLevelPosition(tempX, tempY);
            currentLevel[tempY][tempX] = '1';
            
            /* CLYDE */
            tempX = clyde.getLevelPositionX();
            tempY = clyde.getLevelPositionY();
            
            // Acconding to wiki clyde chase exactly like blinky, but retreats
            // when getting close. towards high Y and low X values.
            int distanceX = Math.abs(clyde.getLevelPositionX() - pacman.getLevelPositionX());
            int distanceY = Math.abs(clyde.getLevelPositionY() - pacman.getLevelPositionY());
            if (distanceX < 4 || distanceY < 4){
                if (currentLevel[tempY + 1][tempX] == 'X'){
                    if (currentLevel[tempY][tempX - 1] == 'X') {
                        if (currentLevel[tempY][tempX + 1] == 'X'){
                            tempY--;
                        } else {
                            tempX++;
                        }
                    } else {
                        tempX--;
                    }
                } else {
                    tempY++;
                }
            } else {
                bestPath = getBestMove(clyde);
                tempX = bestPath[1];
                tempY = bestPath[0];
            }
            removeCharFromLevel('4', clyde.getLastChar());
            clyde.setLevelPosition(tempX, tempY);
           currentLevel[tempY][tempX] = '4';
            
            
        }
    }

    public void movePlayer(int direction) {
        // 0 = left, 1 = right, 2 = up, 3 = down
        // player = 'S'.
        char replaceValue = 'O';
        if (pacman.getLastChar() == 'E') {
            replaceValue = 'E';
        }
        removeCharFromLevel('S', replaceValue);
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
        if (currentValue == 'E') {

            // first check if we level up
            if (this.currentLevelMaxPoints == pacman.getPoints()) {
                this.currentLevel = load.getSecondLevel();

            }
            if (tempX <= 0) {
                tempX = currentLevel[0].length - 2;
            } else if (tempX >= currentLevel[0].length - 2) {
                tempX = 1;
            }
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
                    case '4':
                        clyde.moveClyde((j * sectionWide), (i * sectionHigh));
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
        this.getChildren().addAll(blinky.getGraphics(), clyde.getGraphics());
        this.getChildren().addAll(pacman.getGraphics());
    }

    private int[] getBestMove(Sprites s) {
        // calculate the "best" move for a ghost.
        // loosly based on an a* Algorithm ;-) we found inspiration to on Youtube.
        /* https://www.youtube.com/watch?v=-L-WgKMFuhE&t=583s */
        int[] values = {0, 0};
        int[] pacPos = {pacman.getLevelPositionY(), pacman.getLevelPositionX()};
        int[] blinkyPos = {s.getLevelPositionY(), s.getLevelPositionX()};

        // because we know where blinky is and where pacman is we can search for open areas towards pacman...
        int[][] nodeValuesPac = new int[currentLevel.length][currentLevel[0].length];
        int[][] nodeValuesBlink = new int[currentLevel.length][currentLevel[0].length];

        for (int i = 0; i < currentLevel.length; i++) {
            for (int j = 0; j < currentLevel[i].length; j++) {
                if (currentLevel[i][j] == 'X') {

                } else {
                    int posAwayFromPacY = Math.abs(pacPos[0] - i);
                    int posAwayFromPacX = Math.abs(pacPos[1] - j);
                    nodeValuesPac[i][j] = (posAwayFromPacY + posAwayFromPacX);

                    int posAwayFromBlinkyY = Math.abs(blinkyPos[0] - i);
                    int posAwayFromBlinkyX = Math.abs(blinkyPos[1] - j);
                    nodeValuesBlink[i][j] = (posAwayFromBlinkyY + posAwayFromBlinkyX);
                }

            }
        }
        int[] bestPath = new int[2];
        int lowestValue = 10000;
        // find the closes position to blinky with the lowest total score.
        for (int i = 0; i < 3; i++) {
            int posY = blinkyPos[0] - 1 + i;
            for (int j = 0; j < 3; j++) {
                int posX = blinkyPos[1] - 1 + j;
                if ( isWall( posY,posX ) || i == 1 && j == 1 || currentLevel[posY][posX] == '2' || currentLevel[posY][posX] == '3' || currentLevel[posY][posX] == '4') {
                    // score does not count. therefore ignored.
                    // Clyde triggers on all accounts at start.
                    if (j == 2 && i == 2 && lowestValue == 10000){
                        bestPath[1] = blinkyPos[1] - 1;
                        bestPath[0] = blinkyPos[0] - 1;
                    }

                } else {
                    //allocating the sum of nodeValues
                    if (lowestValue > (nodeValuesPac[posY][posX] + nodeValuesBlink[posY][posX])) {
                        lowestValue = (nodeValuesPac[posY][posX] + nodeValuesBlink[posY][posX]);
                        bestPath[0] = posY;
                        bestPath[1] = posX;
                    }
                }
            }
        }
        return bestPath;

    }

    private boolean isWall(int i, int j) {
        if (currentLevel[i][j] == 'X') {
            return true;
        } else if (currentLevel[i][j] == 'E') {
            return true;
        }
        return false;
    }
}
