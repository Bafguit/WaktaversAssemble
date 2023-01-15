package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;

import java.util.HashMap;

public class ResizeButton extends AbstractUI {

    private final FontHandler.FontData font = FontHandler.TURN_CHANGE;
    private final HashMap<Integer, Integer> resolution = new HashMap<>();
    private final Array<Integer> w = new Array<>();
    private int ww = 1280, hh = 720;
    private int index = 0;

    public ResizeButton() {
        super(FileHandler.dice.get("Dice"));
        w.add(1280);
        w.add(1366);
        w.add(1600);
        resolution.put(1280, 720);
        resolution.put(1366, 768);
        resolution.put(1600, 900);
        setPosition(1440, 810);
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, ww + "x" + hh, originX, originY);
        }
    }

    @Override
    public void onClick() {
        index++;
        if(index == w.size) {
            index = 0;
        }

        ww = w.get(index);
        hh = resolution.get(ww);

        Gdx.graphics.setWindowedMode(ww, hh);
        WaktaAssemble.camera.setToOrtho(false, ww, hh);
    }
}
