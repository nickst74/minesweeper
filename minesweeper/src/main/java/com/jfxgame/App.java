package com.jfxgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App{// extends Application {
/*
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main-menu"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }*/
    public static Game game;

    public static void main(String[] args) {
        App.game = new Game(10, 10, 5);
        App.game.initGame();
        int x, y;
        Scanner myInput = new Scanner( System.in );
        while(true) {
            App.game.printGrid();
            x = myInput.nextInt();
            y = myInput.nextInt();
            App.game.reveal(x, y);
        }
        //launch();
    }

}