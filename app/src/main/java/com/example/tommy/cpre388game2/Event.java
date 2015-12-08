package com.example.tommy.cpre388game2;

/**
 * This class represents the Event object whose data will be stored in the database.
 */
public class Event {

    // Unique ID of event. User does not enter this information.
    private long id;

    private int highScore;

    public Event(long id, int highScore) {
        this.id = id;
        this.highScore = highScore;
    }

    /**
     * Constructs an empty Event
     */
    public Event() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        highScore = score;
    }
}