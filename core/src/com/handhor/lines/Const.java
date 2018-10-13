package com.handhor.lines;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ігор on 20.09.16.
 */
public class Const {
    public static final int HEIGHT = 1920;
    public static final int WIDTH = 1080;
    public static final int FIELD_SIZE = 9; //if you wanna change this, you should change texture
    public enum BallColor {red, blue, yellow, cyan, magenta, green};

    public static final List<BallColor> COLOR_VALUES =
            Collections.unmodifiableList(Arrays.asList(BallColor.values()));
    public static final Random RANDOM = new Random();
}
