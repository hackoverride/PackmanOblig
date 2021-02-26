package com.classroom.packman;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    
    @Override
    public void start(Stage stage) {
        
        BorderPane mainWindow = new BorderPane();
        mainWindow.setStyle("-fx-background-color: #000;");
        // setting the mainWindow to the scene first.
        VBox menu_container = new VBox(30);
        Button menuButn_play = new Button("Play");
        menu_container.getChildren().addAll(menuButn_play);
        mainWindow.setCenter(menu_container);
        scene = new Scene(mainWindow, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
        
        Timer tid = new Timer();
        TimerTask ge = new GameEngine();
        tid.schedule(ge, 0, 500);
        Loader load = new Loader();
        menuButn_play.setOnAction((event) -> {
            System.out.println("play");
            load.writeFirstLevel();
        });
        
    }

    public static void main(String[] args) {
        launch();
    }

}