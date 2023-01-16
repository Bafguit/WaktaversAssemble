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
    public DiceButton tracking;
    public TileSquare overTile;
    public TileSquare[][] tiles;
    public int wSize = 6, hSize = 6;

    public DiceRollScreen() {
        //setBg(FileHandler.bg.get("GRID"));
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(480, 270);
        for(int i = 1; i <= 3; i++) {
            DiceButton b = new DiceButton(this, new Fraud3(), i - 1);
            b.setPosition(150 * i, 810);
            dice.add(b);
        }
        for(int i = 4; i <= 6; i++) {
            DiceButton b = new DiceButton(this, new NormalDice(), i - 1);
            b.setPosition(150 * i, 810);
            dice.add(b);
        }
        tiles = new TileSquare[wSize][hSize];
        for (int i = 0; i < wSize; i++) {
            for(int j = 0; j < hSize; j++) {
                TileSquare t = new TileSquare(this);
                t.setPosition(1100 + 100 * i, 600 - 100 * j);
                tiles[i][j] = t;
            }
        }
    }

    @Override
    public void update() {
        resizeButton.update();
        rollButton.update();

        for(int i = 0; i < dice.size; i++) {
            DiceButton b = dice.get(i);
            b.update();
            if(b.tracking) tracking = b;
        }

        boolean hasOver = false;
        for(int i = 0; i < wSize; i++) {
            for(int j = 0; j < hSize; j++) {
                TileSquare t = tiles[i][j];
                t.update();
                if(tracking != null && !hasOver && t.over) hasOver = true;
            }
        }

        if(!hasOver) overTile = null;

        tracking = null;
    }

    @Override
    public void render(SpriteBatch sb) {
        resizeButton.render(sb);
        rollButton.render(sb);
        for(int i = 0; i < wSize; i++) {
            for(int j = 0; j < hSize; j++) {
                TileSquare t = tiles[i][j];
                t.render(sb);
            }
        }
        for(DiceButton b : dice) {
            if(!b.tracking) b.render(sb);
        }
        for(DiceButton b : dice) {
            if(b.tracking) b.render(sb);
        }
    }

    public void rollDice() {
        for(DiceButton b : dice) {
            b.dice.roll();
        }
    }
}
