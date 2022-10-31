package com.jfxgame.squares;

import java.util.ArrayList;
import java.util.List;

import com.jfxgame.Game;

import javafx.util.Pair;

public class Square {
    enum Type {
        SAFE,
        MINE
    }

    protected Game game;
    protected Pair<Integer, Integer> pos;

    protected final Type type;
    protected boolean marked, revealed;

    /**
     * Create a square in the grid, of specified type,
     * also storing its coordinates
     * @param type Mine or Safe square
     * @param x Row of square
     * @param y Column of square
     * @param game The active game session
     */
    public Square(Type type, int x, int y, Game game) {
        this.type = type;
        this.pos = new Pair<Integer,Integer>(x, y);
        this.game = game;
        this.marked = false;
        this.revealed = false;
    }

    /**
     * Check if square is a Mine
     * @return True if Mine else false
     */
    public boolean isMine() {
        return this.type == Type.MINE;
    }

    /**
     * Get x coordinate
     * @return row of square
     */
    protected Integer getX() {
        return this.pos.getKey();
    }

    /**
     * Get y coordinate
     * @return column of square
     */
    protected Integer getY() {
        return this.pos.getValue();
    }

    /**
     * Get square position
     * @return Pair with coordinates
     */
    public Pair<Integer, Integer> getPos() {
        return pos;
    }

    /**
     * Find all Squares adjacent to current one
     * @return A list with adjacent squares
     */
    public List<Square> adjacentSquares() {
        List<Square> adjs = new ArrayList<>();
        for(int i = this.getX() - 1; i <= this.getX() + 1; i++) {
            for (int j = this.getY() - 1; j <= this.getY() + 1; j++) {
                if(i >= 0 && i < this.game.getRows() && j >= 0 && j < this.game.getCols() && (i != this.getX() || j != this.getY())) {
                    adjs.add(this.game.getGrid()[i][j]);
                }
            }
        }
        return adjs;
    }

    /**
     * Mark or Unmark square, depends on previous state
     */
    public void mark_unmark() {
        this.marked = !this.marked;
    }

    /**
     * Reveal square and neighbors recursively if needed
     * @return
     */
    public boolean reveal() {
        this.revealed = true;
        if(this.isMine()) // TODO: maybe throw an exception
            return false;
        return ((Safe)this).getAdjMines() == 0;
    }

    public boolean isRevealed() {
        return this.revealed;
    }
}
