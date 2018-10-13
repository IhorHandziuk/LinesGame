package com.handhor.lines;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ігор on 20.09.16.
 */
public interface GameLoop {
    abstract void init();
    abstract void update(float dt);
    abstract void render(SpriteBatch sb);
    abstract boolean handleInput();
}
