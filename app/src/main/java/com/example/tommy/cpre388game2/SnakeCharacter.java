package com.example.tommy.cpre388game2;

import java.util.ArrayList;

/**
 * Created by tommysch on 11/17/2015.
 */
public class SnakeCharacter extends GameCharacter{
    ArrayList<Pair> pixels;
    private boolean eaten;

    public SnakeCharacter() {
        super(16, 16, 0, 0, 1);
        pixels = new ArrayList<Pair>();
        Pair start = new Pair(16, 16);
        pixels.add(start);
        eaten = false;
    }

    public boolean move(int x, int y) {
        Pair p = new Pair(x, y);
        if(!eaten) {
            pixels.remove(pixels.size() - 1);
        } else {
            eaten = false;
        }
        if(pixels.contains(p)) {
            return true;
        }
        pixels.add(0, p);
        return false;
    }

    public ArrayList<Pair> drawToBoard() {
        return pixels;
    }

    public boolean checkApple(AppleObject apple) {
        Pair p = pixels.get(0);
        if(p.x == apple.x && p.y == apple.y) {
            eaten = true;
            return true;
        } else {
            return false;
        }
    }
}
