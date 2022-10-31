package com.jfxgame.squares;

import com.jfxgame.Game;

public class Mine extends Square {
    public Mine(int x, int y, Game game) {
        super(Type.MINE, x, y, game);
    }
}
