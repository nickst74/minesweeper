package com.jfxgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public Game game;
    public GridFX gridFX;


    @Override
    public void start(Stage stage) throws IOException {
        // initialize grid and game
        this.game = new Game(10, 10, 10, this);
        game.initGame();
        this.gridFX = new GridFX(10, 10, this);
        Group root = new Group();
        ScrollPane pane = new ScrollPane(this.gridFX.getCanvas());
        pane.setMaxSize(500, 500);
        root.getChildren().add(pane);
        scene = new Scene(root);
        stage.sizeToScene();
        //scene = new Scene(loadFXML("main-menu"), 640, 480);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}