package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractDice;
import com.fastcat.assemble.abstrcts.AbstractGame;
import com.fastcat.assemble.dices.basic.NormalDice;
import com.fastcat.assemble.dices.legend.Fraud3;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.handlers.FontHandler;
import com.fastcat.assemble.handlers.InputHandler;
import com.fastcat.assemble.screens.TempScreen;
import com.fastcat.assemble.utils.FastCatUtils;

public class DiceRollScreen extends TempScreen {

    public ResizeButton resizeButton;
    public RollDiceButton rollButton;
    public Array<DiceButton> dice = new Array<>();

    public DiceRollScreen() {
        setBg(FileHandler.bg.get("GRID"));
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(480, 270);
        for(int i = 1; i <= 3; i++) {
            DiceButton b = new DiceButton(new Fraud3());
            b.setPosition(150 * i, 810);
            dice.add(b);
        }
        for(int i = 4; i <= 6; i++) {
            DiceButton b = new DiceButton(new NormalDice());
            b.setPosition(150 * i, 810);
            dice.add(b);
        }
    }

    @Override
    public void update() {
        resizeButton.update();
        rollButton.update();
        for(DiceButton b : dice) {
            b.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        resizeButton.render(sb);
        rollButton.render(sb);
        for(int i = 0; i < dice.size; i++) {
            DiceButton b = dice.get(i);
            b.setPosition(120 * (i + 1), 810);
            b.render(sb);
        }
    }

    public void rollDice() {
        FastCatUtils.staticShuffle(dice, AbstractGame.publicRandom, DiceButton.class);
        for(DiceButton b : dice) {
            b.dice.roll();
        }
    }
}
