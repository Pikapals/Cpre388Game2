package com.example.tommy.cpre388game2;

import java.util.ArrayList;

/**
 * Created by tommysch on 11/17/2015.
 */
public class AppleObject extends GameCharacter {

    public AppleObject(int x, int y) {
        super(x, y, 0, 1, 0);
    }

    public ArrayList<Pair> drawToBoard() {
        ArrayList<Pair> arr = new ArrayList<Pair>();
        arr.add(new Pair(x, y));
        return arr;
    }
}
