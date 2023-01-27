package com.fastcat.assemble.screens.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fastcat.assemble.WaktaAssemble;
import com.fastcat.assemble.abstrcts.AbstractGame;
import com.fastcat.assemble.abstrcts.AbstractScreen;
import com.fastcat.assemble.abstrcts.AbstractUI;
import com.fastcat.assemble.cards.basic.TestCard;
import com.fastcat.assemble.dices.basic.NormalDice;
import com.fastcat.assemble.dices.legend.Fraud3;
import com.fastcat.assemble.handlers.InputHandler;

import java.util.Iterator;

public class BattleScreen extends AbstractScreen {

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
    public int wSize = 8, hSize = 6;

    public BattleScreen() {
        super(ScreenType.BASE);
        WaktaAssemble.game = new AbstractGame();
        phase = BattlePhase.READY;
        resizeButton = new ResizeButton();
        rollButton = new RollDiceButton(this);
        rollButton.setPosition(320, 650);
        phaseButton = new PhaseButton(this);
        phaseButton.setPosition(320, 500);
        for(int i = 0; i < 6; i++) {
            DiceButton b = new DiceButton(this, new NormalDice(), i);
            b.setPosition(60, 920 - 100 * i);
            dice.add(b);
        }
        for(int i = 0; i < 6; i++) {
            CharacterButton b = new CharacterButton(this, i);
            b.setPosition(710 + 100 * i, 270);
            chars.add(b);
        }
        tiles = new TileSquare[wSize][hSize];
        for (int i = 0; i < wSize; i++) {
            for(int j = 0; j < hSize; j++) {
                TileSquare t = new TileSquare(this);
                t.setPosition(610 + 100 * i, 890 - 100 * j);
                tiles[i][j] = t;
            }
        }
        float start = 960 - (((float) (hand.size - 1) / 2) * 250);
        for(int i = 0; i < 5; i++) {
            CardButton c = new CardButton(this, new TestCard());
            c.setPosition(start + (250 * i), 50);
            c.fluid = true;
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

        if(hand.size > 0) {
            Iterator<CardButton> itr = hand.iterator();
            int cnt = 0;
            float start = 960 - (((float) (hand.size - 1) / 2) * hand.get(0).originWidth);
            while (itr.hasNext()) {
                CardButton b = itr.next();
                b.setPosition(start + (b.originWidth * cnt++), 50);
                b.update();
                if (b.tracking && !tr) {
                    tracking = b;
                    tr = true;
                }
                if (b.isUsed) itr.remove();
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
    public void render(float delta) {
        WaktaAssemble.application.sb.setColor(Color.WHITE);
        if(background != null) {
            WaktaAssemble.application.sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        render(WaktaAssemble.application.sb);
        effectHandler.render(WaktaAssemble.application.sb);
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
        for(CharacterButton b : chars) {
            if(!b.tracking) b.render(sb);
        }
        for(CardButton b : hand) {
            if(!b.tracking) b.render(sb);
        }
        //FontHandler.renderCenter(sb, FontHandler.LOGO, "WAKTAVERSE ASSEMBLE", 0, 980 * InputHandler.scaleY, 1920 * InputHandler.scaleX);
        if(tracking != null) tracking.render(sb);
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
