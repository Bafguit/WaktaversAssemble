package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.dices.basic.NormalDice;
import com.fastcat.assemble.dices.legend.Fraud3;
import com.fastcat.assemble.screens.TempScreen;

public class BattleScreen extends TempScreen {

    public ResizeButton resizeButton;
    public RollDiceButton rollButton;
    public Array<DiceButton> dice = new Array<>();
    public Array<CharacterButton> chars = new Array<>();
    public AbstractUI tracking;
    public TileSquare overTile;
    public TileSquare[][] tiles;
    public int wSize = 6, hSize = 6;

    public BattleScreen() {
        //setBg(FileHandler.bg.get("GRID"));
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(480, 600);
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
        for(int i = 1; i <= 6; i++) {
            CharacterButton b = new CharacterButton(this, i - 1);
            b.setPosition(150 * i, 270);
            chars.add(b);
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

        for(int i = 0; i < chars.size; i++) {
            CharacterButton b = chars.get(i);
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
        for(CharacterButton b : chars) {
            if(!b.tracking) b.render(sb);
        }
        for(CharacterButton b : chars) {
            if(b.tracking) b.render(sb);
        }
    }

    public void rollDice() {
        for(DiceButton b : dice) {
            b.dice.roll();
        }
    }
}
