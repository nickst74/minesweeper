package com.jfxgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfxgame.squares.Mine;
import com.jfxgame.squares.Safe;
import com.jfxgame.squares.Square;

public class Game {
    private int rows, cols, mines;
    private Square grid[][];

    /**
     * Initialize game variables, given at the start of a
     * new game.
     * @param rows Number of rows
     * @param cols Nunber of columns
     * @param mines Number of mines that the game should have
     */
    public Game(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
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

    public Square[][] getGrid() {
        return this.grid;
    }


    /**
     * Creates game grid and initialize all cells, by picking
     * random locations for all mines and filling the rest
     * with Safe Squares.
     */
    public void initGame() {
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
        // check if it is a mine
        if(this.grid[x][y].isMine()) {
            System.out.println("Boom!");
            return false;
        }
        // reveal Sqaures using a list
        Set<Square> set = new HashSet<>();
        List<Square> list = new ArrayList<>();
        list.add(this.grid[x][y]);
        while(!list.isEmpty()) {
            // remove square from list and reveal
            Square curr = list.remove(list.size() - 1);
            set.add(curr);
            if(curr.reveal()) {
                // if no adjacent mines, add neighbors to list
                for(Square adj : curr.adjacentSquares()) {
                    if(set.add(adj)) {
                        list.add(adj);
                    }
                }
            }
        }
        return true;
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
