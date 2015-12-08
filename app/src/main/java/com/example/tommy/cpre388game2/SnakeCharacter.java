package com.example.tommy.cpre388game2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by tommysch on 11/17/2015.
 */
public class SnakeCharacter extends GameCharacter implements Parcelable {
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

    private SnakeCharacter(Parcel in) {
        super(in.readInt(), in.readInt(), 0, 0, 1);
        pixels = new ArrayList<Pair>();
        in.readList(pixels, null);
        switch(in.readInt()) {
            case 0:
                dir = Direction.UP;
                break;
            case 1:
                dir = Direction.DOWN;
                break;
            case 2:
                dir = Direction.LEFT;
                break;
            case 3:
                dir = Direction.RIGHT;
                break;
        }
        score = in.readInt();
        eaten = false;
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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeList(pixels);
        switch(dir) {
            case UP:
                out.writeInt(1);
                break;
            case DOWN:
                out.writeInt(2);
                break;
            case LEFT:
                out.writeInt(3);
                break;
            case RIGHT:
                out.writeInt(4);
                break;
        }
        out.writeInt(score);
    }

    public static final Parcelable.Creator<SnakeCharacter> CREATOR
            = new Parcelable.Creator<SnakeCharacter>() {
        public SnakeCharacter createFromParcel(Parcel in) {
            return new SnakeCharacter(in);
        }

        public SnakeCharacter[] newArray(int size) {
            return new SnakeCharacter[size];
        }
    };
}
