package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.screens.TempScreen;

public class DiceRollScreen extends TempScreen {

    public Array<DiceButton> dice = new Array<>();

    public DiceRollScreen() {
        setBg(FileHandler.bg.get("GRID"));
        DiceButton b = new DiceButton();
        b.setPosition(960, 540);
        dice.add(b);
    }

    @Override
    public void update() {
        for(DiceButton b : dice) {
            b.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        for(DiceButton b : dice) {
            b.render(sb);
        }
    }
}
