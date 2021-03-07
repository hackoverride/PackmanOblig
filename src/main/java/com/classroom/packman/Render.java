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

    /**
     *
     * @param height
     * @param width
     */
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
                        blinky.setStartPosition(i, j);
                        blinky.returnHome();
                        break;
                    case 'S':
                        pacman = new Player("Player1", sectionHigh, sectionWide, (j * sectionWide), (i * sectionHigh));
                        pacman.setLevelPosition(j, i);
                        pacman.setStartPosition(i, j);
                        break;
                    //pacman.setLevelPosition(j, i);
                    case '4':
                        clyde = new Clyde(sectionHigh, sectionWide, (j * sectionWide), (i * sectionHigh));
                        clyde.setLevelPosition(j, i);
                        clyde.setStartPosition(i, j);
                        clyde.returnHome();
                        break;
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

    /**
     *
     * @return returns the player object
     */
    public Player getPlayer() {
        return this.pacman;
    }

    /**
     * Controlls the ticker, and also the movment 
     * of ghosts and the movment direction of packman
     */
    public void ghostLogic() {
        // Blinky!
        this.ticker++;
        // dont move ghosts too quickly, but dont lag the render either.

        if (ticker > 3) {

            if (clyde.isReturningHome()) {
                // return clyde to homebase... And check wait-time.
                if (clyde.waitedLongEnough()) {
                    clyde.readyForAction();
                } else {
                    clyde.waitTicker();
                }

            }
            if (blinky.isReturningHome()) {
                if (blinky.waitedLongEnough()) {
                    blinky.readyForAction();
                } else {
                    blinky.waitTicker();
                }
            }

            this.ticker = 0;
            // we always move player first.
            movePlayer(pacman.getActiveDirection());
            int tempX = blinky.getLevelPositionX();
            int tempY = blinky.getLevelPositionY();

            // blinky needs to move towards pacman but default would be up first.
            int[] bestPath = getBestMove(blinky);
            tempX = bestPath[1];
            tempY = bestPath[0];
            // Limit to no diagonal movement.
            if (blinky.isReturningHome()) {
                // blinky does not move.
                int[] pos = blinky.getStartPosition();
                removeCharFromLevel('1', blinky.getLastChar());
                blinky.setLevelPosition(pos[0], pos[1]);
                currentLevel[pos[1]][pos[0]] = '1';
            } else {
                removeCharFromLevel('1', blinky.getLastChar());
                blinky.setLevelPosition(tempX, tempY);
                blinky.setLastChar(currentLevel[tempY][tempX]);
                currentLevel[tempY][tempX] = '1';
            }

            /* CLYDE */
            tempX = clyde.getLevelPositionX();
            tempY = clyde.getLevelPositionY();

            // Acconding to wiki clyde chase exactly like blinky, but retreats
            // when getting close. towards high Y and low X values.
            int distanceX = Math.abs(clyde.getLevelPositionX() - pacman.getLevelPositionX());
            int distanceY = Math.abs(clyde.getLevelPositionY() - pacman.getLevelPositionY());
            if (distanceX < 4 || distanceY < 4) {
                if (currentLevel[tempY + 1][tempX] == 'X') {
                    if (currentLevel[tempY][tempX - 1] == 'X') {
                        if (currentLevel[tempY][tempX + 1] == 'X') {
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
            if (clyde.isReturningHome()) {
                // clyde dont move!
                int[] pos = clyde.getStartPosition();
                tempX = pos[0];
                tempY = pos[1];
            }
            removeCharFromLevel('4', clyde.getLastChar());
            clyde.setLevelPosition(tempX, tempY);
            clyde.setLastChar(currentLevel[tempY][tempX]);
            currentLevel[tempY][tempX] = '4';

            /*
            Now if a ghost has "eaten" pacman then remove life.
            
             */
            if (clyde.getLastChar() == 'S' || blinky.getLastChar() == 'S') {
                if (!pacman.checkPower()) {
                    boolean pacAlive = false;
                    for (int i = 0; i < currentLevel.length; i++) {
                        for (int j = 0; j < currentLevel[i].length; j++) {
                            if (currentLevel[i][j] == 'S') {
                                pacAlive = true;
                                continue;
                            }
                        }
                    }
                    if (!pacAlive) {
                        pacman.removeLife();
                        int[] pacPos = pacman.getStartPosition();
                        pacman.setLevelPosition(pacPos[0], pacPos[1]);
                        currentLevel[pacPos[0]][pacPos[1]] = 'S';

                        if (clyde.getLastChar() == 'S') {
                            clyde.setLastChar('O');
                        }
                        if (blinky.getLastChar() == 'S') {
                            blinky.setLastChar('O');
                        }
                        blinky.returnHome();
                        clyde.returnHome();
                    }
                }
            }
        }
    }

    /**
     *
     * @param direction sets player direction 
     * 0 = left, 1 = right, 2 = up, 3 = down
     */
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
                break;
            case 1:
                // moving right
                if (currentLevel[tempY][tempX + 1] != 'X') {
                    tempX++;
                }
                break;
            case 2:
                // moving up
                if (currentLevel[tempY - 1][tempX] != 'X') {
                    tempY--;
                }
                break;
            case 3:
                // moving down
                if (currentLevel[tempY + 1][tempX] != 'X') {
                    tempY++;
                }
                break;
            default:
                break;
        }
        char currentValue = currentLevel[tempY][tempX];
        int ascii = (int) currentValue;
        System.out.println(ascii);
        if (currentValue == 'P') {
            pacman.updatePoints();
        } else if (currentValue == '-') {
            pacman.powerup();
        } else if (currentValue == 'E') {

            // first check if we level up
            if (this.currentLevelMaxPoints == pacman.getPoints()) {
                this.currentLevel = load.getSecondLevel();

            }
            if (tempX <= 0) {
                tempX = currentLevel[0].length - 2;
            } else if (tempX >= currentLevel[0].length - 2) {
                tempX = 1;
            }
        } else if (ascii >= 49 && ascii <= 52) {
            // pacman is at same position as ghosts
            if (pacman.checkPower()) {
                System.out.println("ghost is dead");

                switch (ascii) {
                    case 49:
                        blinky.returnHome();
                        break;
                    case 50:
                        break;
                    case 51:
                        break;
                    case 52:
                        clyde.returnHome();
                        break;
                    default:
                        break;
                }

            } else {
                pacman.removeLife();
                // Level reset position is always: X: 12 Y: 10
                int[] start = pacman.getStartPosition();
                tempX = start[1];
                tempY = start[0];
                blinky.returnHome();
                clyde.returnHome();
            }
        }

        currentLevel[tempY][tempX] = 'S';
        pacman.setLevelPosition(tempX, tempY);
    }

    /**
     *
     * @param remove char to remove
     * @param add char to add in place
     */
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

    /**
     * Method for drawing the graphics based on current active level
     */
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
                        break;
                    case 'S':
                        pacman.movePacman((j * sectionWide), (i * sectionHigh));
                        break;
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
                    // dont concider walls for best moves.
                } else {
                    // ghosts can only move right, left, up or down. not diagonally.
                    
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
        int highestValue = 0;
        // find the closes position to blinky with the lowest total score.
        for (int i = 0; i < 3; i++) {
            int posY = blinkyPos[0] - 1 + i;
            for (int j = 0; j < 3; j++) {
                int posX = blinkyPos[1] - 1 + j;
                // best move if pacman is powerd is to run away from pacman!
                if (pacman.checkPower()) {
                    if (isWall(posY, posX) || i == 1 && j == 1 || currentLevel[posY][posX] == '2' || currentLevel[posY][posX] == '3' || currentLevel[posY][posX] == '4') {
                        // score does not count. therefore ignored.
                        // Clyde triggers on all accounts at start.

                        if (j == 2 && i == 2 && highestValue == 0) {
                            bestPath[1] = blinkyPos[1] - 1;
                            bestPath[0] = blinkyPos[0] - 1;
                        }

                    } else {
                        //allocating the sum of nodeValues
                        if (highestValue < (nodeValuesPac[posY][posX] + nodeValuesBlink[posY][posX])) {
                            highestValue = (nodeValuesPac[posY][posX] + nodeValuesBlink[posY][posX]);
                            bestPath[0] = posY;
                            bestPath[1] = posX;
                        }
                    }
                } else {
                    if (isWall(posY, posX) || i == 1 && j == 1 || currentLevel[posY][posX] == '2' || currentLevel[posY][posX] == '3' || currentLevel[posY][posX] == '4') {
                        // score does not count. therefore ignored.
                        // Clyde triggers on all accounts at start.

                        if (j == 2 && i == 2 && lowestValue == 10000) {
                            bestPath[1] = blinkyPos[1] - 1;
                            bestPath[0] = blinkyPos[0] - 1;
                        }

                    } else {
                        //allocating the sum of nodeValues
                        if (currentLevel[posY][posX] == 'S') {
                            // pacman is there so it would be best to kill player now!
                            bestPath[0] = posY;
                            bestPath[1] = posX;
                        } else {
                            if (lowestValue > (nodeValuesPac[posY][posX] + nodeValuesBlink[posY][posX])) {
                                lowestValue = (nodeValuesPac[posY][posX] + nodeValuesBlink[posY][posX]);
                                bestPath[0] = posY;
                                bestPath[1] = posX;
                            }
                        }

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
