package com.fastcat.assemble.screens.temp;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.cards.basic.TestCard;
import com.fastcat.assemble.dices.basic.NormalDice;
import com.fastcat.assemble.dices.legend.Fraud3;
import com.fastcat.assemble.handlers.FileHandler;
import com.fastcat.assemble.screens.TempScreen;

public class BattleScreen extends TempScreen {

    public BattlePhase phase;
    public ResizeButton resizeButton;
    public RollDiceButton rollButton;
    public PhaseButton phaseButton;
    public Array<DiceButton> dice = new Array<>();
    public Array<CharacterButton> chars = new Array<>();
    public Array<CardButton> hand = new Array<>();
    public AbstractUI tracking;
    public TileSquare overTile;
    public TileSquare[][] tiles;
    public int wSize = 6, hSize = 4;

    public BattleScreen() {
        phase = BattlePhase.READY;
        setBg(FileHandler.bg.get("GRID"));
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(480, 650);
        phaseButton = new PhaseButton(this);
        phaseButton.setPosition(480, 500);
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
        for(int i = 0; i < 5; i++) {
            CardButton c = new CardButton(this, new TestCard());
            c.setPosition(300 + (250 * i), 0);
            hand.add(c);
        }
    }

    @Override
    public void update() {
        resizeButton.update();
        rollButton.update();
        phaseButton.update();
        boolean tr = false;
        for(int i = 0; i < dice.size; i++) {
            DiceButton b = dice.get(i);
            b.update();
            if(b.tracking && !tr) {
                tracking = b;
                tr = true;
            }
        }

        for(int i = 0; i < chars.size; i++) {
            CharacterButton b = chars.get(i);
            b.update();
            if(b.tracking && !tr) {
                tracking = b;
                tr = true;
            }
        }

        for(int i = 0; i < hand.size; i++) {
            CardButton b = hand.get(i);
            b.setPosition(300 + (250 * i), 0);
            b.update();
            if(b.tracking && !tr) {
                tracking = b;
                tr = true;
            }
        }

        if(!tr) tracking = null;

        boolean hasOver = false;
        for(int i = 0; i < wSize; i++) {
            for(int j = 0; j < hSize; j++) {
                TileSquare t = tiles[i][j];
                t.update();
                if(tracking != null && !hasOver && t.over) hasOver = true;
            }
        }

        if(!hasOver) overTile = null;
    }

    @Override
    public void render(SpriteBatch sb) {
        resizeButton.render(sb);
        rollButton.render(sb);
        phaseButton.render(sb);
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
        for(CardButton b : hand) {
            if(!b.tracking) b.render(sb);
        }
        for(CardButton b : hand) {
            if(b.tracking) b.render(sb);
        }
    }

    public void rollDice() {
        for(DiceButton b : dice) {
            b.dice.roll();
        }
        phase = BattlePhase.CARD;
    }

    public void resetDice() {
        for(DiceButton b : dice) {
            b.reset();
        }
    }

    public void resetChar() {
        for(CharacterButton b : chars) {
            b.reset();
        }
    }

    public enum BattlePhase {
        READY,
        DEPLOY,
        DICE,
        CARD,
        END_TURN,
        TURN_CHANGE,
        ENEMY,
        END
    }
}
