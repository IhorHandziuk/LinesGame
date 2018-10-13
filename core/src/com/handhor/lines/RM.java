package com.handhor.lines;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ігор on 20.09.16.
 * Resource Manager
 */
public class RM {
    private Map <String, Texture> textures;

    private static RM instance = new RM();

    //get instance
    public static RM gIns() {
        return instance;
    }

    private RM() {
        textures = new HashMap<String, Texture>();
        textures.put("blue", new Texture("blue_ball.png"));
        textures.put("cyan", new Texture("cyan_ball.png"));
        textures.put("green", new Texture("green_ball.png"));
        textures.put("magenta", new Texture("magenta_ball.png"));
        textures.put("red", new Texture("red_ball.png"));
        textures.put("yellow", new Texture("yellow_ball.png"));
        textures.put("shining", new Texture("shining_ball.png"));
        textures.put("field", new Texture("field.png"));
        textures.put("score", new Texture("score.png"));
        textures.put("numbers", new Texture("numbers.png"));
        textures.put("gameover", new Texture("gameover.png"));
    }

    public Texture getTexture(String name) {
        return textures.get(name);
    }
}
