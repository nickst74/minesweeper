package com.jfxgame.squares;

import com.jfxgame.Game;
import com.jfxgame.GridFX.CellState;

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
    public Integer getX() {
        return this.pos.getKey();
    }

    /**
     * Get y coordinate
     * @return column of square
     */
    public Integer getY() {
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
        if(this.isMine())
            return false;
        if(this.isMarked())
            this.mark_unmark();
        return ((Safe)this).getAdjMines() == 0;
    }

    public boolean isRevealed() {
        return this.revealed;
    }

    public boolean isMarked() {
        return this.marked;
    }

    public CellState getState() {
        if(this.marked)
            return CellState.MARK;
        if(this.revealed) {
            if(this.isMine())
                return CellState.MINE;
            switch (((Safe)this).getAdjMines()) {
                case 0:
                    return CellState.ZERO;
                case 1:
                    return CellState.ONE;
                case 2:
                    return CellState.TWO;
                case 3:
                    return CellState.THREE;
                case 4:
                    return CellState.FOUR;
                case 5:
                    return CellState.FIVE;
                case 6:
                    return CellState.SIX;
                case 7:
                    return CellState.SEVEN;
                case 8:
                    return CellState.EIGHT;
            };
        }
        return CellState.BLANK;
    }
}
