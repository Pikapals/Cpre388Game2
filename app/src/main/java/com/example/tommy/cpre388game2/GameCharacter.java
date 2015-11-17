package com.example.tommy.cpre388game2;

import java.util.ArrayList;

/**
 * Created by tommysch on 11/10/2015.
 */
public abstract class GameCharacter {

    int x, y;
    int r, g, b;

    public GameCharacter(int x, int y, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public abstract ArrayList<Pair> drawToBoard();

}
