package com.classroom.packman;

import java.util.Timer;
import java.util.TimerTask;
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
    Scene scene;
    
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
        Label score = new Label("Total Score: ");
        menu_container.getChildren().addAll(score);
        mainWindow.setTop(menu_container);
        
        // testing out images
        //Player pack = new Player("Michael");
        //ImageView packGraphics = pack.getGraphics(0);
       
        
        scene = new Scene(mainWindow, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
        Render playBoard = new Render();
        playBoard.setStyle("-fx-background-color: #232323");
        /* Handle */
        
        GameEngine engine = new GameEngine(playBoard);
        playBoard.draw(HEIGHT, WIDTH);
        Player pacman = playBoard.getPlayer();
        mainWindow.setCenter(playBoard); 
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A: 
                    System.out.println("left");
                    engine.run();
                    break;
                case S:
                    System.out.println("down");
                    break;
                case D:
                    System.out.println("right");
                    break;
                case W:
                    System.out.println("up");
                    break;
                default:
                    System.out.println("default");
                    break;
            }
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