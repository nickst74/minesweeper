package com.jfxgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfxgame.GridFX.CellState;
import com.jfxgame.squares.Mine;
import com.jfxgame.squares.Safe;
import com.jfxgame.squares.Square;

public class Game {
    public int rows, cols, mines;
    private int marked, revealed;
    private Square grid[][];
    private final App app;

    /**
     * Initialize game variables, given at the start of a
     * new game.
     * @param rows Number of rows
     * @param cols Nunber of columns
     * @param mines Number of mines that the game should have
     */
    public Game(int rows, int cols, int mines, App app) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.app = app;
        this.marked = 0;
        this.revealed = 0;
        this.initGame();
    }

    /**
     * Get number of rows for current session
     * @return number of rows
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Get number of columns for current session
     * @return number of columns
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Creates game grid and initialize all cells, by picking
     * random locations for all mines and filling the rest
     * with Safe Squares.
     */
    public void initGame() {
        //System.out.println("Created game");
        // initialize grid;
        this.grid = new Square[this.rows][this.cols];
        // pick postitions for mines
        List<Integer> mines = randomInts(this.mines, this.rows * this.cols);
        for (Integer idx : mines) {
            int x, y;
            x = idx / this.cols;
            y = idx % this.cols;
            this.grid[x][y] = new Mine(x, y, this);
        }
        // fill the rest of the squares
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(this.grid[i][j] == null)
                    this.grid[i][j] = new Safe(i, j, this);
            }
        }
        // for every mine increase adjMines for its surrounding blocks
        for (Integer idx : mines) {
            int x, y;
            x = idx / this.cols;
            y = idx % this.cols;
            for(int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if(i >= 0 && i < this.rows && j >= 0 && j < this.cols && !this.grid[i][j].isMine()) {
                        ((Safe)this.grid[i][j]).adjMines++;
                    }
                }
            }
        }
    }


    /**
     * Reveal squares in grid starting from the specified coordinates.
     * @param x Corresponding row index
     * @param y Corresponding column index
     * @return false if it is a mine, else true
     */
    public boolean reveal(int x, int y) {
        if(this.grid[x][y].isRevealed() || this.grid[x][y].isMarked())
            return true;
        // check if it is a mine
        if(this.grid[x][y].isMine()) {
            //System.out.println("Boom!");
            this.gameOver();
            return false;
        }
        // reveal Sqaures using a list
        Set<Square> set = new HashSet<>();
        List<Square> list = new ArrayList<>();
        list.add(this.grid[x][y]);
        while(!list.isEmpty()) {
            // remove square from list and reveal
            Square curr = list.remove(list.size() - 1);
            // if we reveal indirectly a marked square, reduce mark count
            if(curr.isMarked()) {
                this.marked--;
            }
            set.add(curr);
            if(curr.reveal()) {
                // if no adjacent mines, add not revealed neighbors to list
                for(Square adj : this.adjacentSquares(curr)) {
                    if(set.add(adj) && !adj.isRevealed()) {
                        list.add(adj);
                    }
                }
            }
            // update revealed square
            this.app.gridFX.updateCell(curr.getX(), curr.getY(), curr.getState());
            // also increase revealed squares
            this.revealed++;
        }
        //System.out.println(revealed);
        // check if game is finished
        if(this.rows * this.cols == this.mines + this.revealed)
            this.victory();
        return true;
    }

    /**
     * Find all Squares adjacent to current one
     * @return A list with adjacent squares
     */
    private List<Square> adjacentSquares(Square curr) {
        List<Square> adjs = new ArrayList<>();
        for(int i = curr.getX() - 1; i <= curr.getX() + 1; i++) {
            for (int j = curr.getY() - 1; j <= curr.getY() + 1; j++) {
                if(i >= 0 && i < this.getRows() && j >= 0 && j < this.getCols() && (i != curr.getX() || j != curr.getY())) {
                    adjs.add(this.grid[i][j]);
                }
            }
        }
        return adjs;
    }

    /**
     * Change marked state of a cell if possible
     * @param x cell row
     * @param y cell column
     */
    public void mark_unmark(int x, int y) {
        // check if it is revealed before
        if(!this.grid[x][y].isRevealed()) {
            // if cell is marked, unmark it
            if(this.grid[x][y].isMarked()) {
                this.grid[x][y].mark_unmark();
                this.app.gridFX.updateCell(x, y, CellState.BLANK);
                this.marked--;
            } else if(this.marked < this.mines) { // if cell is marked, check if we have any mark left
                this.grid[x][y].mark_unmark();
                this.app.gridFX.updateCell(x, y, CellState.MARK);
                this.marked++;
            }
        }
        this.app.header.updateMineCnt(this.mines - this.marked);
    }

    /**
     * Called when the player reveales a mine and loses.
     */
    private void gameOver() {
        // make the grid unclickable
        this.app.gridFX.disable();
        // just reveal locations of all mines
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(this.grid[i][j].isMine())
                    this.app.gridFX.updateCell(i, j, CellState.MINE);
            }
        }
        this.app.header.gameOverButton();
        //System.out.println("GAME OVER");
    }

    /**
     * Called when the  player reveals all safe cells in the grid
     * and wins the game.
     */
    private void victory() {
        // make the grid unclickable
        this.app.gridFX.disable();
        this.app.header.victoryButton();
        //System.out.println("VICTORY");
    }


    /**
     * Randomly pick unique random Integers from
     * inside the range of [0, max).
     * @param count Number of random Integers to pick
     * @param max Ceiling of numbers (not included in possible picks)
     * @return A list of unique random Integers
     */
    private List<Integer> randomInts(int count, int max) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        // create an array of INtegers from 0 to max
        for (int i=1; i<max; i++)
            list.add(i);
        // shuffle array and pick first elements
        Collections.shuffle(list);
        return list.subList(0, count);
    }

    /**
     * Just a testing function to print the grid
     */
    public void printGrid() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(this.grid[i][j].isRevealed()) {
                    if(this.grid[i][j].isMine()) {
                        System.out.print("x ");
                    } else {
                        System.out.print(Integer.toString(((Safe)this.grid[i][j]).adjMines)+" ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("");
        }
    }
}
