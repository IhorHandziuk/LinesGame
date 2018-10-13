package com.handhor.lines;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Ігор on 28.07.2016.
 */

public class Numbers {
    private Texture numTexture;
    Array<TextureRegion> numbers;

    /*
    Texture with numbers 0,1,..,9
    that situated on equal regions of texture
    */
    public Numbers(Texture numTexture) {
        numTexture = numTexture;
        numbers = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = numTexture.getWidth() / 10;
        for(int i = 0; i < 10; i++){
            temp = new TextureRegion(numTexture, i * frameWidth, 0, frameWidth, numTexture.getHeight());
            numbers.add(temp);
        }
    }

    private TextureRegion getNumberUnderTen(int number) {
        return numbers.get(number);
    }

    public Array<TextureRegion> getNumber(int number) {
        Array<TextureRegion> digits = new Array<TextureRegion>();
        int modRes = 0, divRes = number;
        if (divRes == 0) {
            digits.add(getNumberUnderTen(0));
        }
        else {
            while (divRes != 0) {
                modRes = divRes % 10;
                divRes /= 10;
                digits.add(getNumberUnderTen(modRes));
            }
        }
        digits.reverse();
        return digits;
    }
}
