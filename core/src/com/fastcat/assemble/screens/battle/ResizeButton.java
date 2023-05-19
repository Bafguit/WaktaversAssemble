package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.MousseAdventure;
import com.fastcat.assemble.abstracts.AbstractUI;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;

import java.util.HashMap;

import static com.fastcat.assemble.MousseAdventure.cam;
import static com.fastcat.assemble.MousseAdventure.camera;

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
        setPosition(1640, 810);
        is3D = false;
    }

    @Override
    protected void renderUi(SpriteBatch sb) {
        if (enabled) {
            if (overable && !over) sb.setColor(Color.LIGHT_GRAY);
            sb.draw(img, x, y, width, height);
            FontHandler.renderCenter(sb, font, ww + "x" + hh, x + width * 0.5f, y + height * 0.5f);
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
        MousseAdventure.camera.setToOrtho(false, ww, hh);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        InputHandler.getInstance().update();
        cam.position.set(w * 0.5f, h * 0.3f, 600 * InputHandler.scaleA);
        cam.lookAt(w * 0.5f, h * 0.5f,0);
        cam.near = InputHandler.scaleA;
        cam.far = 1300 * InputHandler.scaleA;
    }
}
