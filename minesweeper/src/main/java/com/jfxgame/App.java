package com.jfxgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public Game game;
    public GridFX gridFX;
    public Header header;
    private Pane mainPane, mainMenu;


    @Override
    public void start(Stage stage) throws IOException {
        // initialize grid and game
        //this.game = new Game(20, 20, 10, this);
        //this.gridFX = new GridFX(20, 20, this);
        
        // set main region variable for later updates
        Pane mainPane = new StackPane();
        this.mainPane = mainPane;

        // create a footer region
        Region footer = new Region();
        footer.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c0"), CornerRadii.EMPTY, Insets.EMPTY)));
        footer.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        VBox.setVgrow(footer, Priority.ALWAYS);

        // create center left and center right region
        Region left = new Region();
        left.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c0"), CornerRadii.EMPTY, Insets.EMPTY)));
        left.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
            BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        HBox.setHgrow(left, Priority.SOMETIMES);

        Region right = new Region();
        right.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c0"), CornerRadii.EMPTY, Insets.EMPTY)));
        right.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
            BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
        HBox.setHgrow(right, Priority.SOMETIMES);
        
        
        // Create an HBox to center grid horizontally
        HBox hbox = new HBox();
        hbox.getChildren().addAll(left, this.mainPane, right);

        // Create header
        this.header = new Header(this);

        // create VBox for main layout
        VBox vbox = new VBox();
        vbox.getChildren().addAll(header.getHeader(), hbox, footer);

        // create the scene and show graphical intereface
        scene = new Scene(vbox);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setMinWidth(400);
        stage.setMinHeight(288);
        stage.setTitle("Minesweeper");
        // create and set main panel to main menu
        this.createMainMenu();
        this.gotoMainMenu();
        // show stage
        stage.show();
    }

    /**
     * Initializes and start a new game instance.
     * @param rows rows of grid
     * @param cols columns of grid
     * @param mines mines to be placed
     */
    private void startNewGame(int rows, int cols, int mines) {
        // init game and visual grid
        this.game = new Game(rows, cols, mines, this);
        this.gridFX = new GridFX(rows, cols, this);
        this.header.resetButton();
        
        // create scrollpane and set max size to fit the grid canvas
        ScrollPane scrollPane = new ScrollPane(this.gridFX.getCanvas());
        scrollPane.setMaxSize(gridFX.getCanvas().getWidth() + 2, gridFX.getCanvas().getHeight() + 2);
        // set main region to contain the scrollpane created
        this.mainPane.getChildren().clear();
        this.mainPane.getChildren().addAll(scrollPane);
        this.header.resetCounter(true);
        this.header.updateMineCnt(mines);
    }

    /**
     * Resets game by creating new instance with same configuration.
     */
    public void resetGame() {
        int rows, cols, mines;
        rows = this.game.rows;
        cols = this.game.cols;
        mines = this.game.mines;
        this.startNewGame(rows, cols, mines);
    }

    /**
     * Checks if game is in progress
     * @return True if game is in progress, else false
     */
    public boolean inGame() {
        return !(this.mainPane.getChildren().get(0) == this.mainMenu);
    }

    /**
     * Switch to main menu.
     */
    public void gotoMainMenu() {
        this.mainPane.getChildren().clear();
        this.mainPane.getChildren().addAll(this.mainMenu);
        this.header.resetButton();
        this.header.resetCounter(false);
        this.header.updateMineCnt(0);
    }

    /**
     * Initialize main menu.
     */
    private void createMainMenu() {
        // create grid for main menu
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setMinSize(396, 200);

        // add fields for options
        Label rows = new Label("Rows:");
        grid.add(rows, 0, 1);

        TextField rowsTextField = new TextField();
        grid.add(rowsTextField, 1, 1);

        Label cols = new Label("Columns:");
        grid.add(cols, 0, 2);

        TextField colsTextField = new TextField();
        grid.add(colsTextField, 1, 2);

        Label mines = new Label("Mines:");
        grid.add(mines, 0, 3);

        TextField minesTextField = new TextField();
        grid.add(minesTextField, 1, 3);

        // create a button to start game
        Button btn = new Button("Start");
        btn.setMinWidth(minesTextField.getPrefWidth());
        grid.add(btn, 1, 4);

        // text for any wrong or missing input warning
        final Text warning = new Text();
        warning.setFill(Color.FIREBRICK);
        grid.add(warning, 1, 5);

        btn.setOnAction(event -> {
            int vrows, vcols, vmines;
            String srows = rowsTextField.getText();
            String scols = colsTextField.getText();
            String smines = minesTextField.getText();
            if(srows.isEmpty() || scols.isEmpty() || smines.isEmpty()) {
                warning.setText("Input missing!");
                return;
            }
            try {
                vrows = Integer.parseInt(srows);
                vcols = Integer.parseInt(scols);
                vmines = Integer.parseInt(smines);
            } catch (Exception e) {
                warning.setText("Input not an integer!");
                return;
            }
            if(!(vrows > 0 && vcols > 0 && vmines > 0)) {
                warning.setText("Only positive numbers!");
                return;
            }
            if(vrows * vcols < vmines) {
                warning.setText("Too many mines!");
                return;
            }
            warning.setText("");
            this.startNewGame(vrows, vcols, vmines);
        });

        this.mainMenu = grid;
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