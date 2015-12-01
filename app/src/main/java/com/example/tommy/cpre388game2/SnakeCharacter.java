package com.example.tommy.cpre388game2;

import java.util.ArrayList;

/**
 * Created by tommysch on 11/17/2015.
 */
public class SnakeCharacter extends GameCharacter{
    ArrayList<Pair> pixels;
    private boolean eaten;
    private Direction dir;
    private int score;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public SnakeCharacter() {
        super(16, 16, 0, 0, 1);
        pixels = new ArrayList<Pair>();
        Pair start = new Pair(16, 16);
        pixels.add(start);
        eaten = false;
        dir = null;
        score = 0;
    }

    public boolean move() {
        switch(dir) {
            case RIGHT:
                x++;
                if(x >= 32) {
                    x = 0;
                }
                break;
            case LEFT:
                x--;
                if(x < 0) {
                    x = 31;
                }
                break;
            case UP:
                y--;
                if(y < 0) {
                    y = 31;
                }
                break;
            case DOWN:
                y++;
                if(y >= 32) {
                    y = 0;
                }
                break;
        }
        Pair p = new Pair(x, y);
        if(!eaten) {
            pixels.remove(pixels.size() - 1);
        } else {
            eaten = false;
        }
        if(collision(p)) {
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
            score++;
            return true;
        } else {
            return false;
        }
    }

    public void setDirection(Direction direction) {
        dir = direction;
    }

    public int getScore() { return score; }

    public boolean collision(Pair p) {
        for(Pair pixel : pixels) {
            if(p.x == pixel.x && p.y == pixel.y) {
                return true;
            }
        }
        return false;
    }
}
