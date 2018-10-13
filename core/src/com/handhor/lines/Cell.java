package com.handhor.lines;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ігор on 20.09.16.
 */
public class Cell {
    private boolean empty;
    private Ball ball;
    private Rectangle bounds;

    public Cell() {
        empty = true;
    }

    public void eraseBall() {
        empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
        this.empty = false;
    }
}
