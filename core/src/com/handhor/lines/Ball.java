package com.handhor.lines;

import com.badlogic.gdx.graphics.Texture;
import com.handhor.lines.Const;
import com.handhor.lines.RM;

/**
 * Created by Ігор on 20.09.16.
 */
public class Ball {
    Texture ballTxt;
    Const.BallColor color;

    public Ball(Const.BallColor color) {
        ballTxt = RM.gIns().getTexture(color.toString());
        this.color = color;
    }

    public Texture getTexture() {
        return ballTxt;
    }

    public Const.BallColor getColor() {
        return color;
    }
}
