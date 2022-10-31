package com.jfxgame.squares;

import com.jfxgame.Game;

public class Safe extends Square {
    public int adjMines;

    public Safe(int x, int y, Game game) {
        super(Type.SAFE, x, y, game);
        this.adjMines = 0;
    }

    /**
     * Check neighbing squares for mines
     * @return number of adjacent mines
     */
    public int getAdjMines() {
        return this.adjMines;
    }
}
