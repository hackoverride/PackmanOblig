package com.classroom.packman;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
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
    
    final int HEIGHT = 800;
    final int WIDTH = (int)(HEIGHT * 1.618);
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
        Button menuButn_play = new Button("Play");
        Label score = new Label("Total Score: ");
        menu_container.getChildren().addAll(menuButn_play, score);
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
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A: 
                    System.out.println("left");
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
        mainWindow.setCenter(playBoard);
        Timer tid = new Timer();
        TimerTask ge = new GameEngine();
        tid.schedule(ge, 0, 500);
        menuButn_play.setOnAction((event) -> {
            System.out.println("play");
            playBoard.draw(this.HEIGHT - 100, this.WIDTH);
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