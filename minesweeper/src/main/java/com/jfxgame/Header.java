package com.jfxgame;

import java.util.Optional;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Header {
    private BorderPane header;
    private Button homeBtn, resetBtn;
    private final App app;
    private Label mLabel, tLabel;
    private int counter;
    private Timeline timeline;

    /**
     * Create an anchorpane for the header.
     */
    public Header(App app) {
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            this.counter++;
            this.tLabel.setText(Integer.toString(this.counter));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        this.app = app;
        this.header = new BorderPane();
        header.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c0"), CornerRadii.EMPTY, Insets.EMPTY)));
        header.setBorder(new Border(new BorderStroke(Color.BLACK, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        header.setMinHeight(50);
        header.setMaxHeight(50);
        header.setMinWidth(400);

        // Create Home button on the left
        this.homeBtn = new Button("Back");
        this.homeBtn.setTextFill(Color.WHITE);
        this.homeBtn.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.homeBtn.setPrefSize(60, 50);
        this.homeBtn.setOnAction(event -> {
            if(this.app.inGame()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Exit");
                alert.setHeaderText("Game progress will be lost.");
                alert.setContentText("Are you sure you want to exit?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    this.timeline.stop();
                    this.resetCounter(false);
                    this.app.gotoMainMenu();
                }
            }
        });

        this.header.setLeft(homeBtn);

        this.resetBtn = new Button();
        this.resetBtn.setFont(new Font(20.0));
        this.resetBtn.setPrefHeight(50);
        this.resetButton();

        this.header.setCenter(this.resetBtn);

        BorderPane rightPane = new BorderPane();
        rightPane.setPrefWidth(60);
        this.mLabel = new Label("0");
        this.tLabel = new Label("0");
        this.mLabel.setTextFill(Color.RED);
        this.mLabel.setFont(new Font(20.0));
        this.tLabel.setFont(new Font(20.0));
        this.mLabel.setPrefHeight(50);
        this.tLabel.setPrefHeight(50);
        rightPane.setLeft(mLabel);
        rightPane.setRight(tLabel);
        this.header.setRight(rightPane);
    }

    public void resetButton() {
        this.resetBtn.setTextFill(Color.BLACK);
        this.resetBtn.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c0"), CornerRadii.EMPTY, Insets.EMPTY)));
        this.resetBtn.setText("In Progress");
        this.resetBtn.setOnAction(null);
    }

    public void victoryButton() {
        this.resetBtn.setTextFill(Color.GREEN);
        this.resetBtn.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.resetBtn.setText("VICTORY");
        this.timeline.stop();
        this.resetBtn.setOnAction(event -> {
            this.app.resetGame();
        });
    }

    public void gameOverButton() {
        this.resetBtn.setTextFill(Color.RED);
        this.resetBtn.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.resetBtn.setText("GAME OVER");
        this.timeline.stop();
        this.resetBtn.setOnAction(event -> {
            this.app.resetGame();
        });
    }

    public void resetCounter(boolean start) {
        this.counter = 0;
        this.tLabel.setText("0");
        if(start) {
            this.timeline.play();
        }
    }

    public void updateMineCnt(int mines) {
        this.mLabel.setText(Integer.toString(mines));
    }

    /**
     * Return the anchorpane header.
     * @return
     */
    public BorderPane getHeader() {
        return this.header;
    }
}
