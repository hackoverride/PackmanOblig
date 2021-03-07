package com.classroom.packman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * JavaFX App oblig - Packman :)
 * Laget av Cato og Michael
 * 
 * Written comments and the program in English as some compilers 
 * can have issues with some specialcharacters.
 */

public class App extends Application {
    
    final int HEIGHT = 900;
    final int WIDTH = HEIGHT;
    Scene scene, gameOver;
    
    /**
     * 
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        BorderPane mainWindow = new BorderPane();
        mainWindow.setStyle("-fx-background-color: #000;");
        // setting the mainWindow to the scene first.
        HBox menu_container = new HBox(30);
        Label score = new Label("Score: ");
        score.setStyle("-fx-text-fill: #FFF; -fx-font-size: 16px;");
        Label lives = new Label("Lives: ");
        lives.setStyle("-fx-text-fill: #FFF; -fx-font-size: 16px;");
        menu_container.getChildren().addAll(score, lives);
        mainWindow.setTop(menu_container);
        
        // testing out images
        //Player pack = new Player("Michael");
        //ImageView packGraphics = pack.getGraphics(0);
       
        
        scene = new Scene(mainWindow, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
        Render playBoard = new Render();
        playBoard.setStyle("-fx-background-color: #232323");
        GameEngine engine = new GameEngine(playBoard);
        playBoard.draw(HEIGHT - 30, WIDTH);
        Player pacman = playBoard.getPlayer();
        mainWindow.setCenter(playBoard); 
        scene.setOnKeyPressed(e -> {
            // starting the gameengine as soon as the player pushes the first key :)
            if (!engine.isRunning()){
                 engine.run();
               }
            
            //System.out.println(e.getCode());
            switch (e.getCode()) {
                case LEFT:
                case A: 
                    System.out.println("left");
                    pacman.setDirection(0);
                    break;
                case DOWN:
                case S:
                    System.out.println("down");
                    pacman.setDirection(3);
                    break;
                case RIGHT:
                case D:
                    System.out.println("right");
                    pacman.setDirection(1);
                    break;
                case UP:
                case W:
                    System.out.println("up");
                    pacman.setDirection(2);
                    break;
                default:
                    System.out.println("default");
                    break;
            }
            score.setText("Score: " + pacman.getPoints());
            int tempLives = pacman.getLives();
            // handle gameover tactics here! before we render to screen.
            if (tempLives <= 0){
                Label gameOverLabel = new Label("GAME OVER!");
                BorderPane gameOverPane = new BorderPane();
                gameOverPane.setStyle("-fx-background-color: #000;");
                gameOverLabel.setStyle("-fx-text-fill: #FFF; -fx-font-size: 42px;");
                gameOverPane.setCenter(gameOverLabel);
                gameOver = new Scene(gameOverPane ,WIDTH, HEIGHT);
                stage.setScene(gameOver);
            }
            lives.setText("Lives: " + tempLives);
        });
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}