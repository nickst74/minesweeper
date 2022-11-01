package com.jfxgame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GridFX {
    public enum CellState {
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        BLANK,
        MINE,
        MARK
    }

    private final App app;
    private Canvas canvas;
    private GraphicsContext gc;

    private boolean clickable;

    private final int fontSize = 22, squareEdge = 30, textDispo = 8;


    /**
     * Initialize canvas to visualize the grid with all the squares
     * and add a listener for mouse clicks.
     */
    public GridFX(int rows, int cols, App app) {
        this.enable();
        // create and initialize canvas
        this.app = app;
        this.canvas = new Canvas(rows * this.squareEdge, cols * this.squareEdge);
        this.gc = canvas.getGraphicsContext2D();
        gc.setFont(new Font(this.fontSize));
        gc.setStroke(Color.BLACK);
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gc.strokeRect(this.squareEdge * i, this.squareEdge * j, this.squareEdge, this.squareEdge);
                this.updateCell(i, j, CellState.BLANK);
            }
        }
        // add mouse listener
        canvas.setOnMouseClicked(event -> {
            if(!clickable)
                return;
            // firstly find corresponding cell
            int x, y;
            x = (int) event.getX() / this.squareEdge;
            y = (int) event.getY() / this.squareEdge;
            // then infere if it wa left or right click and try to perform move
            if(event.getButton() == MouseButton.PRIMARY)
                this.app.game.reveal(x, y);
            else if(event.getButton() == MouseButton.SECONDARY)
                this.app.game.mark_unmark(x, y);
        });
    }

    /**
     * Make the grid clickable.
     */
    public void enable() {
        this.clickable = true;
    }

    /**
     * Disable the grid from being clickable.
     */
    public void disable() {
        this.clickable = false;
    }

    /**
     * Get the canvas for the visual representation.
     * @return canvas object
     */
    public Canvas getCanvas() {
        return this.canvas;
    }

    /**
     * Update a specific cell in the visual representation of the grid.
     * @param i row of cell
     * @param j column of cell
     * @param state new state
     */
    public void updateCell(int i, int j, CellState state) {
        switch (state) {
            case BLANK:
                this.updateCellHelper(i, j, null, Color.GREY, null);
                break;
            
            case MINE:
                this.updateCellHelper(i, j, "M", Color.RED, Color.BLACK);
                break;

            case MARK:
                this.updateCellHelper(i, j, "F", Color.YELLOW, Color.RED);
                break;

            case ZERO:
                this.updateCellHelper(i, j, null, Color.WHITESMOKE, null);
                break;

            case ONE:
                this.updateCellHelper(i, j, "1", Color.WHITESMOKE, Color.BLUE);
                break;
            
            case TWO:
                this.updateCellHelper(i, j, "2", Color.WHITESMOKE, Color.GREEN);
                break;
            
            case THREE:
                this.updateCellHelper(i, j, "3", Color.WHITESMOKE, Color.RED);
                break;
            
            case FOUR:
                this.updateCellHelper(i, j, "4", Color.WHITESMOKE, Color.PURPLE);
                break;
            
            case FIVE:
                this.updateCellHelper(i, j, "5", Color.WHITESMOKE, Color.MAROON);
                break;
            
            case SIX:
                this.updateCellHelper(i, j, "6", Color.WHITESMOKE, Color.TURQUOISE);
                break;
            
            case SEVEN:
                this.updateCellHelper(i, j, "7", Color.WHITESMOKE, Color.BLACK);
                break;
            
            case EIGHT:
                this.updateCellHelper(i, j, "8", Color.WHITESMOKE, Color.GRAY);
                break;
        }
    }

    private void updateCellHelper(int i, int j, String text, Color cellColor, Color textColor) {
        this.gc.setFill(cellColor);
        this.gc.fillRect(this.squareEdge * i + 1, this.squareEdge * j + 1, this.squareEdge - 2, this.squareEdge - 2);
        if(text != null) {
            this.gc.setFill(textColor);
            this.gc.fillText(text, this.squareEdge * i + this.textDispo, this.squareEdge * (j + 1) - this.textDispo);
        }
    }
}
